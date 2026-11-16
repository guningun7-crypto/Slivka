const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");
const serverTimestamp = () => new Date(Date.now() - 1000);

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (
  process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085"
).split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

function validProfile(uid) {
  return {
    userId: uid,
    name: "Алиса",
    gender: "girl",
    skinToneIndex: 1,
    hairStyleIndex: 0,
    hairColorIndex: 2,
    hatId: "cap",
    coatId: "plum",
    accessoryId: "loupe",
    selectedDifficulty: "detective",
    totalScore: 0,
    totalStars: 0,
    hintsBalance: 3,
    onboardingCompleted: true,
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp(),
  };
}

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user cannot read or write profile or subcollections", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).set(validProfile(ALICE_UID)));
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("clues").get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("quests").get());
});

test("Owner can create and update their DetectiveProfile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const ref = aliceDb.collection("users").doc(ALICE_UID);
  await assertSucceeds(ref.set(validProfile(ALICE_UID)));
  await assertSucceeds(ref.get());
  await assertSucceeds(
    ref.update({
      coatId: "emerald",
      totalScore: 450,
      updatedAt: serverTimestamp(),
    })
  );
});

test("Cross-user access is denied on profile and subcollections", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).set(validProfile(ALICE_UID)));

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).get());
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).update({ name: "Взлом", updatedAt: serverTimestamp() })
  );
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("clues").get());
});

test("Profile rejects ghost fields, invalid gender, and oversized name", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const ref = aliceDb.collection("users").doc(ALICE_UID);

  const ghost = { ...validProfile(ALICE_UID), isAdmin: true };
  await assertFails(ref.set(ghost));

  const badGender = { ...validProfile(ALICE_UID), gender: "robot" };
  await assertFails(ref.set(badGender));

  const longName = { ...validProfile(ALICE_UID), name: "А".repeat(31) };
  await assertFails(ref.set(longName));
});

test("Owner can manage location_progress, clues, achievements, and quests with terminal locking", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const userRef = aliceDb.collection("users").doc(ALICE_UID);

  // Location progress create & update
  const locRef = userRef.collection("location_progress").doc("cottage");
  await assertSucceeds(
    locRef.set({
      userId: ALICE_UID,
      locationId: "cottage",
      unlocked: true,
      completed: false,
      stars: 0,
      bestScore: 0,
      bestTimeSeconds: 0,
      difficultyCompleted: "",
      questsCompletedCount: 0,
      createdAt: serverTimestamp(),
      updatedAt: serverTimestamp(),
    })
  );
  await assertSucceeds(
    locRef.update({
      completed: true,
      stars: 3,
      bestScore: 1200,
      bestTimeSeconds: 48,
      difficultyCompleted: "detective",
      updatedAt: serverTimestamp(),
    })
  );

  // Clues are immutable after creation
  const clueRef = userRef.collection("clues").doc("clue_red_ribbon");
  await assertSucceeds(
    clueRef.set({
      userId: ALICE_UID,
      clueId: "clue_red_ribbon",
      locationId: "cottage",
      title: "Алая ленточка",
      description: "Зацепилась за куст у крыльца.",
      slivaNote: "След ведёт прямо к печке!",
      rarity: "rare",
      discoveredAt: serverTimestamp(),
    })
  );
  await assertFails(clueRef.update({ title: "Подмена" }));

  // Achievements are immutable after creation
  const achRef = userRef.collection("achievements").doc("ach_first_case");
  await assertSucceeds(
    achRef.set({
      userId: ALICE_UID,
      achievementId: "ach_first_case",
      title: "Первое дело",
      unlockedAt: serverTimestamp(),
    })
  );
  await assertFails(achRef.update({ title: "Подмена" }));

  // Quests progress and terminal state lock when rewardClaimed becomes true
  const questRef = userRef.collection("quests").doc("quest_cottage_clues");
  await assertSucceeds(
    questRef.set({
      userId: ALICE_UID,
      questId: "quest_cottage_clues",
      status: "active",
      currentProgress: 1,
      targetProgress: 3,
      rewardClaimed: false,
      createdAt: serverTimestamp(),
      updatedAt: serverTimestamp(),
    })
  );
  await assertSucceeds(
    questRef.update({
      currentProgress: 3,
      status: "completed",
      rewardClaimed: true,
      updatedAt: serverTimestamp(),
    })
  );
  // Locked once rewardClaimed == true
  await assertFails(
    questRef.update({
      currentProgress: 2,
      updatedAt: serverTimestamp(),
    })
  );
});
