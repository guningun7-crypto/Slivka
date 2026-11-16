package com.example.data.model

object GameCatalog {
    val locations: List<FairyLocationDef> = locationsPart1 + locationsPart2

    val quests: List<QuestDef> = listOf(
        QuestDef(
            id = "quest_cottage_case",
            title = "Дело на лесной опушке",
            description = "Помогите Красной Шапочке найти все пропавшие вещи в домике бабушки.",
            slivaPrompt = "«Начни путь сыщика — осмотри домик у опушки!»",
            targetProgress = 1L,
            rewardScore = 150L,
            rewardHints = 2L,
            rewardLabel = "+150 очков и 2 подсказки"
        ),
        QuestDef(
            id = "quest_collect_5_clues",
            title = "Коллекционер улик",
            description = "Соберите 5 особых улик в свой детективный чемоданчик.",
            slivaPrompt = "«Особые улики хранят ответы на главные загадки сказок.»",
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
            description = "Раскройте дело в Зачарованном лесу.",
            slivaPrompt = "«Лесные тени скрывают совиное перо — разберись, в чём дело!»",
            targetProgress = 1L,
            rewardScore = 400L,
            rewardHints = 2L,
            rewardLabel = "+400 очков и 2 подсказки"
        ),
        QuestDef(
            id = "quest_snow_lukomorye",
            title = "От льдов к Лукоморью",
            description = "Раскройте дела в Ледяном чертоге и у Дуба Лукоморья (2 сказки).",
            slivaPrompt = "«Мур-р! Передай привет моему коллеге Коту Учёному!»",
            targetProgress = 2L,
            rewardScore = 500L,
            rewardHints = 3L,
            rewardLabel = "+500 очков и 3 подсказки"
        ),
        QuestDef(
            id = "quest_bazaar_morozko",
            title = "Жара и стужа",
            description = "Помогите героям Восточного базара и Терема Морозко (2 сказки).",
            slivaPrompt = "«От пустынных ковров до резной печи — настоящий сыщик везде дома!»",
            targetProgress = 2L,
            rewardScore = 600L,
            rewardHints = 3L,
            rewardLabel = "+600 очков и 3 подсказки"
        ),
        QuestDef(
            id = "quest_collect_15_clues",
            title = "Хранитель архива",
            description = "Соберите 15 особых улик из разных уголков Сказочной страны.",
            slivaPrompt = "«Твой чемоданчик скоро станет настоящим музеем сказок!»",
            targetProgress = 15L,
            rewardScore = 750L,
            rewardHints = 4L,
            rewardLabel = "+750 очков и 4 подсказки"
        ),
        QuestDef(
            id = "quest_buyan_finale",
            title = "Тайна острова Буян",
            description = "Пройдите финальное испытание у Царевича Гвидона.",
            slivaPrompt = "«Это твой выпускной экзамен, юный детектив!»",
            targetProgress = 1L,
            rewardScore = 900L,
            rewardHints = 4L,
            rewardLabel = "+900 очков и 4 подсказки"
        ),
        QuestDef(
            id = "quest_18_stars",
            title = "Звёздный магистр",
            description = "Наберите суммарно 18 звёзд на карте Сказочной страны.",
            slivaPrompt = "«Перепроходи локации на высокой сложности без промахов!»",
            targetProgress = 18L,
            rewardScore = 1000L,
            rewardHints = 5L,
            rewardLabel = "+1000 очков и 5 подсказок"
        )
    )

    val achievements: List<AchievementDef> = listOf(
        AchievementDef("ach_rookie", "Юный стажёр", "Поступили в школу детективов Сливы Владиславовны.", "Орден новичка"),
        AchievementDef("ach_cottage", "Лесной следопыт", "Помогли Красной Шапочке вернуть все вещи.", "Знак опушки"),
        AchievementDef("ach_palace", "Королевский инспектор", "Помогли Золушке успеть до полуночи.", "Хрустальный значок"),
        AchievementDef("ach_forest", "Хранитель рощи", "Раскрыли тайну Василисы Премудрой.", "Изумрудный орден"),
        AchievementDef("ach_snow_queen", "Покоритель льдов", "Спасли Кая и Герду в Ледяном чертоге.", "Ледяная звезда"),
        AchievementDef("ach_lukomorye", "Друг Кота Учёного", "Вернули учёные реликвии к зелёному дубу.", "Золотое звено"),
        AchievementDef("ach_bazaar", "Мастер чудес", "Отыскали волшебную лампу на Восточном базаре.", "Знак джинна"),
        AchievementDef("ach_morozko", "Гость Морозко", "Помогли Настеньке в снежном тереме.", "Морозный узор"),
        AchievementDef("ach_buyan", "Княжеский советник", "Раскрыли финальное дело острова Буян.", "Венец Буяна"),
        AchievementDef("ach_eagle_eye", "Орлиный глаз", "Прошли локацию без единого промаха.", "Золотая лупа"),
        AchievementDef("ach_clue_hunter", "Эксперт по уликам", "Собрали 12 или более особых улик в инвентарь.", "Архивариус"),
        AchievementDef("ach_master", "Магистр сыска", "Победили в суровом режиме «Магистр сыска».", "Платиновый значок"),
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
