package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class DetectiveProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val gender: String = "girl",
    val skinToneIndex: Long = 1L,
    val hairStyleIndex: Long = 0L,
    val hairColorIndex: Long = 1L,
    val hatId: String = "cap",
    val coatId: String = "plum",
    val accessoryId: String = "loupe",
    val selectedDifficulty: String = "detective",
    val totalScore: Long = 0L,
    val totalStars: Long = 0L,
    val hintsBalance: Long = 3L,
    val onboardingCompleted: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun toCreateMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "name" to name,
        "gender" to gender,
        "skinToneIndex" to skinToneIndex,
        "hairStyleIndex" to hairStyleIndex,
        "hairColorIndex" to hairColorIndex,
        "hatId" to hatId,
        "coatId" to coatId,
        "accessoryId" to accessoryId,
        "selectedDifficulty" to selectedDifficulty,
        "totalScore" to totalScore,
        "totalStars" to totalStars,
        "hintsBalance" to hintsBalance,
        "onboardingCompleted" to onboardingCompleted,
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )
}

@IgnoreExtraProperties
data class LocationProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val locationId: String = "",
    val unlocked: Boolean = false,
    val completed: Boolean = false,
    val stars: Long = 0L,
    val bestScore: Long = 0L,
    val bestTimeSeconds: Long = 0L,
    val difficultyCompleted: String = "",
    val questsCompletedCount: Long = 0L,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun toCreateMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "locationId" to locationId,
        "unlocked" to unlocked,
        "completed" to completed,
        "stars" to stars,
        "bestScore" to bestScore,
        "bestTimeSeconds" to bestTimeSeconds,
        "difficultyCompleted" to difficultyCompleted,
        "questsCompletedCount" to questsCompletedCount,
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )
}

@IgnoreExtraProperties
data class CollectedClue(
    @DocumentId val id: String = "",
    val userId: String = "",
    val clueId: String = "",
    val locationId: String = "",
    val title: String = "",
    val description: String = "",
    val slivaNote: String = "",
    val rarity: String = "common",
    @ServerTimestamp val discoveredAt: Timestamp? = null
) {
    fun toCreateMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "clueId" to clueId,
        "locationId" to locationId,
        "title" to title,
        "description" to description,
        "slivaNote" to slivaNote,
        "rarity" to rarity,
        "discoveredAt" to FieldValue.serverTimestamp()
    )
}

@IgnoreExtraProperties
data class UnlockedAchievement(
    @DocumentId val id: String = "",
    val userId: String = "",
    val achievementId: String = "",
    val title: String = "",
    @ServerTimestamp val unlockedAt: Timestamp? = null
) {
    fun toCreateMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "achievementId" to achievementId,
        "title" to title,
        "unlockedAt" to FieldValue.serverTimestamp()
    )
}

@IgnoreExtraProperties
data class QuestProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val questId: String = "",
    val status: String = "active",
    val currentProgress: Long = 0L,
    val targetProgress: Long = 1L,
    val rewardClaimed: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun toCreateMap(): Map<String, Any> = mapOf(
        "userId" to userId,
        "questId" to questId,
        "status" to status,
        "currentProgress" to currentProgress,
        "targetProgress" to targetProgress,
        "rewardClaimed" to rewardClaimed,
        "createdAt" to FieldValue.serverTimestamp(),
        "updatedAt" to FieldValue.serverTimestamp()
    )
}
