package com.example.data.repository

import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.example.data.model.LocationProgress
import com.example.data.model.QuestProgress
import com.example.data.model.UnlockedAchievement
import kotlinx.coroutines.flow.Flow

interface DetectiveDataSource {
    fun observeProfile(userId: String): Flow<DetectiveProfile?>
    fun observeLocations(userId: String): Flow<List<LocationProgress>>
    fun observeClues(userId: String): Flow<List<CollectedClue>>
    fun observeAchievements(userId: String): Flow<List<UnlockedAchievement>>
    fun observeQuests(userId: String): Flow<List<QuestProgress>>

    suspend fun createInitialGameState(profile: DetectiveProfile): Result<Unit>
    suspend fun updateProfileFields(userId: String, fields: Map<String, Any>): Result<Unit>
    suspend fun upsertLocationProgress(userId: String, progress: LocationProgress): Result<Unit>
    suspend fun saveCollectedClue(userId: String, clue: CollectedClue): Result<Unit>
    suspend fun unlockAchievement(userId: String, achievementId: String, title: String): Result<Unit>
    suspend fun updateQuestProgress(
        userId: String,
        questId: String,
        newProgress: Long,
        targetProgress: Long
    ): Result<Unit>

    suspend fun claimQuestReward(
        userId: String,
        questId: String,
        targetProgress: Long,
        rewardScore: Long,
        rewardHints: Long,
        currentScore: Long,
        currentHints: Long
    ): Result<Unit>
}
