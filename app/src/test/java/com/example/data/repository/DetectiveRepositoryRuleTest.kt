package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class DetectiveRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun createInitialGameState_validProfile_savesAndObservesProfileAndLocations() = runBlocking {
        val uid = signInTestUser("alice_${UUID.randomUUID()}@test.com")
        val repository = DetectiveRepository(firestore)

        val profile = DetectiveProfile(
            userId = uid,
            name = "Алиса",
            gender = "girl",
            onboardingCompleted = true
        )

        val createResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.createInitialGameState(profile)
        }
        assertTrue(createResult.isSuccess)

        val observed = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeProfile(uid).first { it != null }
        }
        assertNotNull(observed)
        assertEquals("Алиса", observed?.name)

        val locations = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeLocations(uid).first { it.size == 3 }
        }
        assertTrue(locations.any { it.locationId == "cottage" && it.unlocked })
    }

    @Test
    fun saveCollectedClue_authenticatedOwner_persistsClueInInventory() = runBlocking {
        val uid = signInTestUser("clue_${UUID.randomUUID()}@test.com")
        val repository = DetectiveRepository(firestore)

        val profile = DetectiveProfile(userId = uid, name = "Максим", gender = "boy", onboardingCompleted = true)
        withTimeout(DEFAULT_TIMEOUT_MS) { repository.createInitialGameState(profile).getOrThrow() }

        val clueId = "clue_${UUID.randomUUID().toString().take(8)}"
        val clue = CollectedClue(
            userId = uid,
            clueId = clueId,
            locationId = "cottage",
            title = "Алая ленточка",
            description = "Шёлковая лента с блёстками.",
            slivaNote = "След ведёт во дворец.",
            rarity = "rare"
        )
        val saveResult = withTimeout(DEFAULT_TIMEOUT_MS) { repository.saveCollectedClue(uid, clue) }
        assertTrue(saveResult.isSuccess)

        val clues = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeClues(uid).first { list -> list.any { it.clueId == clueId } }
        }
        assertTrue(clues.any { it.clueId == clueId })
    }

    @Test
    fun crossUserAccess_readingAnotherUsersClues_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser("alice_cross_${UUID.randomUUID()}@test.com")
        val aliceRepo = DetectiveRepository(firestore)
        withTimeout(DEFAULT_TIMEOUT_MS) {
            aliceRepo.createInitialGameState(
                DetectiveProfile(userId = aliceUid, name = "Алиса", gender = "girl", onboardingCompleted = true)
            ).getOrThrow()
        }

        signInTestUser("bob_cross_${UUID.randomUUID()}@test.com")
        var caught: Throwable? = null
        try {
            withTimeout(DEFAULT_TIMEOUT_MS) {
                firestore.collection("users").document(aliceUid).collection("clues").get().await()
            }
        } catch (t: Throwable) {
            caught = t
        }
        val firestoreEx = caught?.unwrapFirestoreException()
        assertNotNull("Expected FirebaseFirestoreException for cross-user access", firestoreEx)
        assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreEx?.code)
    }

    @Test
    fun observeClues_unauthenticatedUser_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser("unauth_${UUID.randomUUID()}@test.com")
        auth.signOut()
        val repository = DetectiveRepository(firestore)

        var caught: Throwable? = null
        try {
            withTimeout(FLOW_TIMEOUT_MS) {
                repository.observeClues(aliceUid).first()
            }
        } catch (t: Throwable) {
            caught = t
        }
        val firestoreEx = caught?.unwrapFirestoreException()
        assertNotNull("Expected nested FirebaseFirestoreException, got $caught", firestoreEx)
        assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreEx?.code)
    }

    private companion object {
        const val DEFAULT_TIMEOUT_MS = 5000L
        const val FLOW_TIMEOUT_MS = 3000L
    }
}
