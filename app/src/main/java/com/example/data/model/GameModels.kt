package com.example.data.model

import androidx.annotation.DrawableRes

enum class DifficultyLevel(
    val id: String,
    val title: String,
    val subtitle: String,
    val objectsToFind: Int,
    val timeLimitSeconds: Int,
    val freeHints: Int,
    val missPenaltySeconds: Int,
    val scoreMultiplier: Double,
    val itemScale: Float,
    val ambientAlpha: Float
) {
    NOVICE(
        id = "novice",
        title = "Новичок",
        subtitle = "8 предметов • 190 сек • штраф за промах -2 сек",
        objectsToFind = 8,
        timeLimitSeconds = 190,
        freeHints = 2,
        missPenaltySeconds = 2,
        scoreMultiplier = 1.0,
        itemScale = 0.96f,
        ambientAlpha = 0.90f
    ),
    DETECTIVE(
        id = "detective",
        title = "Сыщик",
        subtitle = "10 предметов • 145 сек • штраф -5 сек • маскировка",
        objectsToFind = 10,
        timeLimitSeconds = 145,
        freeHints = 2,
        missPenaltySeconds = 5,
        scoreMultiplier = 1.6,
        itemScale = 0.82f,
        ambientAlpha = 0.80f
    ),
    MASTER(
        id = "master",
        title = "Магистр сыска",
        subtitle = "Все 12 предметов • 110 сек • мелкие тени, штраф -9 сек",
        objectsToFind = 12,
        timeLimitSeconds = 110,
        freeHints = 1,
        missPenaltySeconds = 9,
        scoreMultiplier = 2.5,
        itemScale = 0.68f,
        ambientAlpha = 0.70f
    );

    companion object {
        fun fromId(id: String): DifficultyLevel = entries.find { it.id == id } ?: DETECTIVE
    }
}

enum class HiddenItemKind {
    BASKET,
    BROOCH,
    RED_HOOD,
    COMPASS,
    KEYS,
    SCROLL,
    RIBBON,
    MEDALLION,
    THIMBLE,
    TEAPOT,
    SLIPPER,
    POCKET_WATCH,
    MASK,
    GOBLET,
    ROSE,
    WAX_SEAL,
    CRYSTAL_SHARD,
    FAIRY_BROOCH,
    CARRIAGE_KEY,
    MIRROR_HAND,
    YARN_BALL,
    FIRE_FEATHER,
    CLAY_POT,
    WOODEN_SPOON,
    GLOW_MUSHROOM,
    RUNE_STONE,
    OWL_AMULET,
    DEW_VIAL,
    TOWEL,
    LANTERN,
    // New items for expanded 12-item levels & 5 new fairy-tale worlds
    ICE_CROWN,
    MUFF,
    REINDEER_BELL,
    SNOWFLAKE,
    GOLDEN_CHAIN,
    SPECTACLES,
    BOOK_SPELLS,
    INKWELL,
    MAGIC_LAMP,
    CARPET_ROLL,
    SAPPHIRE_GEM,
    AMPHORA,
    HOURGLASS,
    SILVER_CASKET,
    MITTENS,
    FROST_STAFF,
    SAMOVAR,
    GOLDEN_NUT,
    SWAN_CROWN,
    ANCHOR,
    TELESCOPE,
    CORAL_BRANCH,
    COIN_POUCH
}

data class ClueInfo(
    val clueId: String,
    val title: String,
    val description: String,
    val slivaNote: String,
    val rarity: String
)

data class HiddenObjectDef(
    val id: String,
    val name: String,
    val kind: HiddenItemKind,
    val xFraction: Float,
    val yFraction: Float,
    val rotationDeg: Float = 0f,
    val hintText: String,
    val clueInfo: ClueInfo? = null
)

data class DialogueLine(
    val speaker: String,
    val role: String,
    val isSliva: Boolean,
    val text: String
)

data class FairyLocationDef(
    val id: String,
    val title: String,
    val fairyTaleName: String,
    val heroName: String,
    val heroTitle: String,
    val shortSummary: String,
    @DrawableRes val sceneDrawableRes: Int,
    val mapXFraction: Float,
    val mapYFraction: Float,
    val introDialogue: List<DialogueLine>,
    val outroDialogue: List<DialogueLine>,
    val items: List<HiddenObjectDef>
)

data class QuestDef(
    val id: String,
    val title: String,
    val description: String,
    val slivaPrompt: String,
    val targetProgress: Long,
    val rewardScore: Long,
    val rewardHints: Long,
    val rewardLabel: String
)

data class AchievementDef(
    val id: String,
    val title: String,
    val description: String,
    val badgeTitle: String
)

data class WardrobeOption(
    val id: String,
    val title: String,
    val subtitle: String
)
