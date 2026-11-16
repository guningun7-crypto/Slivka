package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.example.data.model.GameCatalog
import com.example.data.model.LocationProgress
import com.example.data.model.QuestProgress
import com.example.data.model.UnlockedAchievement
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class DetectiveRepository(private val db: FirebaseFirestore) : DetectiveDataSource {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun userDoc(userId: String) = db.collection("users").document(userId)

    override fun observeProfile(userId: String): Flow<DetectiveProfile?> =
        userDoc(userId).snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(
                        DetectiveProfile::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                } else {
                    null
                }
            }
            .catch { error ->
                if (error !is CancellationException) {
                    handleFirestoreError(error, OperationType.GET, "users/$userId")
                }
                throw error
            }

    override fun observeLocations(userId: String): Flow<List<LocationProgress>> =
        userDoc(userId).collection("location_progress").snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    LocationProgress::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error !is CancellationException) {
                    handleFirestoreError(error, OperationType.LIST, "users/$userId/location_progress")
                }
                throw error
            }

    override fun observeClues(userId: String): Flow<List<CollectedClue>> =
        userDoc(userId).collection("clues").snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    CollectedClue::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error !is CancellationException) {
                    handleFirestoreError(error, OperationType.LIST, "users/$userId/clues")
                }
                throw error
            }

    override fun observeAchievements(userId: String): Flow<List<UnlockedAchievement>> =
        userDoc(userId).collection("achievements").snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    UnlockedAchievement::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error !is CancellationException) {
                    handleFirestoreError(error, OperationType.LIST, "users/$userId/achievements")
                }
                throw error
            }

    override fun observeQuests(userId: String): Flow<List<QuestProgress>> =
        userDoc(userId).collection("quests").snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    QuestProgress::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error !is CancellationException) {
                    handleFirestoreError(error, OperationType.LIST, "users/$userId/quests")
                }
                throw error
            }

    override suspend fun createInitialGameState(profile: DetectiveProfile): Result<Unit> {
        val uid = profile.userId
        val path = "users/$uid"
        return try {
            val batch = db.batch()
            batch.set(userDoc(uid), profile.toCreateMap())

            // Unlock cottage first; palace and forest initially locked until unlocked by progress
            GameCatalog.locations.forEachIndexed { idx, loc ->
                val locProg = LocationProgress(
                    userId = uid,
                    locationId = loc.id,
                    unlocked = idx == 0,
                    completed = false,
                    stars = 0L,
                    bestScore = 0L,
                    bestTimeSeconds = 0L,
                    difficultyCompleted = "",
                    questsCompletedCount = 0L
                )
                batch.set(
                    userDoc(uid).collection("location_progress").document(loc.id),
                    locProg.toCreateMap()
                )
            }

            // Initialize quests
            GameCatalog.quests.forEach { quest ->
                val q = QuestProgress(
                    userId = uid,
                    questId = quest.id,
                    status = "active",
                    currentProgress = 0L,
                    targetProgress = quest.targetProgress,
                    rewardClaimed = false
                )
                batch.set(
                    userDoc(uid).collection("quests").document(quest.id),
                    q.toCreateMap()
                )
            }

            // Grant starter achievement
            val starterAch = UnlockedAchievement(
                userId = uid,
                achievementId = "ach_rookie",
                title = "Юный стажёр"
            )
            batch.set(
                userDoc(uid).collection("achievements").document("ach_rookie"),
                starterAch.toCreateMap()
            )

            batch.commit().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, path)
            Result.failure(e)
        }
    }

    override suspend fun updateProfileFields(userId: String, fields: Map<String, Any>): Result<Unit> {
        val path = "users/$userId"
        return try {
            val payload = fields.toMutableMap().apply {
                put("updatedAt", FieldValue.serverTimestamp())
            }
            userDoc(userId).update(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, path)
            Result.failure(e)
        }
    }

    override suspend fun upsertLocationProgress(userId: String, progress: LocationProgress): Result<Unit> {
        val docRef = userDoc(userId).collection("location_progress").document(progress.locationId)
        return try {
            val existing = docRef.get().await()
            if (existing.exists()) {
                val updates = mapOf(
                    "unlocked" to progress.unlocked,
                    "completed" to progress.completed,
                    "stars" to progress.stars,
                    "bestScore" to progress.bestScore,
                    "bestTimeSeconds" to progress.bestTimeSeconds,
                    "difficultyCompleted" to progress.difficultyCompleted,
                    "questsCompletedCount" to progress.questsCompletedCount,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.update(updates).await()
            } else {
                docRef.set(progress.copy(userId = userId).toCreateMap()).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    override suspend fun saveCollectedClue(userId: String, clue: CollectedClue): Result<Unit> {
        val docRef = userDoc(userId).collection("clues").document(clue.clueId)
        return try {
            val existing = docRef.get().await()
            if (!existing.exists()) {
                docRef.set(clue.copy(userId = userId).toCreateMap()).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    override suspend fun unlockAchievement(userId: String, achievementId: String, title: String): Result<Unit> {
        val docRef = userDoc(userId).collection("achievements").document(achievementId)
        return try {
            val existing = docRef.get().await()
            if (!existing.exists()) {
                val item = UnlockedAchievement(
                    userId = userId,
                    achievementId = achievementId,
                    title = title
                )
                docRef.set(item.toCreateMap()).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    override suspend fun updateQuestProgress(
        userId: String,
        questId: String,
        newProgress: Long,
        targetProgress: Long
    ): Result<Unit> {
        val docRef = userDoc(userId).collection("quests").document(questId)
        return try {
            val existing = docRef.get().await()
            val clamped = newProgress.coerceAtMost(targetProgress)
            val status = if (clamped >= targetProgress) "completed" else "active"
            if (existing.exists()) {
                val alreadyClaimed = existing.getBoolean("rewardClaimed") == true
                if (!alreadyClaimed) {
                    docRef.update(
                        mapOf(
                            "currentProgress" to clamped,
                            "status" to status,
                            "updatedAt" to FieldValue.serverTimestamp()
                        )
                    ).await()
                }
            } else {
                val q = QuestProgress(
                    userId = userId,
                    questId = questId,
                    status = status,
                    currentProgress = clamped,
                    targetProgress = targetProgress,
                    rewardClaimed = false
                )
                docRef.set(q.toCreateMap()).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    override suspend fun claimQuestReward(
        userId: String,
        questId: String,
        targetProgress: Long,
        rewardScore: Long,
        rewardHints: Long,
        currentScore: Long,
        currentHints: Long
    ): Result<Unit> {
        val questRef = userDoc(userId).collection("quests").document(questId)
        return try {
            val existing = questRef.get().await()
            if (existing.exists() && existing.getBoolean("rewardClaimed") == true) {
                return Result.success(Unit)
            }
            val batch = db.batch()
            batch.update(
                questRef,
                mapOf(
                    "currentProgress" to targetProgress,
                    "status" to "completed",
                    "rewardClaimed" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            batch.update(
                userDoc(userId),
                mapOf(
                    "totalScore" to currentScore + rewardScore,
                    "hintsBalance" to currentHints + rewardHints,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            batch.commit().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, questRef.path)
            Result.failure(e)
        }
    }
}
