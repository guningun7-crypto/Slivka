package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.example.data.model.GameCatalog
import com.example.data.model.LocationProgress
import com.example.data.model.QuestProgress
import com.example.data.model.UnlockedAchievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class LocalGuestRepository(context: Context) : DetectiveDataSource {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _profile = MutableStateFlow(readProfile())
    private val _locations = MutableStateFlow(mergeLocationsWithCatalog(readLocations()))
    private val _clues = MutableStateFlow(readClues())
    private val _achievements = MutableStateFlow(readAchievements())
    private val _quests = MutableStateFlow(mergeQuestsWithCatalog(readQuests()))

    override fun observeProfile(userId: String): Flow<DetectiveProfile?> = _profile.asStateFlow()
    override fun observeLocations(userId: String): Flow<List<LocationProgress>> = _locations.asStateFlow()
    override fun observeClues(userId: String): Flow<List<CollectedClue>> = _clues.asStateFlow()
    override fun observeAchievements(userId: String): Flow<List<UnlockedAchievement>> = _achievements.asStateFlow()
    override fun observeQuests(userId: String): Flow<List<QuestProgress>> = _quests.asStateFlow()

    override suspend fun createInitialGameState(profile: DetectiveProfile): Result<Unit> {
        val initialLocations = GameCatalog.locations.mapIndexed { idx, loc ->
            LocationProgress(
                id = loc.id,
                userId = profile.userId,
                locationId = loc.id,
                unlocked = idx == 0,
                completed = false,
                stars = 0L,
                bestScore = 0L,
                bestTimeSeconds = 0L,
                difficultyCompleted = "",
                questsCompletedCount = 0L
            )
        }
        val initialQuests = GameCatalog.quests.map { q ->
            QuestProgress(
                id = q.id,
                userId = profile.userId,
                questId = q.id,
                status = "active",
                currentProgress = 0L,
                targetProgress = q.targetProgress,
                rewardClaimed = false
            )
        }
        val initialAchievements = listOf(
            UnlockedAchievement(
                id = "ach_rookie",
                userId = profile.userId,
                achievementId = "ach_rookie",
                title = "Юный стажёр"
            )
        )

        writeProfile(profile.copy(id = profile.userId))
        writeLocations(initialLocations)
        writeClues(emptyList())
        writeQuests(initialQuests)
        writeAchievements(initialAchievements)

        _profile.value = profile.copy(id = profile.userId)
        _locations.value = initialLocations
        _clues.value = emptyList()
        _quests.value = initialQuests
        _achievements.value = initialAchievements
        return Result.success(Unit)
    }

    override suspend fun updateProfileFields(userId: String, fields: Map<String, Any>): Result<Unit> {
        val current = _profile.value ?: return Result.success(Unit)
        val updated = current.copy(
            name = (fields["name"] as? String) ?: current.name,
            gender = (fields["gender"] as? String) ?: current.gender,
            skinToneIndex = (fields["skinToneIndex"] as? Number)?.toLong() ?: current.skinToneIndex,
            hairStyleIndex = (fields["hairStyleIndex"] as? Number)?.toLong() ?: current.hairStyleIndex,
            hairColorIndex = (fields["hairColorIndex"] as? Number)?.toLong() ?: current.hairColorIndex,
            hatId = (fields["hatId"] as? String) ?: current.hatId,
            coatId = (fields["coatId"] as? String) ?: current.coatId,
            accessoryId = (fields["accessoryId"] as? String) ?: current.accessoryId,
            selectedDifficulty = (fields["selectedDifficulty"] as? String) ?: current.selectedDifficulty,
            totalScore = (fields["totalScore"] as? Number)?.toLong() ?: current.totalScore,
            totalStars = (fields["totalStars"] as? Number)?.toLong() ?: current.totalStars,
            hintsBalance = (fields["hintsBalance"] as? Number)?.toLong() ?: current.hintsBalance,
            onboardingCompleted = (fields["onboardingCompleted"] as? Boolean) ?: current.onboardingCompleted
        )
        writeProfile(updated)
        _profile.value = updated
        return Result.success(Unit)
    }

    override suspend fun upsertLocationProgress(userId: String, progress: LocationProgress): Result<Unit> {
        val list = _locations.value.toMutableList()
        val idx = list.indexOfFirst { it.locationId == progress.locationId }
        val normalized = progress.copy(id = progress.locationId, userId = userId)
        if (idx >= 0) {
            list[idx] = normalized
        } else {
            list.add(normalized)
        }
        writeLocations(list)
        _locations.value = list
        return Result.success(Unit)
    }

    override suspend fun saveCollectedClue(userId: String, clue: CollectedClue): Result<Unit> {
        val list = _clues.value.toMutableList()
        if (list.none { it.clueId == clue.clueId }) {
            list.add(clue.copy(id = clue.clueId, userId = userId))
            writeClues(list)
            _clues.value = list
        }
        return Result.success(Unit)
    }

    override suspend fun unlockAchievement(userId: String, achievementId: String, title: String): Result<Unit> {
        val list = _achievements.value.toMutableList()
        if (list.none { it.achievementId == achievementId }) {
            list.add(
                UnlockedAchievement(
                    id = achievementId,
                    userId = userId,
                    achievementId = achievementId,
                    title = title
                )
            )
            writeAchievements(list)
            _achievements.value = list
        }
        return Result.success(Unit)
    }

    override suspend fun updateQuestProgress(
        userId: String,
        questId: String,
        newProgress: Long,
        targetProgress: Long
    ): Result<Unit> {
        val list = _quests.value.toMutableList()
        val clamped = newProgress.coerceAtMost(targetProgress)
        val status = if (clamped >= targetProgress) "completed" else "active"
        val idx = list.indexOfFirst { it.questId == questId }
        if (idx >= 0) {
            val existing = list[idx]
            if (!existing.rewardClaimed) {
                list[idx] = existing.copy(currentProgress = clamped, status = status, targetProgress = targetProgress)
            }
        } else {
            list.add(
                QuestProgress(
                    id = questId,
                    userId = userId,
                    questId = questId,
                    status = status,
                    currentProgress = clamped,
                    targetProgress = targetProgress,
                    rewardClaimed = false
                )
            )
        }
        writeQuests(list)
        _quests.value = list
        return Result.success(Unit)
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
        val list = _quests.value.toMutableList()
        val idx = list.indexOfFirst { it.questId == questId }
        if (idx >= 0 && list[idx].rewardClaimed) return Result.success(Unit)
        if (idx >= 0) {
            list[idx] = list[idx].copy(
                currentProgress = targetProgress,
                status = "completed",
                rewardClaimed = true
            )
            writeQuests(list)
            _quests.value = list
        }
        updateProfileFields(
            userId,
            mapOf(
                "totalScore" to currentScore + rewardScore,
                "hintsBalance" to currentHints + rewardHints
            )
        )
        return Result.success(Unit)
    }

    private fun mergeLocationsWithCatalog(saved: List<LocationProgress>): List<LocationProgress> {
        if (saved.isEmpty()) return saved
        val byId = saved.associateBy { it.locationId }.toMutableMap()
        val userId = saved.firstOrNull()?.userId ?: "guest_local"
        var changed = false

        GameCatalog.locations.forEachIndexed { idx, loc ->
            val prevCompleted = idx > 0 && byId[GameCatalog.locations[idx - 1].id]?.completed == true
            val existing = byId[loc.id]
            if (existing == null) {
                byId[loc.id] = LocationProgress(
                    id = loc.id,
                    userId = userId,
                    locationId = loc.id,
                    unlocked = idx == 0 || prevCompleted
                )
                changed = true
            } else if (prevCompleted && !existing.unlocked) {
                byId[loc.id] = existing.copy(unlocked = true)
                changed = true
            }
        }

        val ordered = GameCatalog.locations.mapNotNull { byId[it.id] }
        if (changed) writeLocations(ordered)
        return ordered
    }

    private fun mergeQuestsWithCatalog(saved: List<QuestProgress>): List<QuestProgress> {
        if (saved.isEmpty()) return saved
        val byId = saved.associateBy { it.questId }.toMutableMap()
        val userId = saved.firstOrNull()?.userId ?: "guest_local"
        var changed = false

        GameCatalog.quests.forEach { quest ->
            if (!byId.containsKey(quest.id)) {
                byId[quest.id] = QuestProgress(
                    id = quest.id,
                    userId = userId,
                    questId = quest.id,
                    status = "active",
                    currentProgress = 0L,
                    targetProgress = quest.targetProgress,
                    rewardClaimed = false
                )
                changed = true
            }
        }

        val ordered = GameCatalog.quests.mapNotNull { byId[it.id] }
        if (changed) writeQuests(ordered)
        return ordered
    }

    private fun readProfile(): DetectiveProfile? {
        val raw = prefs.getString(KEY_PROFILE, null) ?: return null
        return try {
            val o = JSONObject(raw)
            DetectiveProfile(
                id = o.optString("userId", "guest_local"),
                userId = o.optString("userId", "guest_local"),
                name = o.optString("name", ""),
                gender = o.optString("gender", "girl"),
                skinToneIndex = o.optLong("skinToneIndex", 1L),
                hairStyleIndex = o.optLong("hairStyleIndex", 0L),
                hairColorIndex = o.optLong("hairColorIndex", 1L),
                hatId = o.optString("hatId", "cap"),
                coatId = o.optString("coatId", "plum"),
                accessoryId = o.optString("accessoryId", "loupe"),
                selectedDifficulty = o.optString("selectedDifficulty", "detective"),
                totalScore = o.optLong("totalScore", 0L),
                totalStars = o.optLong("totalStars", 0L),
                hintsBalance = o.optLong("hintsBalance", 2L),
                onboardingCompleted = o.optBoolean("onboardingCompleted", false)
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun writeProfile(p: DetectiveProfile) {
        val o = JSONObject().apply {
            put("userId", p.userId)
            put("name", p.name)
            put("gender", p.gender)
            put("skinToneIndex", p.skinToneIndex)
            put("hairStyleIndex", p.hairStyleIndex)
            put("hairColorIndex", p.hairColorIndex)
            put("hatId", p.hatId)
            put("coatId", p.coatId)
            put("accessoryId", p.accessoryId)
            put("selectedDifficulty", p.selectedDifficulty)
            put("totalScore", p.totalScore)
            put("totalStars", p.totalStars)
            put("hintsBalance", p.hintsBalance)
            put("onboardingCompleted", p.onboardingCompleted)
        }
        prefs.edit().putString(KEY_PROFILE, o.toString()).apply()
    }

    private fun readLocations(): List<LocationProgress> {
        val raw = prefs.getString(KEY_LOCATIONS, null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val locId = o.getString("locationId")
                    add(
                        LocationProgress(
                            id = locId,
                            userId = o.optString("userId", "guest_local"),
                            locationId = locId,
                            unlocked = o.optBoolean("unlocked", false),
                            completed = o.optBoolean("completed", false),
                            stars = o.optLong("stars", 0L),
                            bestScore = o.optLong("bestScore", 0L),
                            bestTimeSeconds = o.optLong("bestTimeSeconds", 0L),
                            difficultyCompleted = o.optString("difficultyCompleted", ""),
                            questsCompletedCount = o.optLong("questsCompletedCount", 0L)
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun writeLocations(list: List<LocationProgress>) {
        val arr = JSONArray()
        list.forEach { l ->
            arr.put(JSONObject().apply {
                put("userId", l.userId)
                put("locationId", l.locationId)
                put("unlocked", l.unlocked)
                put("completed", l.completed)
                put("stars", l.stars)
                put("bestScore", l.bestScore)
                put("bestTimeSeconds", l.bestTimeSeconds)
                put("difficultyCompleted", l.difficultyCompleted)
                put("questsCompletedCount", l.questsCompletedCount)
            })
        }
        prefs.edit().putString(KEY_LOCATIONS, arr.toString()).apply()
    }

    private fun readClues(): List<CollectedClue> {
        val raw = prefs.getString(KEY_CLUES, null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val cid = o.getString("clueId")
                    add(
                        CollectedClue(
                            id = cid,
                            userId = o.optString("userId", "guest_local"),
                            clueId = cid,
                            locationId = o.getString("locationId"),
                            title = o.getString("title"),
                            description = o.getString("description"),
                            slivaNote = o.getString("slivaNote"),
                            rarity = o.optString("rarity", "common")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun writeClues(list: List<CollectedClue>) {
        val arr = JSONArray()
        list.forEach { c ->
            arr.put(JSONObject().apply {
                put("userId", c.userId)
                put("clueId", c.clueId)
                put("locationId", c.locationId)
                put("title", c.title)
                put("description", c.description)
                put("slivaNote", c.slivaNote)
                put("rarity", c.rarity)
            })
        }
        prefs.edit().putString(KEY_CLUES, arr.toString()).apply()
    }

    private fun readAchievements(): List<UnlockedAchievement> {
        val raw = prefs.getString(KEY_ACHIEVEMENTS, null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val aid = o.getString("achievementId")
                    add(
                        UnlockedAchievement(
                            id = aid,
                            userId = o.optString("userId", "guest_local"),
                            achievementId = aid,
                            title = o.getString("title")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun writeAchievements(list: List<UnlockedAchievement>) {
        val arr = JSONArray()
        list.forEach { a ->
            arr.put(JSONObject().apply {
                put("userId", a.userId)
                put("achievementId", a.achievementId)
                put("title", a.title)
            })
        }
        prefs.edit().putString(KEY_ACHIEVEMENTS, arr.toString()).apply()
    }

    private fun readQuests(): List<QuestProgress> {
        val raw = prefs.getString(KEY_QUESTS, null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val qid = o.getString("questId")
                    add(
                        QuestProgress(
                            id = qid,
                            userId = o.optString("userId", "guest_local"),
                            questId = qid,
                            status = o.optString("status", "active"),
                            currentProgress = o.optLong("currentProgress", 0L),
                            targetProgress = o.optLong("targetProgress", 1L),
                            rewardClaimed = o.optBoolean("rewardClaimed", false)
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun writeQuests(list: List<QuestProgress>) {
        val arr = JSONArray()
        list.forEach { q ->
            arr.put(JSONObject().apply {
                put("userId", q.userId)
                put("questId", q.questId)
                put("status", q.status)
                put("currentProgress", q.currentProgress)
                put("targetProgress", q.targetProgress)
                put("rewardClaimed", q.rewardClaimed)
            })
        }
        prefs.edit().putString(KEY_QUESTS, arr.toString()).apply()
    }

    private companion object {
        const val PREFS_NAME = "fairy_detective_guest_prefs"
        const val KEY_PROFILE = "guest_profile"
        const val KEY_LOCATIONS = "guest_locations"
        const val KEY_CLUES = "guest_clues"
        const val KEY_ACHIEVEMENTS = "guest_achievements"
        const val KEY_QUESTS = "guest_quests"
    }
}
