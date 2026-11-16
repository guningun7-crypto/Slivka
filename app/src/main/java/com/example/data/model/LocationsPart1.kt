package com.example.data.model

import com.example.R

internal val locationsPart1: List<FairyLocationDef> = listOf(
    FairyLocationDef(
        id = "cottage",
        title = "Домик у опушки",
        fairyTaleName = "Красная Шапочка",
        heroName = "Красная Шапочка",
        heroTitle = "Юная гостья леса",
        shortSummary = "В уютном домике бабушки всё перевёрнуто: пропали гостинцы, компас и семейная реликвия.",
        sceneDrawableRes = R.drawable.img_scene_cottage,
        mapXFraction = 0.22f,
        mapYFraction = 0.87f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы юных детективов",
                isSliva = true,
                text = "Мур-р, добро пожаловать в Сказочную страну! Твоё первое задание — на лесной опушке. Будь внимателен: мелкие предметы отлично прячутся в тенях."
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
                text = "Ищи не только пропавшие вещи, но и особые улики для чемоданчика. Мои подсказки теперь указывают лишь примерную область поиска — остальное за твоей зоркостью!"
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
                text = "Блестящая работа, мур-р! Улики занесены в досье, а путь в Полночный зал на карте теперь открыт."
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
                hintText = "Плетёная спутница путешествий ещё помнит тепло печи и дубовой столешницы."
            ),
            HiddenObjectDef(
                id = "cottage_brooch",
                name = "Бабушкина брошь",
                kind = HiddenItemKind.BROOCH,
                xFraction = 0.20f,
                yFraction = 0.41f,
                rotationDeg = 12f,
                hintText = "Алый камень в золоте прячется среди деревянных безделушек на высоте руки."
            ),
            HiddenObjectDef(
                id = "cottage_hood",
                name = "Красный колпачок",
                kind = HiddenItemKind.RED_HOOD,
                xFraction = 0.76f,
                yFraction = 0.56f,
                rotationDeg = -10f,
                hintText = "Бархатная ткань цвета спелой вишни отдыхает там, где обычно сидят гости."
            ),
            HiddenObjectDef(
                id = "cottage_compass",
                name = "Латунный компас",
                kind = HiddenItemKind.COMPASS,
                xFraction = 0.61f,
                yFraction = 0.78f,
                rotationDeg = 18f,
                hintText = "Стрелка всё ещё дрожит на мягком узоре, по которому ходят шаги."
            ),
            HiddenObjectDef(
                id = "cottage_keys",
                name = "Связка ключей",
                kind = HiddenItemKind.KEYS,
                xFraction = 0.35f,
                yFraction = 0.25f,
                rotationDeg = -15f,
                hintText = "Металлический звон замер ближе к дневному свету и деревянным переплётам."
            ),
            HiddenObjectDef(
                id = "cottage_ribbon",
                name = "Алая ленточка",
                kind = HiddenItemKind.RIBBON,
                xFraction = 0.14f,
                yFraction = 0.79f,
                rotationDeg = 22f,
                hintText = "Улика! Шёлковый бант уронили у самого скрипучего края пола.",
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
                yFraction = 0.32f,
                rotationDeg = -8f,
                hintText = "Серебряный отблеск улики зацепился высоко, где вьётся печной дым.",
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
                hintText = "Крошечный швейный помощник блестит в самом сердце уютной избы.",
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
                xFraction = 0.80f,
                yFraction = 0.83f,
                rotationDeg = -20f,
                hintText = "Свёрнутый рецепт пирогов завалился в тень подальше от центра."
            ),
            HiddenObjectDef(
                id = "cottage_teapot",
                name = "Глиняный чайник",
                kind = HiddenItemKind.TEAPOT,
                xFraction = 0.28f,
                yFraction = 0.61f,
                rotationDeg = 5f,
                hintText = "Посуда с пузатым боком ждёт заварки неподалёку от трапезы."
            ),
            HiddenObjectDef(
                id = "cottage_yarn",
                name = "Клубок шерсти",
                kind = HiddenItemKind.YARN_BALL,
                xFraction = 0.67f,
                yFraction = 0.22f,
                rotationDeg = 15f,
                hintText = "Мои кошачьи усы почуяли мягкий клубок — он забрался на самую верхотуру."
            ),
            HiddenObjectDef(
                id = "cottage_mittens",
                name = "Вязаные варежки",
                kind = HiddenItemKind.MITTENS,
                xFraction = 0.13f,
                yFraction = 0.20f,
                rotationDeg = -18f,
                hintText = "Тёплая пара притаилась вдали от огня, под деревянным козырьком."
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
        mapXFraction = 0.74f,
        mapYFraction = 0.78f,
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
                text = "В лунном сиянии мраморной залы прозрачные и золотые вещи сливаются со стеклом. Приближай картинку щипком — спешка сейчас плохой советчик!"
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
                text = "Вот она, недостающая ниточка! Следующий след уводит нас в Зачарованный лес к Василисе Премудрой."
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "palace_slipper",
                name = "Хрустальная туфелька",
                kind = HiddenItemKind.SLIPPER,
                xFraction = 0.44f,
                yFraction = 0.79f,
                rotationDeg = -8f,
                hintText = "Прозрачный звон каблука замер на холодных плитах, где торопились шаги."
            ),
            HiddenObjectDef(
                id = "palace_watch",
                name = "Карманные часы",
                kind = HiddenItemKind.POCKET_WATCH,
                xFraction = 0.67f,
                yFraction = 0.34f,
                rotationDeg = 14f,
                hintText = "Тихий бег секунд спрятался среди каменной резьбы и лепнины."
            ),
            HiddenObjectDef(
                id = "palace_mask",
                name = "Маскарадная маска",
                kind = HiddenItemKind.MASK,
                xFraction = 0.19f,
                yFraction = 0.48f,
                rotationDeg = -14f,
                hintText = "Тайну карнавала скрывают тяжёлые складки дворцового бархата."
            ),
            HiddenObjectDef(
                id = "palace_goblet",
                name = "Серебряный кубок",
                kind = HiddenItemKind.GOBLET,
                xFraction = 0.83f,
                yFraction = 0.66f,
                rotationDeg = 4f,
                hintText = "Праздничная чаша остывает в тени белой колоннады."
            ),
            HiddenObjectDef(
                id = "palace_rose",
                name = "Алая роза",
                kind = HiddenItemKind.ROSE,
                xFraction = 0.28f,
                yFraction = 0.74f,
                rotationDeg = 20f,
                hintText = "Нежный цветок выронили на зеркальный паркет в танце."
            ),
            HiddenObjectDef(
                id = "palace_wax_seal",
                name = "Королевская печать",
                kind = HiddenItemKind.WAX_SEAL,
                xFraction = 0.56f,
                yFraction = 0.59f,
                rotationDeg = -5f,
                hintText = "Капля алого сургуча хранит королевскую волю на самом видном перепутье.",
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
                yFraction = 0.23f,
                rotationDeg = 26f,
                hintText = "Магическая грань поймала свет хрустальной люстры высоко над полом.",
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
                yFraction = 0.22f,
                rotationDeg = -12f,
                hintText = "Искрящаяся булавка парила у лепного карниза, пока не зацепилась за тень.",
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
                xFraction = 0.13f,
                yFraction = 0.84f,
                rotationDeg = 30f,
                hintText = "Тот, кто открывает тыквенную дверцу, закатился далеко от блеска свечей."
            ),
            HiddenObjectDef(
                id = "palace_mirror",
                name = "Зеркальце",
                kind = HiddenItemKind.MIRROR_HAND,
                xFraction = 0.87f,
                yFraction = 0.43f,
                rotationDeg = -18f,
                hintText = "Тонкое стекло в серебре ловит отражение далёкой галереи."
            ),
            HiddenObjectDef(
                id = "palace_hourglass",
                name = "Песочные часы",
                kind = HiddenItemKind.HOURGLASS,
                xFraction = 0.50f,
                yFraction = 0.19f,
                rotationDeg = 8f,
                hintText = "Золотой песок утекает там, где ветер колышет верхние своды."
            ),
            HiddenObjectDef(
                id = "palace_inkwell",
                name = "Хрустальная чернильница",
                kind = HiddenItemKind.INKWELL,
                xFraction = 0.16f,
                yFraction = 0.27f,
                rotationDeg = -7f,
                hintText = "Перо и тёмные чернила замерли в тихой нише в стороне от гостей."
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
        mapXFraction = 0.28f,
        mapYFraction = 0.66f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Василиса Премудрая",
                role = "Героиня сказки",
                isSliva = false,
                text = "Здравствуй, юный детектив! В нашем лесу закружились сказочные вихри — кто-то унёс путеводный клубок и заветные обереги!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Между светлячками, мхом и корнями векового дуба вещи словно растворяются в сумраке. Не торопись — цена ошибки здесь высока!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Василиса Премудрая",
                role = "Героиня сказки",
                isSliva = false,
                text = "Вот так чудо! Половину вещей стащила лесная сова, а ледяной след на росе уходит далеко на север..."
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Мур-р! Значит, дело куда масштабнее! За пределами леса уже блестит Ледяной чертог Снежной королевы — вперёд!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "forest_yarn",
                name = "Волшебный клубок",
                kind = HiddenItemKind.YARN_BALL,
                xFraction = 0.51f,
                yFraction = 0.74f,
                rotationDeg = 0f,
                hintText = "Золотая нить запуталась там, где лесная тропа стелется мягким мхом."
            ),
            HiddenObjectDef(
                id = "forest_feather",
                name = "Перо Жар-птицы",
                kind = HiddenItemKind.FIRE_FEATHER,
                xFraction = 0.21f,
                yFraction = 0.30f,
                rotationDeg = -22f,
                hintText = "Огненный отблеск дрожит высоко среди тёмных хвойных ветвей."
            ),
            HiddenObjectDef(
                id = "forest_pot",
                name = "Глиняный горшочек",
                kind = HiddenItemKind.CLAY_POT,
                xFraction = 0.73f,
                yFraction = 0.65f,
                rotationDeg = 6f,
                hintText = "Тяжёлая глина схоронилась там, где переплелись могучие корни."
            ),
            HiddenObjectDef(
                id = "forest_spoon",
                name = "Расписная ложка",
                kind = HiddenItemKind.WOODEN_SPOON,
                xFraction = 0.37f,
                yFraction = 0.58f,
                rotationDeg = 28f,
                hintText = "Хохломской узор мелькает между папоротником и травами поляны."
            ),
            HiddenObjectDef(
                id = "forest_mushroom",
                name = "Светлячок-гриб",
                kind = HiddenItemKind.GLOW_MUSHROOM,
                xFraction = 0.14f,
                yFraction = 0.81f,
                rotationDeg = -8f,
                hintText = "Маленький лесной фонарик пророс у самой сырой земли."
            ),
            HiddenObjectDef(
                id = "forest_owl_amulet",
                name = "Амулет мудрой совы",
                kind = HiddenItemKind.OWL_AMULET,
                xFraction = 0.62f,
                yFraction = 0.25f,
                rotationDeg = 10f,
                hintText = "Янтарные крылья улики наблюдают за поляной с большой высоты.",
                clueInfo = ClueInfo(
                    clueId = "clue_owl_amulet",
                    title = "Амулет ночной совы",
                    description = "Резной янтарный медальон в форме совиных крыльев.",
                    slivaNote = "Ночная гостья обронила его, когда пролетала над избушкой!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "forest_dew_vial",
                name = "Пузырёк росы",
                kind = HiddenItemKind.DEW_VIAL,
                xFraction = 0.85f,
                yFraction = 0.79f,
                rotationDeg = -14f,
                hintText = "Хрустальная капля светится среди высокой ночной травы.",
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
                hintText = "Тканый узор с красной нитью прячется рядом с резным крыльцом.",
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
                xFraction = 0.28f,
                yFraction = 0.85f,
                rotationDeg = -10f,
                hintText = "Древний знак притаился у холодной воды лесного ручья."
            ),
            HiddenObjectDef(
                id = "forest_lantern",
                name = "Фонарик гномов",
                kind = HiddenItemKind.LANTERN,
                xFraction = 0.84f,
                yFraction = 0.41f,
                rotationDeg = 5f,
                hintText = "Тёплый огонёк висит там, где смыкаются лиственные кроны."
            ),
            HiddenObjectDef(
                id = "forest_book",
                name = "Травник Василисы",
                kind = HiddenItemKind.BOOK_SPELLS,
                xFraction = 0.43f,
                yFraction = 0.18f,
                rotationDeg = -12f,
                hintText = "Старинный переплёт ускользнул вверх, поближе к звёздному небу."
            ),
            HiddenObjectDef(
                id = "forest_casket",
                name = "Серебряный ларец",
                kind = HiddenItemKind.SILVER_CASKET,
                xFraction = 0.65f,
                yFraction = 0.84f,
                rotationDeg = 14f,
                hintText = "Металлическая шкатулка стынет на ночной росе."
            )
        )
    ),
    FairyLocationDef(
        id = "snow_queen",
        title = "Ледяной чертог",
        fairyTaleName = "Снежная королева",
        heroName = "Герда",
        heroTitle = "Отважная спутница",
        shortSummary = "В огромном ледяном дворце бушует вьюга: нужно отыскать тёплые вещи Кая, колокольчик оленя и осколки зеркала.",
        sceneDrawableRes = R.drawable.img_scene_snow_queen,
        mapXFraction = 0.76f,
        mapYFraction = 0.55f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Герда",
                role = "Героиня сказки",
                isSliva = false,
                text = "Сыщик, ты добрался вовремя! В этих ледяных залах затерялись тёплая муфта, колокольчик северного оленя и заветное слово из льдинок!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Бр-р, мои усы покрылись инеем! Помни: в чертоге светло-голубые предметы почти сливаются со льдом. Не промахивайся!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Герда",
                role = "Героиня сказки",
                isSliva = false,
                text = "Слово собрано, а ледяное сердце оттаяло! Посмотрите — среди вещей нашлась золотая цепь со старого дуба..."
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Ого! Эта цепь принадлежит самому Коту Учёному из Лукоморья! Мур-р, отправляемся навестить моего коллегу!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "sq_crown",
                name = "Ледяная корона",
                kind = HiddenItemKind.ICE_CROWN,
                xFraction = 0.50f,
                yFraction = 0.23f,
                rotationDeg = -4f,
                hintText = "Холодные зубцы сияют там, куда возносятся ледяные своды трона."
            ),
            HiddenObjectDef(
                id = "sq_muff",
                name = "Тёплая муфта",
                kind = HiddenItemKind.MUFF,
                xFraction = 0.24f,
                yFraction = 0.71f,
                rotationDeg = 12f,
                hintText = "Меховое тепло укрылось у подножия снежной лестницы."
            ),
            HiddenObjectDef(
                id = "sq_bell",
                name = "Колокольчик оленя",
                kind = HiddenItemKind.REINDEER_BELL,
                xFraction = 0.79f,
                yFraction = 0.48f,
                rotationDeg = -16f,
                hintText = "Звонкий бубенчик обронила упряжка среди прозрачных колонн."
            ),
            HiddenObjectDef(
                id = "sq_snowflake",
                name = "Кристалл вьюги",
                kind = HiddenItemKind.SNOWFLAKE,
                xFraction = 0.34f,
                yFraction = 0.34f,
                rotationDeg = 15f,
                hintText = "Хрупкий звёздный узор парит в потоках северного сияния.",
                clueInfo = ClueInfo(
                    clueId = "clue_eternal_snowflake",
                    title = "Нетающая снежинка",
                    description = "Вырезана из вечного льда, внутри неё отражается слово «Вечность».",
                    slivaNote = "Без этой улики заклятие Снежной королевы не разрушилось бы!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "sq_rose",
                name = "Морозная роза",
                kind = HiddenItemKind.ROSE,
                xFraction = 0.64f,
                yFraction = 0.77f,
                rotationDeg = -10f,
                hintText = "Живой алый лепесток не сдаётся холоду на ледяном зеркале пола."
            ),
            HiddenObjectDef(
                id = "sq_crystal",
                name = "Осколок тролля",
                kind = HiddenItemKind.CRYSTAL_SHARD,
                xFraction = 0.16f,
                yFraction = 0.45f,
                rotationDeg = 25f,
                hintText = "Острая стеклянная игла вмёрзла в кристальную стену.",
                clueInfo = ClueInfo(
                    clueId = "clue_troll_splinter",
                    title = "Осколок кривого зеркала",
                    description = "Зловещая льдинка, искажающая всё доброе и прекрасное.",
                    slivaNote = "Держим её в бархатном мешочке, чтобы больше никому не навредила.",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "sq_chain",
                name = "Звено золотой цепи",
                kind = HiddenItemKind.GOLDEN_CHAIN,
                xFraction = 0.75f,
                yFraction = 0.83f,
                rotationDeg = 18f,
                hintText = "Неожиданный тёплый металл тает прямо в снежной позёмке.",
                clueInfo = ClueInfo(
                    clueId = "clue_lukomorye_link",
                    title = "Золотое звено из Лукоморья",
                    description = "Массивное звено с выбитыми древними буквами «К. У.».",
                    slivaNote = "Это и есть зацепка от Кота Учёного — следуем к дубу!",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "sq_mittens",
                name = "Меховые рукавицы",
                kind = HiddenItemKind.MITTENS,
                xFraction = 0.42f,
                yFraction = 0.62f,
                rotationDeg = -14f,
                hintText = "Пара уютных спутников согревает прохладные ступени."
            ),
            HiddenObjectDef(
                id = "sq_staff",
                name = "Ледяной скипетр",
                kind = HiddenItemKind.FROST_STAFF,
                xFraction = 0.86f,
                yFraction = 0.28f,
                rotationDeg = 22f,
                hintText = "Высокий жезл стужи прислонён к заиндевевшей арке."
            ),
            HiddenObjectDef(
                id = "sq_mirror",
                name = "Зеркальце Герды",
                kind = HiddenItemKind.MIRROR_HAND,
                xFraction = 0.14f,
                yFraction = 0.83f,
                rotationDeg = -20f,
                hintText = "Круглая оправа блестит там, куда намело больше всего снега."
            ),
            HiddenObjectDef(
                id = "sq_compass",
                name = "Северный компас",
                kind = HiddenItemKind.COMPASS,
                xFraction = 0.62f,
                yFraction = 0.41f,
                rotationDeg = 10f,
                hintText = "Стрелка упорно показывает на полюс посреди хрустального зала."
            ),
            HiddenObjectDef(
                id = "sq_lantern",
                name = "Фонарь полярника",
                kind = HiddenItemKind.LANTERN,
                xFraction = 0.29f,
                yFraction = 0.17f,
                rotationDeg = 6f,
                hintText = "Крошечная искра теплится под самым ледяным куполом."
            )
        )
    )
)
