package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.example.data.model.DifficultyLevel
import com.example.data.model.FairyLocationDef
import com.example.data.model.GameCatalog
import com.example.data.model.HiddenObjectDef
import com.example.data.model.LocationProgress
import com.example.data.model.QuestProgress
import com.example.data.model.UnlockedAchievement
import com.example.data.repository.DetectiveDataSource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface GameUiState {
    data object Loading : GameUiState
    data class Error(val message: String) : GameUiState
    data class Ready(
        val profile: DetectiveProfile?,
        val locations: Map<String, LocationProgress>,
        val clues: List<CollectedClue>,
        val achievements: Map<String, UnlockedAchievement>,
        val quests: Map<String, QuestProgress>
    ) : GameUiState
}

data class ActiveSceneState(
    val location: FairyLocationDef,
    val difficulty: DifficultyLevel,
    val targetItems: List<HiddenObjectDef>,
    val foundItemIds: Set<String> = emptySet(),
    val mistakesCount: Int = 0,
    val secondsRemaining: Int = difficulty.timeLimitSeconds,
    val hintedItemId: String? = null,
    val slivaBannerText: String? = null,
    val newlyDiscoveredClueTitle: String? = null,
    val phase: ScenePhase = ScenePhase.INTRO_DIALOGUE,
    val dialogueIndex: Int = 0,
    val earnedScore: Long = 0L,
    val earnedStars: Int = 0,
    val isVictory: Boolean = false
)

enum class ScenePhase {
    INTRO_DIALOGUE,
    SEARCHING,
    OUTRO_DIALOGUE,
    RESULTS
}

class DetectiveViewModel(
    private val repository: DetectiveDataSource,
    private val currentUserId: String
) : ViewModel() {

    val uiState: StateFlow<GameUiState> = combine(
        repository.observeProfile(currentUserId),
        repository.observeLocations(currentUserId),
        repository.observeClues(currentUserId),
        repository.observeAchievements(currentUserId),
        repository.observeQuests(currentUserId)
    ) { profile, locations, clues, achievements, quests ->
        GameUiState.Ready(
            profile = profile,
            locations = locations.associateBy { it.locationId },
            clues = clues,
            achievements = achievements.associateBy { it.achievementId },
            quests = quests.associateBy { it.questId }
        ) as GameUiState
    }.catch { error ->
        val firestoreError = generateSequence(error) { it.cause }
            .filterIsInstance<FirebaseFirestoreException>()
            .firstOrNull()
        if (firestoreError == null && error is CancellationException) throw error
        val rootError = firestoreError ?: error
        Log.w(TAG, "Error observing detective state", rootError)
        emit(GameUiState.Error(rootError.localizedMessage ?: "Не удалось загрузить данные детектива"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = GameUiState.Loading
    )

    private val _activeScene = MutableStateFlow<ActiveSceneState?>(null)
    val activeScene: StateFlow<ActiveSceneState?> = _activeScene.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private var timerRunning = false

    fun clearToast() {
        _toastMessage.value = null
    }

    fun completeOnboarding(
        name: String,
        gender: String,
        skinToneIndex: Int,
        hairStyleIndex: Int,
        hairColorIndex: Int,
        hatId: String,
        coatId: String,
        accessoryId: String
    ) {
        val cleanName = name.trim().ifEmpty { "Юный сыщик" }.take(30)
        val profile = DetectiveProfile(
            userId = currentUserId,
            name = cleanName,
            gender = gender,
            skinToneIndex = skinToneIndex.toLong(),
            hairStyleIndex = hairStyleIndex.toLong(),
            hairColorIndex = hairColorIndex.toLong(),
            hatId = hatId,
            coatId = coatId,
            accessoryId = accessoryId,
            selectedDifficulty = DifficultyLevel.DETECTIVE.id,
            totalScore = 0L,
            totalStars = 0L,
            hintsBalance = 3L,
            onboardingCompleted = true
        )
        viewModelScope.launch {
            repository.createInitialGameState(profile)
                .onSuccess {
                    _toastMessage.value = "Добро пожаловать в школу, $cleanName!"
                }
                .onFailure {
                    _toastMessage.value = "Ошибка сохранения профиля: ${it.localizedMessage}"
                }
        }
    }

    fun updateCustomization(
        name: String,
        gender: String,
        skinToneIndex: Int,
        hairStyleIndex: Int,
        hairColorIndex: Int,
        hatId: String,
        coatId: String,
        accessoryId: String
    ) {
        val cleanName = name.trim().ifEmpty { "Юный сыщик" }.take(30)
        viewModelScope.launch {
            repository.updateProfileFields(
                currentUserId,
                mapOf(
                    "name" to cleanName,
                    "gender" to gender,
                    "skinToneIndex" to skinToneIndex.toLong(),
                    "hairStyleIndex" to hairStyleIndex.toLong(),
                    "hairColorIndex" to hairColorIndex.toLong(),
                    "hatId" to hatId,
                    "coatId" to coatId,
                    "accessoryId" to accessoryId
                )
            ).onSuccess {
                repository.unlockAchievement(currentUserId, "ach_stylist", "Безупречный стиль")
                _toastMessage.value = "Образ детектива обновлён!"
            }
        }
    }

    fun setDifficulty(difficulty: DifficultyLevel) {
        viewModelScope.launch {
            repository.updateProfileFields(
                currentUserId,
                mapOf("selectedDifficulty" to difficulty.id)
            )
        }
    }

    fun claimQuestReward(questId: String) {
        val ready = uiState.value as? GameUiState.Ready ?: return
        val profile = ready.profile ?: return
        val questDef = GameCatalog.quests.find { it.id == questId } ?: return
        val progress = ready.quests[questId] ?: return
        if (progress.rewardClaimed || progress.currentProgress < questDef.targetProgress) return

        viewModelScope.launch {
            repository.claimQuestReward(
                userId = currentUserId,
                questId = questId,
                targetProgress = questDef.targetProgress,
                rewardScore = questDef.rewardScore,
                rewardHints = questDef.rewardHints,
                currentScore = profile.totalScore,
                currentHints = profile.hintsBalance
            ).onSuccess {
                _toastMessage.value = "Награда получена: ${questDef.rewardLabel}!"
            }
        }
    }

    fun startLocation(locationId: String, difficulty: DifficultyLevel) {
        val loc = GameCatalog.locationById(locationId)
        // Prioritize clue items first so every run lets player discover clues!
        val cluesFirst = loc.items.sortedByDescending { it.clueInfo != null }
        val targetItems = cluesFirst.take(difficulty.objectsToFind)
        _activeScene.value = ActiveSceneState(
            location = loc,
            difficulty = difficulty,
            targetItems = targetItems,
            secondsRemaining = difficulty.timeLimitSeconds,
            phase = ScenePhase.INTRO_DIALOGUE,
            dialogueIndex = 0
        )
    }

    fun advanceIntroDialogue() {
        val scene = _activeScene.value ?: return
        if (scene.dialogueIndex + 1 < scene.location.introDialogue.size) {
            _activeScene.value = scene.copy(dialogueIndex = scene.dialogueIndex + 1)
        } else {
            _activeScene.value = scene.copy(
                phase = ScenePhase.SEARCHING,
                slivaBannerText = "Удачи, сыщик! Ищи предметы из списка внизу."
            )
            startSceneTimer()
        }
    }

    fun skipIntroDialogue() {
        val scene = _activeScene.value ?: return
        _activeScene.value = scene.copy(
            phase = ScenePhase.SEARCHING,
            slivaBannerText = "Осмотр места происшествия начался!"
        )
        startSceneTimer()
    }

    private fun startSceneTimer() {
        if (timerRunning) return
        timerRunning = true
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                val current = _activeScene.value
                if (current == null || current.phase != ScenePhase.SEARCHING) {
                    timerRunning = false
                    break
                }
                val nextSeconds = (current.secondsRemaining - 1).coerceAtLeast(0)
                if (nextSeconds == 0) {
                    timerRunning = false
                    _activeScene.value = current.copy(
                        secondsRemaining = 0,
                        phase = ScenePhase.RESULTS,
                        isVictory = false,
                        earnedScore = 0L,
                        earnedStars = 0
                    )
                    break
                } else {
                    _activeScene.value = current.copy(secondsRemaining = nextSeconds)
                }
            }
        }
    }

    fun onItemTapped(itemId: String) {
        val scene = _activeScene.value ?: return
        if (scene.phase != ScenePhase.SEARCHING) return
        if (itemId in scene.foundItemIds) return

        val foundItem = scene.targetItems.find { it.id == itemId } ?: return
        val newFound = scene.foundItemIds + itemId

        // Persist clue immediately if this object carries a special clue
        val clueInfo = foundItem.clueInfo
        if (clueInfo != null) {
            viewModelScope.launch {
                val collected = CollectedClue(
                    userId = currentUserId,
                    clueId = clueInfo.clueId,
                    locationId = scene.location.id,
                    title = clueInfo.title,
                    description = clueInfo.description,
                    slivaNote = clueInfo.slivaNote,
                    rarity = clueInfo.rarity
                )
                repository.saveCollectedClue(currentUserId, collected)
                syncClueQuestsAndAchievements()
            }
        }

        val slivaMsg = if (clueInfo != null) {
            "Мур-р! Найдена улика «${clueInfo.title}» — мои усы сразу её учуяли!"
        } else {
            "Мяу-отлично! «${foundItem.name}» найден."
        }

        val clearedHint = if (scene.hintedItemId == itemId) null else scene.hintedItemId

        if (newFound.size >= scene.targetItems.size) {
            timerRunning = false
            val timeUsed = (scene.difficulty.timeLimitSeconds - scene.secondsRemaining).coerceAtLeast(1)
            val baseScore = (newFound.size * 100L) + (scene.secondsRemaining * 3L) - (scene.mistakesCount * 20L)
            val finalScore = (baseScore.coerceAtLeast(100L) * scene.difficulty.scoreMultiplier).toLong()
            val stars = when {
                scene.mistakesCount <= 1 && scene.secondsRemaining >= scene.difficulty.timeLimitSeconds / 3 -> 3
                scene.mistakesCount <= 4 -> 2
                else -> 1
            }

            _activeScene.value = scene.copy(
                foundItemIds = newFound,
                hintedItemId = clearedHint,
                slivaBannerText = slivaMsg,
                newlyDiscoveredClueTitle = clueInfo?.title,
                phase = ScenePhase.OUTRO_DIALOGUE,
                dialogueIndex = 0,
                earnedScore = finalScore,
                earnedStars = stars,
                isVictory = true
            )
            persistVictory(scene, finalScore, stars, timeUsed)
        } else {
            _activeScene.value = scene.copy(
                foundItemIds = newFound,
                hintedItemId = clearedHint,
                slivaBannerText = slivaMsg,
                newlyDiscoveredClueTitle = clueInfo?.title
            )
        }
    }

    fun onEmptySceneTapped() {
        val scene = _activeScene.value ?: return
        if (scene.phase != ScenePhase.SEARCHING) return
        val penalty = scene.difficulty.missPenaltySeconds
        val updatedSec = (scene.secondsRemaining - penalty).coerceAtLeast(1)
        _activeScene.value = scene.copy(
            mistakesCount = scene.mistakesCount + 1,
            secondsRemaining = updatedSec,
            slivaBannerText = if (penalty > 0) {
                "Фыр! Мимо! Кошачья осторожность не помешает — штраф -${penalty} сек."
            } else {
                "Там только пылинки, присмотрись получше!"
            }
        )
    }

    fun useHint() {
        val scene = _activeScene.value ?: return
        if (scene.phase != ScenePhase.SEARCHING) return
        val ready = uiState.value as? GameUiState.Ready ?: return
        val profile = ready.profile ?: return

        if (profile.hintsBalance <= 0L) {
            _activeScene.value = scene.copy(
                slivaBannerText = "Подсказки закончились! Выполняй квесты Сливы Владиславовны, чтобы получить ещё."
            )
            return
        }

        val unfound = scene.targetItems.firstOrNull { it.id !in scene.foundItemIds } ?: return
        _activeScene.value = scene.copy(
            hintedItemId = unfound.id,
            slivaBannerText = "Слива Владиславовна: «${unfound.hintText}»"
        )
        viewModelScope.launch {
            repository.updateProfileFields(
                currentUserId,
                mapOf("hintsBalance" to (profile.hintsBalance - 1L).coerceAtLeast(0L))
            )
        }
    }

    fun advanceOutroDialogue() {
        val scene = _activeScene.value ?: return
        if (scene.dialogueIndex + 1 < scene.location.outroDialogue.size) {
            _activeScene.value = scene.copy(dialogueIndex = scene.dialogueIndex + 1)
        } else {
            _activeScene.value = scene.copy(phase = ScenePhase.RESULTS)
        }
    }

    fun exitActiveScene() {
        timerRunning = false
        _activeScene.value = null
    }

    private fun persistVictory(
        scene: ActiveSceneState,
        earnedScore: Long,
        stars: Int,
        timeUsedSeconds: Int
    ) {
        val ready = uiState.value as? GameUiState.Ready ?: return
        val profile = ready.profile ?: return
        val existingLoc = ready.locations[scene.location.id]

        val bestStars = maxOf(existingLoc?.stars ?: 0L, stars.toLong())
        val bestScore = maxOf(existingLoc?.bestScore ?: 0L, earnedScore)
        val bestTime = if ((existingLoc?.bestTimeSeconds ?: 0L) == 0L) {
            timeUsedSeconds.toLong()
        } else {
            minOf(existingLoc!!.bestTimeSeconds, timeUsedSeconds.toLong())
        }

        viewModelScope.launch {
            repository.upsertLocationProgress(
                currentUserId,
                LocationProgress(
                    userId = currentUserId,
                    locationId = scene.location.id,
                    unlocked = true,
                    completed = true,
                    stars = bestStars,
                    bestScore = bestScore,
                    bestTimeSeconds = bestTime,
                    difficultyCompleted = scene.difficulty.id,
                    questsCompletedCount = existingLoc?.questsCompletedCount ?: 0L
                )
            )

            // Unlock next fairy-tale location on the map
            val idx = GameCatalog.locations.indexOfFirst { it.id == scene.location.id }
            if (idx >= 0 && idx + 1 < GameCatalog.locations.size) {
                val nextLoc = GameCatalog.locations[idx + 1]
                val nextExisting = ready.locations[nextLoc.id]
                if (nextExisting == null || !nextExisting.unlocked) {
                    repository.upsertLocationProgress(
                        currentUserId,
                        (nextExisting ?: LocationProgress(userId = currentUserId, locationId = nextLoc.id))
                            .copy(unlocked = true)
                    )
                }
            }

            // Recompute total stars
            val updatedStarsMap = ready.locations.toMutableMap().apply {
                put(scene.location.id, LocationProgress(locationId = scene.location.id, stars = bestStars))
            }
            val totalStarsSum = updatedStarsMap.values.sumOf { it.stars }

            repository.updateProfileFields(
                currentUserId,
                mapOf(
                    "totalScore" to (profile.totalScore + earnedScore),
                    "totalStars" to totalStarsSum
                )
            )

            // Achievements
            when (scene.location.id) {
                "cottage" -> {
                    repository.unlockAchievement(currentUserId, "ach_cottage", "Лесной следопыт")
                    repository.updateQuestProgress(currentUserId, "quest_cottage_case", 1L, 1L)
                }
                "palace" -> {
                    repository.unlockAchievement(currentUserId, "ach_palace", "Королевский инспектор")
                    if (scene.difficulty != DifficultyLevel.NOVICE) {
                        repository.updateQuestProgress(currentUserId, "quest_palace_detective", 1L, 1L)
                    }
                }
                "forest" -> {
                    repository.unlockAchievement(currentUserId, "ach_forest", "Хранитель сказок")
                    repository.updateQuestProgress(currentUserId, "quest_forest_case", 1L, 1L)
                }
            }

            if (scene.mistakesCount == 0) {
                repository.unlockAchievement(currentUserId, "ach_eagle_eye", "Орлиный глаз")
            }
            if (scene.difficulty == DifficultyLevel.MASTER) {
                repository.unlockAchievement(currentUserId, "ach_master", "Магистр сыска")
            }

            repository.updateQuestProgress(currentUserId, "quest_7_stars", totalStarsSum, 7L)
        }
    }

    private suspend fun syncClueQuestsAndAchievements() {
        val ready = uiState.value as? GameUiState.Ready ?: return
        val clueCount = (ready.clues.size + 1).toLong()
        repository.updateQuestProgress(currentUserId, "quest_collect_5_clues", clueCount, 5L)
        if (clueCount >= 6L) {
            repository.unlockAchievement(currentUserId, "ach_clue_hunter", "Эксперт по уликам")
        }
    }

    private companion object {
        const val TAG = "DetectiveVM"
    }
}
