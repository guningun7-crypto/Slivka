package com.example.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CollectedClue
import com.example.data.model.DetectiveProfile
import com.example.data.model.DifficultyLevel
import com.example.data.model.GameCatalog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LocalGuestRepositoryTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.getSharedPreferences("fairy_detective_guest_prefs", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun `catalog contains 8 locations with 12 items each and harder difficulty scaling`() {
        assertEquals(8, GameCatalog.locations.size)
        GameCatalog.locations.forEach { loc ->
            assertEquals("Location ${loc.id} must have 12 items", 12, loc.items.size)
            loc.items.forEach { item ->
                assertTrue("${item.id} xFraction out of safe portrait bounds", item.xFraction in 0.10f..0.90f)
                assertTrue("${item.id} yFraction out of safe portrait bounds", item.yFraction in 0.10f..0.90f)
                assertTrue("${item.id} hintText should not be empty", item.hintText.isNotBlank())
            }
        }
        assertEquals(8, DifficultyLevel.NOVICE.objectsToFind)
        assertEquals(10, DifficultyLevel.DETECTIVE.objectsToFind)
        assertEquals(12, DifficultyLevel.MASTER.objectsToFind)
        assertTrue(DifficultyLevel.MASTER.missPenaltySeconds > DifficultyLevel.DETECTIVE.missPenaltySeconds)
        assertTrue(DifficultyLevel.MASTER.timeLimitSeconds < DifficultyLevel.DETECTIVE.timeLimitSeconds)
    }

    @Test
    fun `initial game state creates all 8 locations and 9 quests`() = runTest {
        val repo = LocalGuestRepository(context)
        val profile = DetectiveProfile(
            userId = "guest_local",
            name = "Варя",
            gender = "girl",
            onboardingCompleted = true
        )
        repo.createInitialGameState(profile)

        val savedProfile = repo.observeProfile("guest_local").first()
        val locations = repo.observeLocations("guest_local").first()
        val quests = repo.observeQuests("guest_local").first()

        assertNotNull(savedProfile)
        assertEquals("Варя", savedProfile?.name)
        assertEquals(8, locations.size)
        assertTrue(locations.first().unlocked)
        assertFalse(locations[1].unlocked)
        assertEquals(9, quests.size)
    }

    @Test
    fun `existing 3-level save automatically migrates 5 new levels and unlocks snow_queen after forest`() = runTest {
        val legacyLocationsJson = """
            [
              {"userId":"guest_local","locationId":"cottage","unlocked":true,"completed":true,"stars":3},
              {"userId":"guest_local","locationId":"palace","unlocked":true,"completed":true,"stars":2},
              {"userId":"guest_local","locationId":"forest","unlocked":true,"completed":true,"stars":3}
            ]
        """.trimIndent()
        val prefs = context.getSharedPreferences("fairy_detective_guest_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("guest_locations", legacyLocationsJson).commit()

        val repo = LocalGuestRepository(context)
        val locations = repo.observeLocations("guest_local").first().associateBy { it.locationId }

        assertEquals(8, locations.size)
        assertTrue(locations["snow_queen"]?.unlocked == true)
        assertFalse(locations["lukomorye"]?.unlocked == true)
    }

    @Test
    fun `clue collection and quest reward claiming update profile score and hints`() = runTest {
        val repo = LocalGuestRepository(context)
        repo.createInitialGameState(
            DetectiveProfile(userId = "guest_local", name = "Лёва", hintsBalance = 2L, onboardingCompleted = true)
        )

        repo.saveCollectedClue(
            "guest_local",
            CollectedClue(
                clueId = "clue_swan_crown",
                locationId = "buyan",
                title = "Корона Царевны-Лебеди",
                description = "Жемчужный венец",
                slivaNote = "Мур-р!",
                rarity = "legendary"
            )
        )
        repo.updateQuestProgress("guest_local", "quest_cottage_case", 1L, 1L)
        repo.claimQuestReward("guest_local", "quest_cottage_case", 1L, 150L, 2L, 0L, 2L)

        val clues = repo.observeClues("guest_local").first()
        val profile = repo.observeProfile("guest_local").first()
        val quest = repo.observeQuests("guest_local").first().first { it.questId == "quest_cottage_case" }

        assertEquals(1, clues.size)
        assertEquals(150L, profile?.totalScore)
        assertEquals(4L, profile?.hintsBalance)
        assertTrue(quest.rewardClaimed)
    }
}
