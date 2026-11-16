package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

enum class DifficultyLevel(
    val id: String,
    val title: String,
    val subtitle: String,
    val objectsToFind: Int,
    val timeLimitSeconds: Int,
    val freeHints: Int,
    val missPenaltySeconds: Int,
    val scoreMultiplier: Double,
    val itemScale: Float
) {
    NOVICE(
        id = "novice",
        title = "Новичок",
        subtitle = "Спокойный темп, 6 предметов, 3 подсказки",
        objectsToFind = 6,
        timeLimitSeconds = 240,
        freeHints = 3,
        missPenaltySeconds = 0,
        scoreMultiplier = 1.0,
        itemScale = 1.15f
    ),
    DETECTIVE(
        id = "detective",
        title = "Сыщик",
        subtitle = "Баланс и интрига, 8 предметов, штраф -3 сек",
        objectsToFind = 8,
        timeLimitSeconds = 180,
        freeHints = 2,
        missPenaltySeconds = 3,
        scoreMultiplier = 1.5,
        itemScale = 1.0f
    ),
    MASTER(
        id = "master",
        title = "Магистр сыска",
        subtitle = "Все 10 предметов, мелкие детали, штраф -7 сек",
        objectsToFind = 10,
        timeLimitSeconds = 140,
        freeHints = 1,
        missPenaltySeconds = 7,
        scoreMultiplier = 2.2,
        itemScale = 0.85f
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
    LANTERN
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

object GameCatalog {
    val locations: List<FairyLocationDef> = listOf(
        FairyLocationDef(
            id = "cottage",
            title = "Домик у опушки",
            fairyTaleName = "Красная Шапочка",
            heroName = "Красная Шапочка",
            heroTitle = "Юная гостья леса",
            shortSummary = "В уютном домике бабушки всё перевёрнуто: пропали гостинцы, компас и семейная реликвия.",
            sceneDrawableRes = R.drawable.img_scene_cottage,
            mapXFraction = 0.23f,
            mapYFraction = 0.72f,
            introDialogue = listOf(
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Кошка-директор школы юных детективов",
                    isSliva = true,
                    text = "Мур-р, добро пожаловать в Сказочную страну! Первое задание — на лесной опушке. Включи кошачью зоркость, осмотри каждую полку и не спеши."
                ),
                DialogueLine(
                    speaker = "Красная Шапочка",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Ох, юный сыщик! Кто-то устроил переполох в домике бабушки! Пропали пирожки, компас и даже важная семейная брошь..."
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Кошка-директор школы",
                    isSliva = true,
                    text = "Ищи не только пропавшие вещи, но и особые улики — они попадут в твой чемоданчик. Если затруднишься — нажми на мою лупу подсказки!"
                )
            ),
            outroDialogue = listOf(
                DialogueLine(
                    speaker = "Красная Шапочка",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Ура, всё нашлось! А по найденной алой ленточке и медальону стало ясно, что серый шутник убежал в сторону королевского дворца!"
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Кошка-директор школы",
                    isSliva = true,
                    text = "Блестящая работа, мур-р! Улики занесены в досье, а путь в Королевский зал на карте теперь открыт."
                )
            ),
            items = listOf(
                HiddenObjectDef(
                    id = "cottage_basket",
                    name = "Корзинка пирожков",
                    kind = HiddenItemKind.BASKET,
                    xFraction = 0.48f,
                    yFraction = 0.67f,
                    rotationDeg = -6f,
                    hintText = "Присмотритесь рядом с резным дубовым столом."
                ),
                HiddenObjectDef(
                    id = "cottage_brooch",
                    name = "Бабушкина брошь",
                    kind = HiddenItemKind.BROOCH,
                    xFraction = 0.22f,
                    yFraction = 0.43f,
                    rotationDeg = 12f,
                    hintText = "Ищите блеск золота на деревянной полке слева."
                ),
                HiddenObjectDef(
                    id = "cottage_hood",
                    name = "Красный колпачок",
                    kind = HiddenItemKind.RED_HOOD,
                    xFraction = 0.74f,
                    yFraction = 0.56f,
                    rotationDeg = -10f,
                    hintText = "Яркая ткань притаилась возле кресла справа."
                ),
                HiddenObjectDef(
                    id = "cottage_compass",
                    name = "Латунный компас",
                    kind = HiddenItemKind.COMPASS,
                    xFraction = 0.61f,
                    yFraction = 0.78f,
                    rotationDeg = 18f,
                    hintText = "Проверьте узорчатый ковёр в нижней части комнаты."
                ),
                HiddenObjectDef(
                    id = "cottage_keys",
                    name = "Связка ключей",
                    kind = HiddenItemKind.KEYS,
                    xFraction = 0.35f,
                    yFraction = 0.27f,
                    rotationDeg = -15f,
                    hintText = "Посмотрите повыше, ближе к оконной раме."
                ),
                HiddenObjectDef(
                    id = "cottage_ribbon",
                    name = "Алая ленточка",
                    kind = HiddenItemKind.RIBBON,
                    xFraction = 0.14f,
                    yFraction = 0.77f,
                    rotationDeg = 22f,
                    hintText = "Улика! Загляните в левый нижний угол у половицы.",
                    clueInfo = ClueInfo(
                        clueId = "clue_red_ribbon",
                        title = "Алая шёлковая лента",
                        description = "Обрывок тонкой ленты с блёстками, явно не из лесной деревни.",
                        slivaNote = "Такие ленты носят только при королевском дворе. След ведёт во дворец!",
                        rarity = "rare"
                    )
                ),
                HiddenObjectDef(
                    id = "cottage_medallion",
                    name = "Медальон следопыта",
                    kind = HiddenItemKind.MEDALLION,
                    xFraction = 0.85f,
                    yFraction = 0.35f,
                    rotationDeg = -8f,
                    hintText = "Улика прячется на верхних стропилах справа.",
                    clueInfo = ClueInfo(
                        clueId = "clue_wolf_medallion",
                        title = "Серебряный медальон с волком",
                        description = "На обратной стороне выгравирована королевская дата полуночного бала.",
                        slivaNote = "Загадочный знак! Похоже, похититель торопился именно на бал.",
                        rarity = "legendary"
                    )
                ),
                HiddenObjectDef(
                    id = "cottage_thimble",
                    name = "Золотой напёрсток",
                    kind = HiddenItemKind.THIMBLE,
                    xFraction = 0.56f,
                    yFraction = 0.38f,
                    rotationDeg = 9f,
                    hintText = "Маленькая золотая деталь блестит в центре комнаты.",
                    clueInfo = ClueInfo(
                        clueId = "clue_gold_thimble",
                        title = "Волшебный напёрсток",
                        description = "Тёплый на ощупь, от него пахнет корицей и звёздной пыльцой.",
                        slivaNote = "С таким напёрстком шьют бальные платья для фей!",
                        rarity = "common"
                    )
                ),
                HiddenObjectDef(
                    id = "cottage_scroll",
                    name = "Старинный свиток",
                    kind = HiddenItemKind.SCROLL,
                    xFraction = 0.79f,
                    yFraction = 0.81f,
                    rotationDeg = -20f,
                    hintText = "Свёрнутый пергамент лежит в правом нижнем углу."
                ),
                HiddenObjectDef(
                    id = "cottage_teapot",
                    name = "Глиняный чайник",
                    kind = HiddenItemKind.TEAPOT,
                    xFraction = 0.29f,
                    yFraction = 0.61f,
                    rotationDeg = 5f,
                    hintText = "Ищите уютную посуду слева от центра стола."
                )
            )
        ),
        FairyLocationDef(
            id = "palace",
            title = "Полночный зал",
            fairyTaleName = "Золушка",
            heroName = "Золушка",
            heroTitle = "Принцесса бала",
            shortSummary = "До полуночи остались считаные минуты: во дворце таинственно исчезли хрустальная туфелька и королевские печати.",
            sceneDrawableRes = R.drawable.img_scene_palace,
            mapXFraction = 0.53f,
            mapYFraction = 0.45f,
            introDialogue = listOf(
                DialogueLine(
                    speaker = "Золушка",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Благодарю, что примчались, детектив! Перед самым боем часов пропала моя хрустальная туфелька, маска и важные королевские бумаги!"
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Кошка-директор школы",
                    isSliva = true,
                    text = "Посмотрите на мраморную лестницу и подсвечники — в свете луны прозрачные предметы почти сливаются со стеклом. Будьте предельно внимательны!"
                )
            ),
            outroDialogue = listOf(
                DialogueLine(
                    speaker = "Золушка",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Вы спасли мой бал! А среди бумаг оказалась записка со следами лесной росы и совиным пером..."
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Кошка-директор школы",
                    isSliva = true,
                    text = "Вот она, недостающая ниточка! Финальная разгадка ждёт нас в Зачарованном лесу у Василисы Премудрой."
                )
            ),
            items = listOf(
                HiddenObjectDef(
                    id = "palace_slipper",
                    name = "Хрустальная туфелька",
                    kind = HiddenItemKind.SLIPPER,
                    xFraction = 0.44f,
                    yFraction = 0.78f,
                    rotationDeg = -8f,
                    hintText = "Проверьте нижние ступени мраморного зала."
                ),
                HiddenObjectDef(
                    id = "palace_watch",
                    name = "Карманные часы",
                    kind = HiddenItemKind.POCKET_WATCH,
                    xFraction = 0.67f,
                    yFraction = 0.34f,
                    rotationDeg = 14f,
                    hintText = "Золотой циферблат поблёскивает у правых колонн."
                ),
                HiddenObjectDef(
                    id = "palace_mask",
                    name = "Маскарадная маска",
                    kind = HiddenItemKind.MASK,
                    xFraction = 0.19f,
                    yFraction = 0.48f,
                    rotationDeg = -14f,
                    hintText = "Ищите у бархатных портьер в левой части зала."
                ),
                HiddenObjectDef(
                    id = "palace_goblet",
                    name = "Серебряный кубок",
                    kind = HiddenItemKind.GOBLET,
                    xFraction = 0.83f,
                    yFraction = 0.66f,
                    rotationDeg = 4f,
                    hintText = "Кубок стоит у подножия правой колонны."
                ),
                HiddenObjectDef(
                    id = "palace_rose",
                    name = "Алая роза",
                    kind = HiddenItemKind.ROSE,
                    xFraction = 0.28f,
                    yFraction = 0.74f,
                    rotationDeg = 20f,
                    hintText = "Алый бутон упал на блестящий пол слева."
                ),
                HiddenObjectDef(
                    id = "palace_wax_seal",
                    name = "Королевская печать",
                    kind = HiddenItemKind.WAX_SEAL,
                    xFraction = 0.56f,
                    yFraction = 0.59f,
                    rotationDeg = -5f,
                    hintText = "Важная улика в центре зала!",
                    clueInfo = ClueInfo(
                        clueId = "clue_wax_seal",
                        title = "Сургучная печать с короной",
                        description = "Оттиск с тремя звёздами — личный знак главного хранителя тайн.",
                        slivaNote = "Печать надломлена аккуратным клювом. Неужели замешана волшебная птица?",
                        rarity = "rare"
                    )
                ),
                HiddenObjectDef(
                    id = "palace_crystal",
                    name = "Осколок хрусталя",
                    kind = HiddenItemKind.CRYSTAL_SHARD,
                    xFraction = 0.34f,
                    yFraction = 0.25f,
                    rotationDeg = 26f,
                    hintText = "Сияющая улика отражает свет люстры наверху слева.",
                    clueInfo = ClueInfo(
                        clueId = "clue_crystal_shard",
                        title = "Лунный осколок",
                        description = "Переливается всеми цветами радуги даже в полной темноте.",
                        slivaNote = "Этот осколок не от туфельки, а от магического зеркала из дремучего леса!",
                        rarity = "legendary"
                    )
                ),
                HiddenObjectDef(
                    id = "palace_fairy_brooch",
                    name = "Брошь феи-крёстной",
                    kind = HiddenItemKind.FAIRY_BROOCH,
                    xFraction = 0.77f,
                    yFraction = 0.24f,
                    rotationDeg = -12f,
                    hintText = "Изящная улика светится справа наверху.",
                    clueInfo = ClueInfo(
                        clueId = "clue_fairy_brooch",
                        title = "Звёздная брошь феи",
                        description = "Оставлена как тайный знак для самых наблюдательных сыщиков.",
                        slivaNote = "Фея-крёстная сама подсказала нам путь к лесной поляне!",
                        rarity = "common"
                    )
                ),
                HiddenObjectDef(
                    id = "palace_carriage_key",
                    name = "Ключик от кареты",
                    kind = HiddenItemKind.CARRIAGE_KEY,
                    xFraction = 0.14f,
                    yFraction = 0.82f,
                    rotationDeg = 30f,
                    hintText = "Бронзовый ключик лежит в левом нижнем углу."
                ),
                HiddenObjectDef(
                    id = "palace_mirror",
                    name = "Зеркальце",
                    kind = HiddenItemKind.MIRROR_HAND,
                    xFraction = 0.87f,
                    yFraction = 0.45f,
                    rotationDeg = -18f,
                    hintText = "Серебряная оправа видна у правого края сцены."
                )
            )
        ),
        FairyLocationDef(
            id = "forest",
            title = "Зачарованный лес",
            fairyTaleName = "Василиса Премудрая",
            heroName = "Василиса Премудрая",
            heroTitle = "Хранительница тайн",
            shortSummary = "На волшебной поляне пропали путеводный клубок, огненное перо и древние обереги из резной избушки.",
            sceneDrawableRes = R.drawable.img_scene_forest,
            mapXFraction = 0.78f,
            mapYFraction = 0.24f,
            introDialogue = listOf(
                DialogueLine(
                    speaker = "Василиса Премудрая",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Здравствуй, юный детектив! Все сказочные нити сошлись здесь, в зачарованном лесу. Кто-то похитил мой путеводный клубок и обереги!"
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Директор школы",
                    isSliva = true,
                    text = "Это решающее дело! Между светлячками, мхом и корнями векового дуба легко упустить мелкие улики. Покажи всё, чему научился в школе сыска!"
                )
            ),
            outroDialogue = listOf(
                DialogueLine(
                    speaker = "Василиса Премудрая",
                    role = "Героиня сказки",
                    isSliva = false,
                    text = "Тайна раскрыта! Оказывается, старая сова собирала блестящие вещи со всех сказок, чтобы украсить лесной праздник к возвращению весны!"
                ),
                DialogueLine(
                    speaker = "Слива Владиславовна",
                    role = "Директор школы",
                    isSliva = true,
                    text = "Поздравляю, выпускник! Мяу-браво! Ты помог всем героям Сказочной страны. Горжусь твоей дедукцией и зоркостью!"
                )
            ),
            items = listOf(
                HiddenObjectDef(
                    id = "forest_yarn",
                    name = "Волшебный клубок",
                    kind = HiddenItemKind.YARN_BALL,
                    xFraction = 0.51f,
                    yFraction = 0.73f,
                    rotationDeg = 0f,
                    hintText = "Золотая нить свернулась на мшистой тропинке внизу."
                ),
                HiddenObjectDef(
                    id = "forest_feather",
                    name = "Перо Жар-птицы",
                    kind = HiddenItemKind.FIRE_FEATHER,
                    xFraction = 0.22f,
                    yFraction = 0.31f,
                    rotationDeg = -22f,
                    hintText = "Огненный отблеск виден среди ветвей слева наверху."
                ),
                HiddenObjectDef(
                    id = "forest_pot",
                    name = "Глиняный горшочек",
                    kind = HiddenItemKind.CLAY_POT,
                    xFraction = 0.72f,
                    yFraction = 0.65f,
                    rotationDeg = 6f,
                    hintText = "Посмотрите у корней дерева справа."
                ),
                HiddenObjectDef(
                    id = "forest_spoon",
                    name = "Расписная ложка",
                    kind = HiddenItemKind.WOODEN_SPOON,
                    xFraction = 0.37f,
                    yFraction = 0.58f,
                    rotationDeg = 28f,
                    hintText = "Резной узор мелькнул левее центра поляны."
                ),
                HiddenObjectDef(
                    id = "forest_mushroom",
                    name = "Светлячок-гриб",
                    kind = HiddenItemKind.GLOW_MUSHROOM,
                    xFraction = 0.14f,
                    yFraction = 0.78f,
                    rotationDeg = -8f,
                    hintText = "Светящаяся шляпка в левом нижнем углу."
                ),
                HiddenObjectDef(
                    id = "forest_owl_amulet",
                    name = "Амулет мудрой совы",
                    kind = HiddenItemKind.OWL_AMULET,
                    xFraction = 0.62f,
                    yFraction = 0.27f,
                    rotationDeg = 10f,
                    hintText = "Ключевая улика висит высоко на ветке справа от центра!",
                    clueInfo = ClueInfo(
                        clueId = "clue_owl_amulet",
                        title = "Амулет ночной совы",
                        description = "Резной янтарный медальон в форме совиных крыльев — доказательство разгадки!",
                        slivaNote = "Вот и портрет виновницы торжества! Сова просто обожала блёстки!",
                        rarity = "legendary"
                    )
                ),
                HiddenObjectDef(
                    id = "forest_dew_vial",
                    name = "Пузырёк росы",
                    kind = HiddenItemKind.DEW_VIAL,
                    xFraction = 0.85f,
                    yFraction = 0.77f,
                    rotationDeg = -14f,
                    hintText = "Хрустальная улика притаилась в траве справа внизу.",
                    clueInfo = ClueInfo(
                        clueId = "clue_dew_vial",
                        title = "Флакон утренней росы",
                        description = "Светится мягким изумрудным сиянием при каждом прикосновении.",
                        slivaNote = "Редчайший эликсир Василисы для проявления невидимых следов!",
                        rarity = "rare"
                    )
                ),
                HiddenObjectDef(
                    id = "forest_towel",
                    name = "Вышитый рушник",
                    kind = HiddenItemKind.TOWEL,
                    xFraction = 0.45f,
                    yFraction = 0.39f,
                    rotationDeg = 8f,
                    hintText = "Узорная улика у крыльца сказочной избушки.",
                    clueInfo = ClueInfo(
                        clueId = "clue_embroidered_towel",
                        title = "Узорный оберег",
                        description = "Красные нити узора образуют карту всех тропинок Сказочной страны.",
                        slivaNote = "Настоящее сокровище народной мудрости для нашего архива!",
                        rarity = "common"
                    )
                ),
                HiddenObjectDef(
                    id = "forest_rune",
                    name = "Рунический камень",
                    kind = HiddenItemKind.RUNE_STONE,
                    xFraction = 0.27f,
                    yFraction = 0.83f,
                    rotationDeg = -10f,
                    hintText = "Камень с резным знаком лежит у ручья внизу."
                ),
                HiddenObjectDef(
                    id = "forest_lantern",
                    name = "Фонарик гномов",
                    kind = HiddenItemKind.LANTERN,
                    xFraction = 0.83f,
                    yFraction = 0.41f,
                    rotationDeg = 5f,
                    hintText = "Тёплый огонёк мерцает справа у кроны."
                )
            )
        )
    )

    val quests: List<QuestDef> = listOf(
        QuestDef(
            id = "quest_cottage_case",
            title = "Дело на лесной опушке",
            description = "Помогите Красной Шапочке найти все пропавшие вещи в домике бабушки.",
            slivaPrompt = "«Начни с малого, юный сыщик — осмотри домик у опушки!»",
            targetProgress = 1L,
            rewardScore = 150L,
            rewardHints = 2L,
            rewardLabel = "+150 очков и 2 подсказки"
        ),
        QuestDef(
            id = "quest_collect_5_clues",
            title = "Коллекционер улик",
            description = "Соберите 5 особых улик в свой детективный чемоданчик.",
            slivaPrompt = "«Особые улики хранят ответы на главные загадки сказки.»",
            targetProgress = 5L,
            rewardScore = 250L,
            rewardHints = 2L,
            rewardLabel = "+250 очков и 2 подсказки"
        ),
        QuestDef(
            id = "quest_palace_detective",
            title = "Бальный инспектор",
            description = "Завершите Полночный зал Золушки на сложности «Сыщик» или «Магистр сыска».",
            slivaPrompt = "«На балу нужна настоящая выдержка! Выбери сложность не ниже Сыщика.»",
            targetProgress = 1L,
            rewardScore = 350L,
            rewardHints = 2L,
            rewardLabel = "+350 очков и 2 подсказки"
        ),
        QuestDef(
            id = "quest_forest_case",
            title = "Загадка Василисы",
            description = "Раскройте финальное дело в Зачарованном лесу.",
            slivaPrompt = "«Все следы ведут к древней избушке. Пора поставить точку в деле!»",
            targetProgress = 1L,
            rewardScore = 500L,
            rewardHints = 3L,
            rewardLabel = "+500 очков и 3 подсказки"
        ),
        QuestDef(
            id = "quest_7_stars",
            title = "Звёздный выпускник",
            description = "Наберите суммарно 7 звёзд на интерактивной карте Сказочной страны.",
            slivaPrompt = "«Перепроходи локации быстрее и точнее, чтобы заслужить все звёзды!»",
            targetProgress = 7L,
            rewardScore = 600L,
            rewardHints = 3L,
            rewardLabel = "+600 очков и 3 подсказки"
        )
    )

    val achievements: List<AchievementDef> = listOf(
        AchievementDef("ach_rookie", "Юный стажёр", "Поступили в школу детективов Сливы Владиславовны.", "Орден новичка"),
        AchievementDef("ach_cottage", "Лесной следопыт", "Помогли Красной Шапочке вернуть все вещи.", "Знак опушки"),
        AchievementDef("ach_palace", "Королевский инспектор", "Помогли Золушке успеть до полуночи.", "Хрустальный значок"),
        AchievementDef("ach_forest", "Хранитель сказок", "Раскрыли главную тайну Василисы Премудрой.", "Изумрудный орден"),
        AchievementDef("ach_eagle_eye", "Орлиный глаз", "Прошли локацию без единого промаха.", "Золотая лупа"),
        AchievementDef("ach_clue_hunter", "Эксперт по уликам", "Собрали 6 или более редких улик в инвентарь.", "Архивариус"),
        AchievementDef("ach_master", "Магистр сыска", "Победили в режиме «Магистр сыска».", "Платиновый значок"),
        AchievementDef("ach_stylist", "Безупречный стиль", "Подобрали новый образ в детективном гардеробе.", "Модный сыщик")
    )

    val hats: List<WardrobeOption> = listOf(
        WardrobeOption("none", "Без головного убора", "Лёгкий и открытый образ"),
        WardrobeOption("cap", "Кепка сыщика", "Классика школы детективов"),
        WardrobeOption("trilby", "Шляпа-трилби", "Строгий стиль мастера"),
        WardrobeOption("beret", "Берет с пером", "Творческий подход к уликам"),
        WardrobeOption("hood", "Сказочный капюшон", "Для прогулок по лесу")
    )

    val coats: List<WardrobeOption> = listOf(
        WardrobeOption("plum", "Сливовый тренч", "Фирменный цвет директора"),
        WardrobeOption("emerald", "Изумрудный плащ", "Оттенок зачарованного леса"),
        WardrobeOption("royal", "Королевский мундир", "Для приёмов во дворце"),
        WardrobeOption("gold", "Золотой камзол", "Торжественный парадный вид")
    )

    val accessories: List<WardrobeOption> = listOf(
        WardrobeOption("loupe", "Латунная лупа", "Видит каждую мелочь"),
        WardrobeOption("watch", "Карманные часы", "Точность до секунды"),
        WardrobeOption("notebook", "Блокнот улик", "Для важных заметок"),
        WardrobeOption("goggles", "Очки следопыта", "Для ночных расследований")
    )

    fun locationById(id: String): FairyLocationDef = locations.find { it.id == id } ?: locations.first()
}
