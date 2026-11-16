package com.example.data.model

import com.example.R

internal val locationsPart2: List<FairyLocationDef> = listOf(
    FairyLocationDef(
        id = "lukomorye",
        title = "Дуб у Лукоморья",
        fairyTaleName = "Лукоморье",
        heroName = "Кот Учёный",
        heroTitle = "Хранитель сказок",
        shortSummary = "У зелёного дуба пропали очки Кота Учёного, книга сказок, золотая цепь и чернильница для записей.",
        sceneDrawableRes = R.drawable.img_scene_lukomorye,
        mapXFraction = 0.24f,
        mapYFraction = 0.43f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Кот Учёный",
                role = "Герой сказки",
                isSliva = false,
                text = "Мяу-приветствую, коллега! Пока я ходил по цепи кругом, неведомый ветер раскидал мои очки, подзорную трубу и свитки по всей поляне!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Рада видеть вас в здравии, профессор! Юный сыщик, помоги нашему почётному коту вернуть учёные инструменты из дубовых корней и кроны!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Кот Учёный",
                role = "Герой сказки",
                isSliva = false,
                text = "Вот спасибо! Без очков и книги я не мог дочитать рукопись про далёкий восточный город и волшебный ковёр..."
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "А на свитке осталась пыльца специй! Ковёр-самолёт унёс часть пропаж на Восточный базар к Аладдину!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "luko_chain",
                name = "Золотая цепь",
                kind = HiddenItemKind.GOLDEN_CHAIN,
                xFraction = 0.47f,
                yFraction = 0.56f,
                rotationDeg = -6f,
                hintText = "Тяжёлые звенья обвились вокруг мощного ствола, где ходит мудрый зверь."
            ),
            HiddenObjectDef(
                id = "luko_spectacles",
                name = "Очки Кота Учёного",
                kind = HiddenItemKind.SPECTACLES,
                xFraction = 0.31f,
                yFraction = 0.72f,
                rotationDeg = 14f,
                hintText = "Две стеклянные линзы поймали закатный луч в мягком клевере."
            ),
            HiddenObjectDef(
                id = "luko_book",
                name = "Книга сказок",
                kind = HiddenItemKind.BOOK_SPELLS,
                xFraction = 0.68f,
                yFraction = 0.69f,
                rotationDeg = -12f,
                hintText = "Толстый фолиант отдыхает на замшелом камне у берега."
            ),
            HiddenObjectDef(
                id = "luko_inkwell",
                name = "Чернильница с пером",
                kind = HiddenItemKind.INKWELL,
                xFraction = 0.22f,
                yFraction = 0.44f,
                rotationDeg = 8f,
                hintText = "Учёные чернила притаились среди ветвистых корней старого дерева."
            ),
            HiddenObjectDef(
                id = "luko_telescope",
                name = "Подзорная труба",
                kind = HiddenItemKind.TELESCOPE,
                xFraction = 0.81f,
                yFraction = 0.34f,
                rotationDeg = -24f,
                hintText = "Латунный глаз направлен к морскому простору с высокой ветки.",
                clueInfo = ClueInfo(
                    clueId = "clue_scholar_telescope",
                    title = "Труба звездочёта",
                    description = "На корпусе выгравированы созвездия южных стран и далёкого Востока.",
                    slivaNote = "Профессор видел в неё летящий над морем ковёр-самолёт!",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "luko_feather",
                name = "Золотое перо",
                kind = HiddenItemKind.FIRE_FEATHER,
                xFraction = 0.56f,
                yFraction = 0.23f,
                rotationDeg = 16f,
                hintText = "Лёгкое крылатое сокровище зацепилось за пышную зелёную крону."
            ),
            HiddenObjectDef(
                id = "luko_scroll",
                name = "Свиток былин",
                kind = HiddenItemKind.SCROLL,
                xFraction = 0.16f,
                yFraction = 0.82f,
                rotationDeg = 18f,
                hintText = "Пожелтевшая бумага пахнет морем у самой кромки прибоя.",
                clueInfo = ClueInfo(
                    clueId = "clue_lukomorye_scroll",
                    title = "Древний свиток сказок",
                    description = "Рукопись прерывается на рисунке золотой лампы и ковра-самолёта.",
                    slivaNote = "Вот и прямая дорога на Восточный базар!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "luko_lantern",
                name = "Судовой фонарь",
                kind = HiddenItemKind.LANTERN,
                xFraction = 0.77f,
                yFraction = 0.82f,
                rotationDeg = -8f,
                hintText = "Медный огонёк подмигивает волнам у прибрежной гальки."
            ),
            HiddenObjectDef(
                id = "luko_rune",
                name = "Камень предсказаний",
                kind = HiddenItemKind.RUNE_STONE,
                xFraction = 0.43f,
                yFraction = 0.84f,
                rotationDeg = 5f,
                hintText = "Гладкий валун с резными знаками омывается утренней водой."
            ),
            HiddenObjectDef(
                id = "luko_mushroom",
                name = "Лесной гриб",
                kind = HiddenItemKind.GLOW_MUSHROOM,
                xFraction = 0.86f,
                yFraction = 0.59f,
                rotationDeg = -10f,
                hintText = "Маленькая шляпка спряталась за дубовым пеньком у тропинки."
            ),
            HiddenObjectDef(
                id = "luko_owl",
                name = "Медальон русалки",
                kind = HiddenItemKind.OWL_AMULET,
                xFraction = 0.34f,
                yFraction = 0.21f,
                rotationDeg = 11f,
                hintText = "Таинственный талисман покачивается высоко среди дубовых листьев.",
                clueInfo = ClueInfo(
                    clueId = "clue_oak_amulet",
                    title = "Оберег зелёного дуба",
                    description = "Плетёный амулет, который русалки оставляют в знак дружбы.",
                    slivaNote = "Он пригодится нам в будущем морском путешествии!",
                    rarity = "common"
                )
            ),
            HiddenObjectDef(
                id = "luko_keys",
                name = "Ключ от дупла",
                kind = HiddenItemKind.KEYS,
                xFraction = 0.14f,
                yFraction = 0.28f,
                rotationDeg = -20f,
                hintText = "Старая связка блеснула там, где переплетаются ветви на краю поляны."
            )
        )
    ),
    FairyLocationDef(
        id = "bazaar",
        title = "Восточный базар",
        fairyTaleName = "Аладдин",
        heroName = "Аладдин",
        heroTitle = "Искатель чудес",
        shortSummary = "В красочной лавке чудес среди ковров, фонарей и самоцветов затерялась та самая волшебная лампа!",
        sceneDrawableRes = R.drawable.img_scene_bazaar,
        mapXFraction = 0.75f,
        mapYFraction = 0.32f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Аладдин",
                role = "Герой сказки",
                isSliva = false,
                text = "Салям, юный детектив! В суматохе восточной лавки пропала моя медная лампа, свёрнутый ковёр-самолёт и драгоценный сапфир!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Мур-р, сколько золота и ярких тканей! Глаза разбегаются — именно поэтому искать здесь труднее всего. Проверяй каждую подушку и полку!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Аладдин",
                role = "Герой сказки",
                isSliva = false,
                text = "Лампа спасена, а мой ковёр снова готов к полёту! Кстати, в песочных часах на полке я нашёл пушистую зимнюю варежку..."
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Тёплая рукавица посреди жаркого Востока?! Это знак из зимнего терема Морозко — летим туда без промедления!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "bazaar_lamp",
                name = "Волшебная лампа",
                kind = HiddenItemKind.MAGIC_LAMP,
                xFraction = 0.52f,
                yFraction = 0.63f,
                rotationDeg = -6f,
                hintText = "Тёплая медь хранит в себе древнего духа посреди бархатных тканей.",
                clueInfo = ClueInfo(
                    clueId = "clue_genie_lamp",
                    title = "Старинная лампа чудес",
                    description = "Едва потёрли бок, как из носика поднялся лёгкий сиреневый дымок.",
                    slivaNote = "Главное — загадывать добрые и мудрые желания, мур-р!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "bazaar_carpet",
                name = "Ковёр-самолёт",
                kind = HiddenItemKind.CARPET_ROLL,
                xFraction = 0.23f,
                yFraction = 0.74f,
                rotationDeg = 14f,
                hintText = "Узорчатый рулон всё ещё подрагивает, мечтая о ветре и небе."
            ),
            HiddenObjectDef(
                id = "bazaar_sapphire",
                name = "Восточный сапфир",
                kind = HiddenItemKind.SAPPHIRE_GEM,
                xFraction = 0.76f,
                yFraction = 0.45f,
                rotationDeg = 8f,
                hintText = "Глубокая синева камня переливается в тени резной решётки.",
                clueInfo = ClueInfo(
                    clueId = "clue_royal_sapphire",
                    title = "Сапфир из пещеры чудес",
                    description = "Идеально огранённый камень, сияющий как ночное небо над пустыней.",
                    slivaNote = "Потрясающая находка! В нашем архиве такого ещё не было.",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "bazaar_amphora",
                name = "Расписная амфора",
                kind = HiddenItemKind.AMPHORA,
                xFraction = 0.14f,
                yFraction = 0.54f,
                rotationDeg = -8f,
                hintText = "Высокий сосуд прячет благовония среди шёлковых занавесей."
            ),
            HiddenObjectDef(
                id = "bazaar_hourglass",
                name = "Песочные часы",
                kind = HiddenItemKind.HOURGLASS,
                xFraction = 0.64f,
                yFraction = 0.28f,
                rotationDeg = 5f,
                hintText = "Золотые песчинки сыплются там, где висят цветные фонарики."
            ),
            HiddenObjectDef(
                id = "bazaar_pouch",
                name = "Мешочек дирхамов",
                kind = HiddenItemKind.COIN_POUCH,
                xFraction = 0.41f,
                yFraction = 0.82f,
                rotationDeg = -14f,
                hintText = "Звонкие монеты притихли на мягком восточном ковре."
            ),
            HiddenObjectDef(
                id = "bazaar_goblet",
                name = "Чеканный кубок",
                kind = HiddenItemKind.GOBLET,
                xFraction = 0.85f,
                yFraction = 0.67f,
                rotationDeg = 10f,
                hintText = "Серебро с тонким орнаментом ждёт пира у подносов."
            ),
            HiddenObjectDef(
                id = "bazaar_teapot",
                name = "Кофейник с мятой",
                kind = HiddenItemKind.TEAPOT,
                xFraction = 0.32f,
                yFraction = 0.42f,
                rotationDeg = -12f,
                hintText = "Аромат пряного чая поднимается с узкой деревянной полки."
            ),
            HiddenObjectDef(
                id = "bazaar_seal",
                name = "Перстень визиря",
                kind = HiddenItemKind.WAX_SEAL,
                xFraction = 0.79f,
                yFraction = 0.83f,
                rotationDeg = 18f,
                hintText = "Алый знак власти выронили среди подушек с кисточками."
            ),
            HiddenObjectDef(
                id = "bazaar_lantern",
                name = "Мозаичный фонарь",
                kind = HiddenItemKind.LANTERN,
                xFraction = 0.39f,
                yFraction = 0.18f,
                rotationDeg = 0f,
                hintText = "Тёплый витражный свет мерцает под самым резным потолком."
            ),
            HiddenObjectDef(
                id = "bazaar_mittens",
                name = "Заблудившаяся варежка",
                kind = HiddenItemKind.MITTENS,
                xFraction = 0.63f,
                yFraction = 0.72f,
                rotationDeg = -18f,
                hintText = "Странная зимняя гостья греется среди шёлковых платков.",
                clueInfo = ClueInfo(
                    clueId = "clue_stray_mitten",
                    title = "Заснеженная рукавица",
                    description = "Вышита северными узорами — совершенно невозможна в жаркой лавке!",
                    slivaNote = "Вот она, таинственная связь со сказкой Морозко!",
                    rarity = "common"
                )
            ),
            HiddenObjectDef(
                id = "bazaar_brooch",
                name = "Брошь султанши",
                kind = HiddenItemKind.BROOCH,
                xFraction = 0.16f,
                yFraction = 0.25f,
                rotationDeg = 22f,
                hintText = "Драгоценная застёжка сияет вдали от суеты, на верхушке арки."
            )
        )
    ),
    FairyLocationDef(
        id = "morozko",
        title = "Терем Морозко",
        fairyTaleName = "Морозко",
        heroName = "Настенька",
        heroTitle = "Добрая мастерица",
        shortSummary = "В заснеженном резном тереме вьюга замела приданое: серебряный ларец, самовар, рушник и посох Морозко.",
        sceneDrawableRes = R.drawable.img_scene_morozko,
        mapXFraction = 0.28f,
        mapYFraction = 0.21f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Настенька",
                role = "Героиня сказки",
                isSliva = false,
                text = "Милости просим в тёплый терем! Только что пронёсся снежный вихрь — и пропали самовар, нарядный ларец и посох дедушки Морозко!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Мур-р, как уютно у печки! Но будь начеку: резное дерево, серебро и кружево искусно прячут каждую деталь. Осмотри избу целиком!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Настенька",
                role = "Героиня сказки",
                isSliva = false,
                text = "Благодарю от всего сердца! В ларце оказался золотой орешек и корабельный знак далёкого острова..."
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Золотые орешки — это же сокровище белки с острова Буян! Впереди финал нашего большого приключения, детектив!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "moroz_casket",
                name = "Серебряный ларец",
                kind = HiddenItemKind.SILVER_CASKET,
                xFraction = 0.49f,
                yFraction = 0.64f,
                rotationDeg = -5f,
                hintText = "Заветное приданое блестит там, где собираются за общим столом.",
                clueInfo = ClueInfo(
                    clueId = "clue_dowry_casket",
                    title = "Заветный ларец Морозко",
                    description = "Полон серебра и кружевных сокровищ, подаренных за доброе сердце.",
                    slivaNote = "Награда за терпение и трудолюбие, достойная учебника сыска!",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "moroz_staff",
                name = "Посох Морозко",
                kind = HiddenItemKind.FROST_STAFF,
                xFraction = 0.81f,
                yFraction = 0.39f,
                rotationDeg = 14f,
                hintText = "Ледяной жезл дремлет рядом с резными наличниками."
            ),
            HiddenObjectDef(
                id = "moroz_samovar",
                name = "Медный самовар",
                kind = HiddenItemKind.SAMOVAR,
                xFraction = 0.25f,
                yFraction = 0.54f,
                rotationDeg = 0f,
                hintText = "Пузатый хранитель чаепития притих у тёплых бревенчатых стен."
            ),
            HiddenObjectDef(
                id = "moroz_mittens",
                name = "Узорные варежки",
                kind = HiddenItemKind.MITTENS,
                xFraction = 0.65f,
                yFraction = 0.79f,
                rotationDeg = -14f,
                hintText = "Шерстяная пара упала на мягкий половик под ногами."
            ),
            HiddenObjectDef(
                id = "moroz_towel",
                name = "Кружевной рушник",
                kind = HiddenItemKind.TOWEL,
                xFraction = 0.36f,
                yFraction = 0.26f,
                rotationDeg = 8f,
                hintText = "Расшитый узорами лён украшает светлое окошко высоко от пола."
            ),
            HiddenObjectDef(
                id = "moroz_snowflake",
                name = "Иней-звезда",
                kind = HiddenItemKind.SNOWFLAKE,
                xFraction = 0.14f,
                yFraction = 0.23f,
                rotationDeg = 20f,
                hintText = "Дыхание зимы застыло на самом морозном краю переплёта."
            ),
            HiddenObjectDef(
                id = "moroz_spoon",
                name = "Деревянная ложка",
                kind = HiddenItemKind.WOODEN_SPOON,
                xFraction = 0.39f,
                yFraction = 0.78f,
                rotationDeg = 26f,
                hintText = "Резной черпачок ждёт ароматных щей на нижней лавке."
            ),
            HiddenObjectDef(
                id = "moroz_pot",
                name = "Печной чугунок",
                kind = HiddenItemKind.CLAY_POT,
                xFraction = 0.16f,
                yFraction = 0.74f,
                rotationDeg = -8f,
                hintText = "Скромный глиняный труженик греется в углу у расписной печи."
            ),
            HiddenObjectDef(
                id = "moroz_nut",
                name = "Золотой орешек",
                kind = HiddenItemKind.GOLDEN_NUT,
                xFraction = 0.68f,
                yFraction = 0.29f,
                rotationDeg = 12f,
                hintText = "Диковинная скорлупка с изумрудным ядром прячется на высокой полке.",
                clueInfo = ClueInfo(
                    clueId = "clue_golden_nut",
                    title = "Золотой орешек с Буяна",
                    description = "Скорлупка из чистого золота — внутри звенит изумрудное ядро.",
                    slivaNote = "Последний шаг нашего расследования ведёт к острову Буян!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "moroz_ribbon",
                name = "Девичья лента",
                kind = HiddenItemKind.RIBBON,
                xFraction = 0.56f,
                yFraction = 0.44f,
                rotationDeg = -18f,
                hintText = "Алый бант запутался в центре горницы среди прялок."
            ),
            HiddenObjectDef(
                id = "moroz_thimble",
                name = "Мастерицын напёрсток",
                kind = HiddenItemKind.THIMBLE,
                xFraction = 0.85f,
                yFraction = 0.73f,
                rotationDeg = 15f,
                hintText = "Крошечный золотой колпачок укатился под дальнюю скамью.",
                clueInfo = ClueInfo(
                    clueId = "clue_lace_thimble",
                    title = "Снежный напёрсток",
                    description = "Им вышивают узоры, не тающие даже в весеннее тепло.",
                    slivaNote = "Прекрасный образец северного мастерства!",
                    rarity = "common"
                )
            ),
            HiddenObjectDef(
                id = "moroz_bell",
                name = "Бубенчик саней",
                kind = HiddenItemKind.REINDEER_BELL,
                xFraction = 0.51f,
                yFraction = 0.16f,
                rotationDeg = -10f,
                hintText = "Звонкая мелодия тройки замерла под деревянной балкой."
            )
        )
    ),
    FairyLocationDef(
        id = "buyan",
        title = "Остров Буян",
        fairyTaleName = "Сказка о царе Салтане",
        heroName = "Царевич Гвидон",
        heroTitle = "Князь острова",
        shortSummary = "На чудесной пристани белокаменного града пропали корона Царевны-Лебеди, золотые орешки и якорь корабля.",
        sceneDrawableRes = R.drawable.img_scene_buyan,
        mapXFraction = 0.72f,
        mapYFraction = 0.11f,
        introDialogue = listOf(
            DialogueLine(
                speaker = "Царевич Гвидон",
                role = "Герой сказки",
                isSliva = false,
                text = "Здравствуй, славный сыщик! Перед приездом гостей с моря пропали золотые орешки белки, венец Царевны-Лебеди и подзорная труба с пристани!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Вот она, вершина твоего мастерства, ученик! Остров Буян сияет златом и лазурью — будь предельно зорок, это выпускной экзамен!"
            )
        ),
        outroDialogue = listOf(
            DialogueLine(
                speaker = "Царевич Гвидон",
                role = "Герой сказки",
                isSliva = false,
                text = "Все чудеса возвращены на свои места! За твоё искусство слава о школе юных детективов разлетится по всем морям и сказкам!"
            ),
            DialogueLine(
                speaker = "Слива Владиславовна",
                role = "Кошка-директор школы",
                isSliva = true,
                text = "Мяу-браво, Магистр Сказочного сыска! Ты раскрыл дела во всех восьми сказках! Горжусь твоей дедукцией и орлиным взглядом!"
            )
        ),
        items = listOf(
            HiddenObjectDef(
                id = "buyan_swan",
                name = "Венец Царевны-Лебеди",
                kind = HiddenItemKind.SWAN_CROWN,
                xFraction = 0.48f,
                yFraction = 0.25f,
                rotationDeg = 0f,
                hintText = "Месяц под косой блестит высоко на фоне белокаменных палат.",
                clueInfo = ClueInfo(
                    clueId = "clue_swan_crown",
                    title = "Корона Царевны-Лебеди",
                    description = "Изящный венец с жемчужинами, озаряющий весь остров мягким светом.",
                    slivaNote = "Главная королевская реликвия всей Сказочной страны!",
                    rarity = "legendary"
                )
            ),
            HiddenObjectDef(
                id = "buyan_nut",
                name = "Изумрудный орех",
                kind = HiddenItemKind.GOLDEN_NUT,
                xFraction = 0.24f,
                yFraction = 0.62f,
                rotationDeg = 14f,
                hintText = "Белка уронила драгоценный плод на каменный парапет пристани."
            ),
            HiddenObjectDef(
                id = "buyan_anchor",
                name = "Бронзовый якорь",
                kind = HiddenItemKind.ANCHOR,
                xFraction = 0.79f,
                yFraction = 0.77f,
                rotationDeg = -18f,
                hintText = "Морской страж кораблей уснул у причальных канатов."
            ),
            HiddenObjectDef(
                id = "buyan_telescope",
                name = "Труба дозорного",
                kind = HiddenItemKind.TELESCOPE,
                xFraction = 0.68f,
                yFraction = 0.36f,
                rotationDeg = 20f,
                hintText = "Морская оптика всматривается в паруса со стороны башни."
            ),
            HiddenObjectDef(
                id = "buyan_coral",
                name = "Алый коралл",
                kind = HiddenItemKind.CORAL_BRANCH,
                xFraction = 0.16f,
                yFraction = 0.81f,
                rotationDeg = -8f,
                hintText = "Дар тридцати трёх богатырей выбросило прибоем на камни.",
                clueInfo = ClueInfo(
                    clueId = "clue_sea_coral",
                    title = "Коралл тридцати трёх богатырей",
                    description = "Ветвь морского коралла, переливающаяся каплями солёного прибоя.",
                    slivaNote = "Богатыри передали привет самому зоркому сыщику!",
                    rarity = "rare"
                )
            ),
            HiddenObjectDef(
                id = "buyan_pouch",
                name = "Купеческая казна",
                kind = HiddenItemKind.COIN_POUCH,
                xFraction = 0.41f,
                yFraction = 0.73f,
                rotationDeg = 10f,
                hintText = "Подарок заморских гостей оставлен на ступенях набережной."
            ),
            HiddenObjectDef(
                id = "buyan_compass",
                name = "Морской компас",
                kind = HiddenItemKind.COMPASS,
                xFraction = 0.58f,
                yFraction = 0.58f,
                rotationDeg = -12f,
                hintText = "Путеводная стрелка дрожит на пути от пристани к городу."
            ),
            HiddenObjectDef(
                id = "buyan_scroll",
                name = "Грамота Салтана",
                kind = HiddenItemKind.SCROLL,
                xFraction = 0.84f,
                yFraction = 0.51f,
                rotationDeg = -22f,
                hintText = "Царское послание прибило ветром к расписной стене.",
                clueInfo = ClueInfo(
                    clueId = "clue_saltan_charter",
                    title = "Царская грамота с печатью",
                    description = "Официальное приглашение юному детективу на великий пир.",
                    slivaNote = "Поздравляю, выпускник — это высшая благодарность!",
                    rarity = "common"
                )
            ),
            HiddenObjectDef(
                id = "buyan_goblet",
                name = "Златой кубок",
                kind = HiddenItemKind.GOBLET,
                xFraction = 0.31f,
                yFraction = 0.41f,
                rotationDeg = 6f,
                hintText = "Праздничная чаша искрится под сенью золотых куполов."
            ),
            HiddenObjectDef(
                id = "buyan_amphora",
                name = "Восточный сосуд",
                kind = HiddenItemKind.AMPHORA,
                xFraction = 0.64f,
                yFraction = 0.84f,
                rotationDeg = 8f,
                hintText = "Заморское угощение выгрузили прямо к краю пристани."
            ),
            HiddenObjectDef(
                id = "buyan_key",
                name = "Ключ от сокровищницы",
                kind = HiddenItemKind.CARRIAGE_KEY,
                xFraction = 0.14f,
                yFraction = 0.31f,
                rotationDeg = -26f,
                hintText = "Маленькая металлическая деталь блестит среди белых арок."
            ),
            HiddenObjectDef(
                id = "buyan_pearl_brooch",
                name = "Жемчужная брошь",
                kind = HiddenItemKind.FAIRY_BROOCH,
                xFraction = 0.82f,
                yFraction = 0.19f,
                rotationDeg = 12f,
                hintText = "Нежный морской отблеск затаился возле самых высоких шпилей."
            )
        )
    )
)
