package com.example.data.model

data class DetectiveProfile(
    val id: String = "",
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
    val hintsBalance: Long = 2L,
    val onboardingCompleted: Boolean = false
)

data class LocationProgress(
    val id: String = "",
    val userId: String = "",
    val locationId: String = "",
    val unlocked: Boolean = false,
    val completed: Boolean = false,
    val stars: Long = 0L,
    val bestScore: Long = 0L,
    val bestTimeSeconds: Long = 0L,
    val difficultyCompleted: String = "",
    val questsCompletedCount: Long = 0L
)

data class CollectedClue(
    val id: String = "",
    val userId: String = "",
    val clueId: String = "",
    val locationId: String = "",
    val title: String = "",
    val description: String = "",
    val slivaNote: String = "",
    val rarity: String = "common"
)

data class UnlockedAchievement(
    val id: String = "",
    val userId: String = "",
    val achievementId: String = "",
    val title: String = ""
)

data class QuestProgress(
    val id: String = "",
    val userId: String = "",
    val questId: String = "",
    val status: String = "active",
    val currentProgress: Long = 0L,
    val targetProgress: Long = 1L,
    val rewardClaimed: Boolean = false
)
