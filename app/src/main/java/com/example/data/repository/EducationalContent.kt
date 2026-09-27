package com.example.data.repository

import com.example.data.model.*

object EducationalContent {

    val levels = listOf(
        LearningLevel(
            levelNumber = 1,
            title = "Alphabet & Qubee",
            oromoTitle = "Qubee A hanga Z",
            amharicTitle = "የእንግሊዝኛ ፊደላት",
            description = "Learn letters A to Z with Qubee phonics, sound and tracing",
            oromoDescription = "Qubee A hanga Z sagaleefi barreeffamaan sirriitti baradhaa",
            isUnlocked = true,
            xpReward = 50,
            iconName = "abc"
        ),
        LearningLevel(
            levelNumber = 2,
            title = "Letter Sounds & Phonics",
            oromoTitle = "Sagalee Qubeefi Qubee Dachaa",
            amharicTitle = "የድምጾች ውህደት",
            description = "Discover English digraphs like SH, CH, TH, PH, WH with Qubee Dachaa",
            oromoDescription = "Qubee Dachaa Afaan Ingilizii (SH, CH, TH, PH) Afaan Oromoo wajjin walbira qabaa",
            isUnlocked = true,
            xpReward = 60,
            iconName = "record_voice_over"
        ),
        LearningLevel(
            levelNumber = 3,
            title = "Simple Words",
            oromoTitle = "Jechoota Salphaa",
            amharicTitle = "ቀላል ቃላት",
            description = "Read first 3-4 letter English words with colorful pictures",
            oromoDescription = "Jechoota gaggabaaboo fakkiiwwan miidhagoo wajjin dubbisaa",
            isUnlocked = true,
            xpReward = 70,
            iconName = "menu_book"
        ),
        LearningLevel(
            levelNumber = 4,
            title = "Vocabulary Builder",
            oromoTitle = "Kuusaa Jechootaa",
            amharicTitle = "የቃላት ክምችት",
            description = "Explore 16 categories including Oromo culture & heritage",
            oromoDescription = "Aadaa Oromoo dabalatee garee 16 keessatti jechoota haaraa baradhaa",
            isUnlocked = true,
            xpReward = 80,
            iconName = "category"
        ),
        LearningLevel(
            levelNumber = 5,
            title = "Sentence Builder",
            oromoTitle = "Hima Ijaaruu",
            amharicTitle = "ዓረፍተ ነገር መገንባት",
            description = "Assemble scrambled words into correct English sentences",
            oromoDescription = "Jechoota wal-makaa sirreessuudhaan hima sirrii uumaa",
            isUnlocked = true,
            xpReward = 90,
            iconName = "extension"
        ),
        LearningLevel(
            levelNumber = 6,
            title = "Reading Paragraphs",
            oromoTitle = "Keeyyata Dubbisuu",
            amharicTitle = "አንቀጾችን ማንበብ",
            description = "Read short texts with live fluency & WPM speed tracking",
            oromoDescription = "Keeyyattoota gaggabaaboo saffisaafi qulqullinaan dubbisuu shaakalaa",
            isUnlocked = true,
            xpReward = 100,
            iconName = "chrome_reader_mode"
        ),
        LearningLevel(
            levelNumber = 7,
            title = "Story Reading",
            oromoTitle = "Ooduu fi Seenaawwan",
            amharicTitle = "አዝናኝ ታሪኮች",
            description = "Read interactive stories of Irreecha, Finfinne, and folk fables",
            oromoDescription = "Seenaa Irreechaa, Finfinneefi oduu durii mi'aawaa dubbisaa",
            isUnlocked = true,
            xpReward = 120,
            iconName = "auto_stories"
        ),
        LearningLevel(
            levelNumber = 8,
            title = "Daily Conversations",
            oromoTitle = "Waliin Dubbii Guyyaa Guyyaa",
            amharicTitle = "የዕለት ተዕለት ውይይቶች",
            description = "Speak naturally at school, at home, and greeting elders",
            oromoDescription = "Mana barumsaatti, mana keessattifi maatii wajjin Afaan Ingilizii dubbadhaa",
            isUnlocked = true,
            xpReward = 130,
            iconName = "forum"
        ),
        LearningLevel(
            levelNumber = 9,
            title = "Situational Dialogues",
            oromoTitle = "Waliin Dubbii Haala Addaa",
            amharicTitle = "ሁኔታዊ ንግግሮች",
            description = "Practice speaking at Gabaa, Buna Ceremony, and Irreecha lake",
            oromoDescription = "Gabaa keessatti, sirna Bunaafi Ayyaana Irreechaa irratti mari'adhaa",
            isUnlocked = true,
            xpReward = 140,
            iconName = "store"
        ),
        LearningLevel(
            levelNumber = 10,
            title = "Pronunciation Lab",
            oromoTitle = "Laabii Sagaleessuu",
            amharicTitle = "የአነባበብ ላቦራቶሪ",
            description = "Master sounds difficult for Afaan Oromoo speakers (P vs B/F, V vs W, TH)",
            oromoDescription = "Sagaleewwan dubbii Afaan Ingilizii adda ta'an (P fi B, V fi W, TH) addaan baasaa",
            isUnlocked = true,
            xpReward = 150,
            iconName = "mic"
        )
    )

    val alphabetList = listOf(
        AlphabetLetter('A', 'a', "æ (short a)", "Apple", "Aappilii", "ፖም", "I eat a sweet red apple.", "Aappilii diimaafi mi'aawaan nyaadha.", "Qubee Afaan Oromoo keessatti 'A' sagalee /a/ qaba; Afaan Ingilizii keessatti immoo akka 'Apple' /æ/ ta'a.", "🍎"),
        AlphabetLetter('B', 'b', "b", "Ball", "Kubbaa", "ኳስ", "The boy kicks the ball.", "Gurbichi kubbaa dhiita.", "Afaan Oromoo fi Afaan Ingilizii keessatti sagaleen 'B' wal fakkaata.", "⚽"),
        AlphabetLetter('C', 'c', "k", "Cat", "Adurree", "ድመት", "The cat is sleeping on the mat.", "Adurreen afata irra raftee jirti.", "Hubadhaa: Qubee Afaan Oromoo keessatti 'C' sagalee /tʃ/ qaba (akka Caaltuu), garuu Afaan Ingilizii keessatti 'C' baay'inaan akka /k/ ('Cat') dubbifama!", "🐱"),
        AlphabetLetter('D', 'd', "d", "Dog", "Saree", "ውሻ", "The dog barks happily.", "Sareen gamachuudhaan dutti.", "Sagaleen 'D' Afaan Oromoo fi Ingilizii keessatti walitti dhiyaata.", "🐶"),
        AlphabetLetter('E', 'e', "ɛ (short e)", "Elephant", "Arba", "ዝሆን", "The elephant is very big.", "Arbi bineensa baay'ee guddaadha.", "Qubee 'E' Afaan Ingilizii keessatti sagalee /ɛ/ akka 'Elephant' kenna.", "🐘"),
        AlphabetLetter('F', 'f', "f", "Fish", "Qurxummii", "ዓሳ", "The fish swims in the river.", "Qurxummiin laga keessa daaka.", "Afaan Oromoo keessattis 'F' akka 'Farda' jedhutti dhagahama.", "🐟"),
        AlphabetLetter('G', 'g', "ɡ", "Goat", "Re'ee", "ፍየል", "The goat eats green grass.", "Re'een marga magariisa nyaatti.", "Qubee 'G' akka 'Gaala' ykn 'Gadaa' jedhutti dhagahama.", "🐐"),
        AlphabetLetter('H', 'h', "h", "Hat", "Kofiyyaa", "ቆብ", "He wears a warm hat.", "Kofiyyaa ho'aa uffata.", "Qubee 'H' akka 'Harka' ykn 'Hora' jedhutti dhagahama.", "🎩"),
        AlphabetLetter('I', 'i', "ɪ (short i)", "Injera", "Injeeraa / Buddeena", "እንጀራ", "Injera is our delicious staple food.", "Buddeenni nyaata aadaa keenya isa baay'ee mi'aawaadha.", "Qubee 'I' Afaan Ingilizii keessatti sagalee /ɪ/ qaba.", "🫓"),
        AlphabetLetter('J', 'j', "dʒ", "Jug", "Kuubbayyaa", "ጆግ", "Pour fresh water into the jug.", "Bishaan qulqulluu kuubbayyaa keessatti naqi.", "Qubee 'J' akka 'Jireenya' jedhutti dhagahama.", "🫖"),
        AlphabetLetter('K', 'k', "k", "Kite", "Kaayittii", "ካይት", "The kite flies high in the sky.", "Kaayittiin samii keessa ol barrifti.", "Qubee 'K' akka 'Keelloo' jedhutti dhagahama.", "🪁"),
        AlphabetLetter('L', 'l', "l", "Lion", "Leenca", "አንበሳ", "The brave lion roars loudly.", "Leenci gooti sagalee guddaan aada.", "Qubee 'L' akka 'Leenca' jedhutti dhagahama.", "🦁"),
        AlphabetLetter('M', 'm', "m", "Monkey", "Qamalee", "ዝንጀሮ", "The monkey climbs the tall tree.", "Qamaleen muka dheeraa irra baati.", "Qubee 'M' akka 'Muka' jedhutti dhagahama.", "🐒"),
        AlphabetLetter('N', 'n', "n", "Nest", "Man'ee simbiraa", "የወፍ ጎጆ", "The bird sleeps in its nest.", "Simbirron man'ee ishee keessa rafti.", "Qubee 'N' akka 'Nageenya' jedhutti dhagahama.", "🪺"),
        AlphabetLetter('O', 'o', "ɒ (short o)", "Orange", "Burtukaana", "ብርቱካን", "An orange is sweet and juicy.", "Burtukaanni mi'aawaafi dhangala'aa gaarii qaba.", "Qubee 'O' akka 'Odaa' jedhutti jalqaba.", "🍊"),
        AlphabetLetter('P', 'p', "p (puff of air)", "Pencil", "Qobdoo / Irsaasii", "እርሳስ", "I write my name with a pencil.", "Maqaa koo irsaasiidhaan barreesse.", "Xiyyeeffannaa: 'P' hidhii lamaan cufuudhaan qilleensa humnaan baasuun dubbifama. 'B' wajjin wal hin makiinaa!", "✏️"),
        AlphabetLetter('Q', 'q', "kw", "Queen", "Mootittii", "ንግሥት", "The queen wears a golden crown.", "Mootittiin gonfoo warqee keessi.", "Afaan Oromoo keessatti 'Q' akka 'Qubee' sagalee dhooqaati; Afaan Ingilizii keessatti immoo 'kw' fakkaata!", "👑"),
        AlphabetLetter('R', 'r', "r", "Rainbow", "Sabbata Waaqayyoo", "ቀስተ ደመና", "Look at the colorful rainbow.", "Sabbata Waaqayyoo halluu hedduu qabu ilaalaa.", "Qubee 'R' akka 'Rooba' jedhutti dhagahama.", "🌈"),
        AlphabetLetter('S', 's', "s", "Sun", "Aduu", "ፀሐይ", "The sun shines bright in Oromia.", "Aduun ifa bareedaa kenniti.", "Qubee 'S' akka 'Siinqee' jedhutti dhagahama.", "☀️"),
        AlphabetLetter('T', 't', "t", "Tree", "Muka", "ዛፍ", "Birds sing in the green tree.", "Simbirronni muka magariisa irra ta'anii sirbu.", "Qubee 'T' akka 'Tulluu' jedhutti dhagahama.", "🌳"),
        AlphabetLetter('U', 'u', "ʌ (short u)", "Umbrella", "Dambal / Gaachana Roobaa", "ጃንጥላ", "Open your umbrella when it rains.", "Yeroo roobu dambal kee bani.", "Qubee 'U' Afaan Ingilizii keessatti akka /ʌ/ ('Umbrella') ta'a.", "☂️"),
        AlphabetLetter('V', 'v', "v (vibrate teeth/lip)", "Van", "Makiinaa Vaan", "ቫን መኪና", "The school van arrives early.", "Vaanin mana barumsaa ganamaan dhufe.", "Xiyyeeffannaa: 'V' ilkaan gubbaa hidhii gadii irra kaawwatee dhidhiituudhaan dubbifama. 'W' ykn 'B' irraa adda!", "🚐"),
        AlphabetLetter('W', 'w', "w", "Water", "Bishaan", "ውሃ", "Water is healthy and fresh.", "Bishaan fayyaafi qulqulluudha.", "Qubee 'W' akka 'Waaqa' ykn 'Wandaboo' jedhutti dhagahama.", "💧"),
        AlphabetLetter('X', 'x', "ks", "Xylophone", "Zayilofoonii", "ዛይሎፎን", "Play joyful music on the xylophone.", "Zayilofoonii irratti muuziqaa gaarii taphadhaa.", "Afaan Oromoo keessatti 'X' akka 'Xiyyaara'ti; Afaan Ingilizii keessatti 'ks' fakkaata.", "🎵"),
        AlphabetLetter('Y', 'y', "j", "Yellow", "Keelloo", "ቢጫ", "The sunflower is bright yellow.", "Abaaboon biiftuu keelloo baredaadha.", "Qubee 'Y' akka 'Yaada' jedhutti dhagahama.", "🌻"),
        AlphabetLetter('Z', 'z', "z", "Zebra", "Harree Diidaa", "የሜዳ አህያ", "The zebra has black and white stripes.", "Harreen diidaa sarara gurraachaafi adii qabdi.", "Qubee 'Z' akka 'Zayitii' jedhutti dhagahama.", "🦓")
    )

    val phonicsSounds = listOf(
        PhonicsSound(
            id = "sh",
            category = "Qubee Dachaa (Digraph)",
            pattern = "SH /ʃ/",
            pronunciationTip = "Round your lips softly and blow gently: 'shhh' like asking someone to be quiet.",
            oromoComparison = "Afaan Oromoo keessattis 'SH' Qubee Dachaa beekamaadha! Akka 'Shan', 'Shanan', 'Shimala' jedhutti dubbifama.",
            amharicComparison = "እንደ 'ሸ' ድምፅ ይወጣል",
            words = listOf("Ship", "Shop", "Fish", "Shoe", "Shell"),
            sampleSentences = listOf("The ship sails on the sea.", "I wear my new shoe.", "She found a shiny shell.")
        ),
        PhonicsSound(
            id = "ch",
            category = "Qubee Dachaa (Digraph)",
            pattern = "CH /tʃ/",
            pronunciationTip = "Start with a crisp 'T' snap and release into 'SH'.",
            oromoComparison = "Afaan Oromoo keessatti 'CH' Qubee Dachaa yoo ta'u akka 'Caaltuu', 'Caffee', 'Cilaloo' jedhutti dhagahama!",
            amharicComparison = "እንደ 'ቸ' ድምፅ ይወጣል",
            words = listOf("Chair", "Cheese", "Chip", "Catch", "Church"),
            sampleSentences = listOf("Sit on the wooden chair.", "Mice love tasty cheese.", "Catch the red ball!")
        ),
        PhonicsSound(
            id = "th_unvoiced",
            category = "Qubee Dachaa (Digraph)",
            pattern = "TH /θ/ (Laafaa / Soft)",
            pronunciationTip = "Gently put the tip of your tongue between front teeth and blow air out softly without vibrating voice cords.",
            oromoComparison = "Arraba kee ilkaan gubbaafi gadii gidduu muraasa baasiiti qilleensa qofa baasi. Akka 'Think', 'Three'!",
            amharicComparison = "ምላስ በጥርስ መሀል በማድረግ የሚወጣ ረጋ ያለ ድምፅ",
            words = listOf("Think", "Three", "Tooth", "Math", "Thumb"),
            sampleSentences = listOf("Think of a good idea.", "I have three sisters.", "Brush every tooth clean.")
        ),
        PhonicsSound(
            id = "th_voiced",
            category = "Qubee Dachaa (Digraph)",
            pattern = "TH /ð/ (Dhidhiitaa / Voiced)",
            pronunciationTip = "Place tongue tip between teeth and turn on your vocal motor vibration.",
            oromoComparison = "Arraba ilkaan gidduu kaawwattee kokkeen kee akka hollatu godhi. Akka 'This', 'That', 'Mother'!",
            amharicComparison = "ምላስ በጥርስ መሀል ሆኖ ድምፁ የሚንዘረዘር",
            words = listOf("This", "That", "Mother", "Father", "Together"),
            sampleSentences = listOf("This is my book.", "Help your mother cook.", "We play together happily.")
        ),
        PhonicsSound(
            id = "wh",
            category = "Qubee Dachaa (Digraph)",
            pattern = "WH /w/",
            pronunciationTip = "Round your lips into an 'O' ring and blow air out softly.",
            oromoComparison = "Hidhii kee akka geengoo gootee sagalee laafaan dubbisi. Akka 'What', 'Where', 'When'!",
            amharicComparison = "ከንፈርን በማድበልበል የሚወጣ ድምፅ",
            words = listOf("What", "When", "White", "Wheel", "Whale"),
            sampleSentences = listOf("What is your name?", "The paper is white.", "The blue whale is huge.")
        ),
        PhonicsSound(
            id = "ph",
            category = "Qubee Dachaa (Digraph)",
            pattern = "PH /f/",
            pronunciationTip = "In English, P and H together sound just like letter 'F'!",
            oromoComparison = "Afaan Ingilizii keessatti 'P' fi 'H' walitti dhufanii sagalee 'F' kennu! (Akka Phone = Foon).",
            amharicComparison = "ፊደል 'P' እና 'H' አንድ ላይ እንደ 'ፍ' ይነበባሉ",
            words = listOf("Phone", "Photo", "Elephant", "Dolphin", "Graph"),
            sampleSentences = listOf("Answer the mobile phone.", "Take a colorful photo.", "The elephant is smart.")
        )
    )

    val vocabularyCategories = listOf(
        "Aadaa Oromoo (Culture)", "Animals (Bineensota)", "Food (Nyaata)", "Family (Maatii)",
        "School (Mana Barumsaa)", "Body Parts (Qaama)", "Colors (Halluuwwan)", "Numbers (Lakkoofsota)",
        "Shapes (Bocawwan)", "Transportation (Geejjiba)", "Nature (Uumama)", "Jobs (Hojiiwwan)",
        "Clothes (Uffata)", "Actions (Gocha)", "Greetings (Nagaa Gaafachuu)", "Weather (Haala Qilleensaa)"
    )

    val vocabularyList = listOf(
        // Aadaa Oromoo (Oromo Culture & Heritage)
        VocabularyWord("oro_1", "Aadaa Oromoo (Culture)", "Odaa", "Odaa", "የኦዳ ዛፍ", "Oh-daa", "The sacred Odaa sycamore tree is a symbol of unity, shade, and Gadaa democracy.", "Muki Odaa mallattoo nageenyaa, tokkummaafi sirna Gadaati.", "የኦዳ ዛፍ የሰላምና የታሪክ ምልክት ነው።", "🌳", true),
        VocabularyWord("oro_2", "Aadaa Oromoo (Culture)", "Irreecha", "Irreecha", "ኢሬቻ", "Ir-ree-cha", "Irreecha is the colorful Oromo Thanksgiving festival celebrated at Lake Hora Harsadi.", "Irreechi ayyaana galateeffannaa Waaqaa isa Hora Harsadiitti kabajamuudha.", "ኢሬቻ በሆራ ሐርሰዲ የሚከበር የምስጋና በዓል ነው።", "🌼", true),
        VocabularyWord("oro_3", "Aadaa Oromoo (Culture)", "Coffee / Buna", "Buna", "ቡና", "Bu-na / Kaw-fee", "Grandmother roasts fresh beans and serves traditional Buna Qalaa.", "Aayyoon buna qalaa qopheessitee nagaafi eebba kenniti.", "አያቴ የባህል ቡና ታፈላለች።", "☕", true),
        VocabularyWord("oro_4", "Aadaa Oromoo (Culture)", "Ancootee", "Ancootee", "አንጮቴ", "An-choo-tay", "Ancootee is a delicious nutritious traditional root dish of Wallagga.", "Ancooteen nyaata aadaa dhandhama gaariifi madaalawaa qabudha.", "አንጮቴ ተወዳጅ ባህላዊ ምግብ ነው።", "🍠", true),
        VocabularyWord("oro_5", "Aadaa Oromoo (Culture)", "Tulluu Diimtuu", "Tulluu Diimtuu", "ቱሉ ዲምቱ", "Tul-luu Deem-too", "Tulluu Diimtuu in the Bale Mountains is one of the highest peaks in Ethiopia.", "Tulluu Diimtuu gaarren Baalee keessatti tulluu baay'ee ol-dheeraadha.", "ቱሉ ዲምቱ በባሌ ተራሮች ውስጥ ረዥም ተራራ ነው።", "🏔️", true),
        VocabularyWord("oro_6", "Aadaa Oromoo (Culture)", "Siinqee", "Siinqee", "ሲንቄ", "Seen-qay", "The sacred Siinqee stick represents the honor and legal rights of Oromo mothers.", "Siinqeen ulee aadaa kabajaafi mirga haadholii Oromoo agarsiisudha.", "ሲንቄ የሴቶች ክብርና መብት መገለጫ ነው።", "🪄", true),
        VocabularyWord("oro_7", "Aadaa Oromoo (Culture)", "Injera / Buddeena", "Buddeena", "እንጀራ", "Bud-dee-na / In-je-ra", "We eat fresh spicy wot with healthy teff injera.", "Buddeena xaafii irraa tolfame ittoo wajjin dhandhamanna.", "እንጀራ ከወጥ ጋር እንበላለን።", "🫓", true),
        VocabularyWord("oro_8", "Aadaa Oromoo (Culture)", "Traditional Dress", "Wayyaa Aadaa", "የሀበሻ ቀሚስ", "Tra-di-tion-al Dress", "She wears an elegant white Oromo dress embroidered with colorful callee beads.", "Uffata aadaa Oromoo callee baredaadhaan faayame uffatti.", "በጥበብ ያጌጠ ባህላዊ ቀሚስ ለብሳለች።", "👗", true),
        VocabularyWord("oro_9", "Aadaa Oromoo (Culture)", "Hora Lake", "Haroo Horaa", "ሆራ ሀይቅ", "Ho-ra Layk", "Lake Hora in Bishoftu is calm, green, and surrounded by beautiful trees.", "Haroon Hora Harsadii Bishooftuutti argamu bishaan qulqulluudha.", "የሆራ ሐይቅ በቢሾፍቱ የሚገኝ ውብ ሐይቅ ነው።", "🌊", true),
        VocabularyWord("oro_10", "Aadaa Oromoo (Culture)", "Gadaa", "Sirna Gadaa", "የገዳ ሥርዓት", "Guh-daa", "The Gadaa system is an indigenous democratic socio-political heritage of the Oromo.", "Sirni Gadaa sirna dimokraasii dhalootaa isa addunyaan beekudha.", "የገዳ ሥርዓት ጥንታዊ የዲሞክራሲ ቅርስ ነው።", "⚖️", true),

        // Animals (Bineensota)
        VocabularyWord("an_1", "Animals (Bineensota)", "Lion", "Leenca", "አንበሳ", "Ly-on", "The lion is the brave king of the wilderness.", "Leenci mootii bineensota bosonaati.", "አንበሳ የዱር ንጉሥ ነው።", "🦁"),
        VocabularyWord("an_2", "Animals (Bineensota)", "Elephant", "Arba", "ዝሆን", "El-e-phant", "The elephant drinks fresh water with its long trunk.", "Arbi bishaan qulqulluu funyaaniin dhuga.", "ዝሆኑ በረዥም አፍንጫው ውሃ ይጠጣል።", "🐘"),
        VocabularyWord("an_3", "Animals (Bineensota)", "Horse", "Farda", "ፈረስ", "Hors", "The strong horse gallops across the green meadow.", "Fardi jabaan marga magariisa keessa fiiga.", "ብርቱው ፈረስ ሜዳ ላይ ይሮጣል።", "🐎"),
        VocabularyWord("an_4", "Animals (Bineensota)", "Cow", "Sa'a / Saawwa", "ላም", "Kow", "The gentle cow gives healthy sweet milk.", "Saani aannan baay'ee mi'aawaa kenniti.", "ጥሩዋ ላም ንጹህ ወተት ትሰጣለች።", "🐄"),
        VocabularyWord("an_5", "Animals (Bineensota)", "Cat", "Adurree", "ድመት", "Kat", "The playful cat catches a little ball.", "Adurreen kubbaa xinnoo wajjin taphatti.", "ድመቷ በትንሽ ኳስ ትጫወታለች።", "🐱"),
        VocabularyWord("an_6", "Animals (Bineensota)", "Dog", "Saree", "ውሻ", "Dog", "The loyal dog guards our home safely.", "Sareen mana keenya nagaan eegdi.", "ታማኙ ውሻ ቤታችንን ይጠብቃል።", "🐶"),

        // Food (Nyaata)
        VocabularyWord("fd_1", "Food (Nyaata)", "Bread", "Dabboo", "ዳቦ", "Bred", "We enjoy warm bread for breakfast.", "Ciree ganamaaf dabboo ho'aa nyaanna.", "ለጠዋት ቁርስ የሞቀ ዳቦ እንበላለን።", "🍞"),
        VocabularyWord("fd_2", "Food (Nyaata)", "Honey", "Damma", "ማር", "Hun-ee", "Golden honey is gathered from the beehive.", "Dammi dhandhama baay'ee mi'aawaa qaba.", "ጣፋጭ ማር ከንብ ቀፎ ይገኛል።", "🍯"),
        VocabularyWord("fd_3", "Food (Nyaata)", "Milk", "Aannan", "ወተት", "Milk", "Drink a glass of cold milk every morning.", "Ganama hundumaa aannan qulqulluu dhugi.", "አንድ ብርጭቆ ወተት ጠጡ።", "🥛"),
        VocabularyWord("fd_4", "Food (Nyaata)", "Apple", "Aappilii", "ፖም", "Ap-pul", "Red apples are sweet, crisp, and fresh.", "Aappiliin diimaan baay'ee mi'aawaadha.", "ቀይ ፖም ጣፋጭና ትኩስ ነው።", "🍎"),

        // School (Mana Barumsaa)
        VocabularyWord("sc_1", "School (Mana Barumsaa)", "Teacher", "Barsiisaa / Barsiistuu", "አስተማሪ", "Tee-cher", "Our teacher helps us learn English happily.", "Barsiistuun keenya Afaan Ingilizii nu barsiisti.", "አስተማሪያችን እንግሊዝኛ ያስተምረናል።", "👩‍🏫"),
        VocabularyWord("sc_2", "School (Mana Barumsaa)", "Book", "Kitaaba", "መጽሐፍ", "Book", "I open my story book and read new words.", "Kitaaba koo baneen jechoota haaraa dubbisa.", "የተረት መጽሐፌን ከፍቼ አነባለሁ።", "📖"),
        VocabularyWord("sc_3", "School (Mana Barumsaa)", "Pencil", "Qobdoo / Irsaasii", "እርሳስ", "Pen-sil", "Write your English exercises with a pencil.", "Shaakala kee qobdoodhaan barreessi.", "እርሳስህን በጥንቃቄ ቅረጽ።", "✏️"),
        VocabularyWord("sc_4", "School (Mana Barumsaa)", "Classroom", "Kutaa Barumsaa", "የመማሪያ ክፍል", "Klass-room", "Our classroom is sunny, clean, and cheerful.", "Kutaan keenya ifaafi qulqulluudha.", "ክፍላችን ብሩህ እና ንጹህ ነው።", "🏫"),

        // Family (Maatii)
        VocabularyWord("fm_1", "Family (Maatii)", "Mother", "Haadha / Aayyoo", "እናት", "Muth-er", "My mother hugs me with warm love.", "Aayyoon koo jaalala guddaadhaan na hammatti.", "እናቴ በፍቅር ታቅፈኛለች።", "👩"),
        VocabularyWord("fm_2", "Family (Maatii)", "Father", "Abbaa", "አባት", "Fah-ther", "My father tells us wonderful bedtime stories.", "Abbaan koo seenaa baredaa nuu hima.", "አባቴ ድንቅ ተረቶችን ይነግረናል።", "👨"),
        VocabularyWord("fm_3", "Family (Maatii)", "Brother", "Obboleessa", "ወንድም", "Bruth-er", "My brother plays football with me in the field.", "Obboleessi koo dirree irratti kubbaa taphata.", "ወንድሜ ከእኔ ጋር ኳስ ይጫወታል።", "👦"),
        VocabularyWord("fm_4", "Family (Maatii)", "Sister", "Obboleettii", "እህት", "Sis-ter", "My sister sings lovely songs with me.", "Obboleettiin koo faaruu wajjin sirbiti.", "እህቴ ከእኔ ጋር ትዘምራለች።", "👧"),

        // Colors (Halluuwwan)
        VocabularyWord("cl_1", "Colors (Halluuwwan)", "Green", "Magariisa", "አረንጓዴ", "Green", "Green is the color of fresh grass and spring.", "Magariisi halluu margaafi biqiltootaati.", "አረንጓዴ የለምለም ሳር ቀለም ነው።", "🟩"),
        VocabularyWord("cl_2", "Colors (Halluuwwan)", "Yellow", "Keelloo", "ቢጫ", "Yel-low", "Yellow is the bright color of the Adey Abeba flower.", "Keelloon halluu abaaboo Adeey Ababaati.", "ቢጫ የአደይ አበባ ቀለም ነው።", "🟨"),
        VocabularyWord("cl_3", "Colors (Halluuwwan)", "Red", "Diimaa", "ቀይ", "Red", "Red is a bold and warm color.", "Diimaan halluu ho'aafi bareedaadha.", "ቀይ ደማቅ ቀለም ነው።", "🟥"),
        VocabularyWord("cl_4", "Colors (Halluuwwan)", "Blue", "Cuquliisa / Samii", "ሰማያዊ", "Bloo", "The morning sky above Finfinne is clear blue.", "Samiin ganamaa cuquliisa qulqulluudha.", "የጠዋቱ ሰማይ ሰማያዊ ነው።", "🟦"),

        // Body Parts (Qaama)
        VocabularyWord("bp_1", "Body Parts (Qaama)", "Eyes", "Ija", "ዓይኖች", "Ize", "We look at books with our eyes.", "Ija keenyaan kitaaba ilaalla.", "በዓይኖቻችን መጽሐፍትን እናያለን።", "👀"),
        VocabularyWord("bp_2", "Body Parts (Qaama)", "Ears", "Gurra", "ጆሮዎች", "Eers", "We listen to the teacher with our ears.", "Gurra keenyaan barsiisaa dhaggeeffanna.", "በጆሮዎቻችን አስተማሪውን እናዳምጣለን።", "👂"),
        VocabularyWord("bp_3", "Body Parts (Qaama)", "Hands", "Harka", "እጆች", "Handz", "Clap your hands with joy!", "Harka keessan gamachuudhaan walitti rukutaa!", "እጆቻችሁን በደስታ አጨብጭቡ!", "👏"),
        VocabularyWord("bp_4", "Body Parts (Qaama)", "Smile", "Seeqa / Kolfa", "ፈገግታ", "Smyle", "Give everyone a warm friendly smile.", "Namoota hundumaaf seeqa bareedaa kennaa.", "ትልቅ የፈገግታ ስጦታ ስጡ።", "😊")
    )

    val pronunciationChallenges = listOf(
        PronunciationChallenge(
            id = "th_dental_fricatives",
            title = "Dental Fricatives: TH Sounds (/θ/ & /ð/)",
            oromoTitle = "Sagalee 'TH' (Arraba Ilkaan Jala)",
            amharicTitle = "የ 'TH' ድምፅ ልምምድ (/θ/ እና /ð/)",
            contrastExplanation = "Afaan Oromoo lacks dental fricatives; children tend to substitute /t/ or /d/. In English, /θ/ (unvoiced) and /ð/ (voiced) are produced by resting the tongue tip gently between the front teeth.",
            oromoTip = "Arraba kee ilkaan kee jala kaa'i. Akka 'Dis' ykn 'Tis' hin jenne, qilleensa qorraa ilkaan gidduun baasiitii 'This' fi 'Three' jedhi!",
            amharicTip = "ምላስዎን ከጥርሶችዎ መሀል ትንሽ አውጥተው ይያዙት። 'This' እና 'Three' ን በትክክል ይለማመዱ።",
            mouthShapeGuidance = "👅 Arraba kee ilkaan kee jala kaa'i! Blow soft air for 'Three' (/θ/), vibrate vocal cords for 'This' (/ð/)!",
            targetWords = listOf(
                "Three" to "Tree",
                "This" to "Dis",
                "Think" to "Tink",
                "Thumb" to "Dumb",
                "That" to "Dat",
                "Thank" to "Tank"
            )
        ),
        PronunciationChallenge(
            id = "labiodental_v_f_b",
            title = "Labiodental Distinction: V vs F vs B",
            oromoTitle = "Sagaleewwan 'V' vs 'F' fi 'B'",
            amharicTitle = "የ 'V' እና 'F' / 'B' ልዩነት",
            contrastExplanation = "Practice labiodental friction (/v/) vs bilabial stops (/b/). For /v/, rest top front teeth against the lower lip and vibrate.",
            oromoTip = "Ilkaan kee gubbaa hidhii kee gadii irra kaa'iitii kokkeen akka hollatu godhi: /v/. Akka 'Ban' hin ta'in 'Van' jedhi!",
            amharicTip = "'B' በሁለት ከንፈር ሲዘጋ፤ 'V' ጥርስ ከንፈር ነክቶ ድምፁ ሲንዘረዘር ይወጣል።",
            mouthShapeGuidance = "🦷 Top teeth on lower lip -> Vibrate motor for /v/ (Van, Vest). Pop two lips together for /b/ (Ban, Best)!",
            targetWords = listOf(
                "Van" to "Ban",
                "Vest" to "Best",
                "Very" to "Berry",
                "Fan" to "Pan",
                "Vine" to "Wine"
            )
        ),
        PronunciationChallenge(
            id = "voiceless_voiced_p_b",
            title = "Voiceless vs Voiced Stops: P vs B",
            oromoTitle = "Sagalee 'P' (Laafaa) fi 'B'",
            amharicTitle = "የ 'P' እና 'B' ልዩነት (Pat vs Bat)",
            contrastExplanation = "Differentiate unvoiced /p/ and voiced /b/. For /p/, press lips tight and release a sudden burst of puffing air without vocal cord vibration.",
            oromoTip = "Waraqaa afaan kee dura qabiitii 'Pat' jedhi; waraqichi dhoohiinsa qilleensaatiin socho'uu qaba. 'Bat' wajjin hin makiin!",
            amharicTip = "'P' ንፁህ የትንፋሽ ፍንዳታ ነው (Pat)፤ 'B' ደግሞ የድምፅ ገመድ ይንቀጠቀጣል (Bat)።",
            mouthShapeGuidance = "👄 Press both lips tight -> Burst air without voice for /p/ ('Pat', 'Pen')! Buzz voice for /b/ ('Bat', 'Ben')!",
            targetWords = listOf(
                "Pat" to "Bat",
                "Pen" to "Ben",
                "Pig" to "Big",
                "Pin" to "Bin",
                "Pan" to "Ban",
                "Pill" to "Bill"
            )
        ),
        PronunciationChallenge(
            id = "ejective_softening",
            title = "Ejective Consonant Softening (k, p, t)",
            oromoTitle = "Qubee Laaffisuu (k, p, t Laafaa)",
            amharicTitle = "የፈንጂ ድምጾች ማለስለስ (k, p, t)",
            contrastExplanation = "Train children to soften voiceless stops (/k/, /p/, /t/) so they do not apply Afaan Oromoo ejective emphasis (k', p', t'). English voiceless stops use gentle aspiration rather than throat compression.",
            oromoTip = "Qubeelee 'k', 'p', 't' akka Afaan Oromoo dhooftee (k', p', t') osoo hin taane, laaffisii qilleensa qulqulluun dubbadhu. Fkn: 'Clean', 'Table', 'Pen'.",
            amharicTip = "የጉሮሮ ግፊት ሳያደርጉ ድምጾቹን በቀስታ ያውጡ።",
            mouthShapeGuidance = "🌬️ Relax your throat and vocal tract! Gently release air for 'Clean', 'Water', and 'Table' without ejective pops.",
            targetWords = listOf(
                "Clean" to "Kleen",
                "Table" to "Teibul",
                "Tea" to "Tee",
                "King" to "K-ing",
                "Pack" to "P-ak"
            )
        ),
        PronunciationChallenge(
            id = "word_stress_svo_sov",
            title = "Contrastive Word Order & Pronouns (SVO vs SOV)",
            oromoTitle = "Sirna Himaa: SVO (Ingiliffa) vs SOV (Oromoo)",
            amharicTitle = "የቃላት ቅደም ተከተል (SVO vs SOV) እና ተውላጠ ስሞች",
            contrastExplanation = "Address Subject-Object-Verb (SOV) in Afaan Oromoo ('Inni kitaaba dubbisa') vs Subject-Verb-Object (SVO) in English ('He reads a book'). Highlight pronoun mapping (He/She/They vs Inni/Ishee/Isaan).",
            oromoTip = "Afaan Oromoo keessatti gochimni dhuma irratti dhufa ('Inni kitaaba dubbisa'). Ingiliffa keessatti garuu gochima gidduu gala: 'He reads a book'. Inni = He, Ishee = She, Isaan = They!",
            amharicTip = "በእንግሊዝኛ ግሱ በመሀል ይገባል፡ He reads a book. He = እርሱ, She = እርሷ, They = እነርሱ።",
            mouthShapeGuidance = "🧠 SVO Formula: [Subject: Who?] + [Verb: Does what?] + [Object: What?]. Example: 'She drinks milk.'",
            targetWords = listOf(
                "He reads a book" to "Inni kitaaba dubbisa",
                "She drinks warm milk" to "Isheen aannan dhugdi",
                "They play together" to "Isaan waliin taphatu",
                "We walk to school" to "Nuyi mana barumsaa deemna"
            )
        )
    )

    val stories = listOf(
        ReadingStory(
            id = "story_irreecha",
            title = "Irreecha Celebration at Lake Hora",
            oromoTitle = "Ayyaana Irreechaa Hora Harsadii",
            amharicTitle = "የኢሬቻ በዓል በሆራ ሀይቅ",
            coverEmoji = "🌼",
            difficulty = "Beginner",
            paragraphs = listOf(
                StoryParagraph(
                    1,
                    "Today is the peaceful Sunday of Irreecha. Caaltuu and her brother Gadaa wake up early with big smiles.",
                    "Har'a Dilbata nagaa Ayyaana Irreechaati. Caaltuufi obboleessi ishee Gadaani gammachuudhaan ganama ka'an.",
                    "ዛሬ የኢሬቻ በዓል ቀን ነው። ጫልቱና ወንድሟ ገዳ በደስታ ማለዳ ተነሱ።",
                    listOf("Sunday", "Irreecha", "smiles", "brother")
                ),
                StoryParagraph(
                    2,
                    "They wear beautiful white cultural clothes with colorful callee beads. They hold fresh green coqorsa grass in their hands as a sign of peace and thanksgiving.",
                    "Uffata aadaa adii callee baredaadhaan faayame uffatan. Mallattoo nagaafi galataatiif marga coqorsaa magariisa harka isaaniitti qabatan.",
                    "በጥበብ ያሸበረቀ ባህላዊ ልብስ ለብሰው በእጃቸው ለምለም ሳር ይዘዋል።",
                    listOf("white", "clothes", "green", "grass", "peace")
                ),
                StoryParagraph(
                    3,
                    "At Lake Hora Harsadii, thousands of smiling people gather under the tall trees to sing songs of harmony and gratitude. 'Happy Irreecha to everyone!' cheers Caaltuu happily.",
                    "Haroo Hora Harsadii biratti, namoonni kumaatama baay'atan mukeen baredoo jalatti galataaf walitti qabaman. 'Baga Ayyaana Irreechaa geessan!' jettee Caaltuun sagalee ol-kaaste.",
                    "በሆራ ሐርሰዲ ሀይቅ ሺዎች ተሰብስበው የምስጋና ዝማሬ ዘመሩ።",
                    listOf("lake", "people", "trees", "gratitude", "happily")
                )
            ),
            questions = listOf(
                StoryQuestion(
                    id = "q1",
                    questionEnglish = "What green plant do Caaltuu and Gadaa hold in their hands?",
                    questionOromo = "Caaltuufi Gadaani harka isaaniitti biqiltuu magariisa kam qabatan?",
                    questionAmharic = "ጫልቱና ገዳ በእጃቸው ምን ዓይነት ለምለም ተክል ያዙ?",
                    options = listOf("Fresh coqorsa grass", "Red flowers", "Dry leaves", "Pine needles"),
                    correctIndex = 0
                ),
                StoryQuestion(
                    id = "q2",
                    questionEnglish = "At which beautiful lake is the Irreecha gathering celebrated?",
                    questionOromo = "Ayyaanni Irreechaa kun haroo kam biratti kabajama?",
                    questionAmharic = "በዓሉ የተከበረው በየትኛው ሐይቅ አጠገብ ነው?",
                    options = listOf("Lake Victoria", "Lake Hora Harsadii", "Lake Tana", "Red Sea"),
                    correctIndex = 1
                )
            )
        ),
        ReadingStory(
            id = "story_finfinne",
            title = "A Day in Finfinne",
            oromoTitle = "Guyyaa Tokko Finfinnee Keessatti",
            amharicTitle = "አንድ ቀን በአዲስ አበባ",
            coverEmoji = "🏙️",
            difficulty = "Beginner",
            paragraphs = listOf(
                StoryParagraph(
                    1,
                    "Today is a sunny morning in Finfinne. The cool mountain breeze sweeps across the green eucalyptus trees of Mount Entoto.",
                    "Har'a ganama iftuu Finfinnee keessatti. Qilleensi qabbanaawaa tulluu Entottoo mukeen baargamoo magariisa keessa bubbisa.",
                    "ዛሬ አዲስ አበባ ውስጥ ሞቅ ያለ የጠዋት ፀሐይ በእንጦጦ ዛፎች ላይ ታበራለች።",
                    listOf("sunny", "morning", "trees", "mountain")
                ),
                StoryParagraph(
                    2,
                    "Caaltuu and Gadaa step inside the modern light rail train. Through the clean glass window, they watch the bustling city markets and laughing school children.",
                    "Caaltuufi Gadaani baabura ammayyaa keessa seenan. Fulaa foddaa qulqulluu irraan gabaa magaalaafi ijoollee mana barumsaa kolfan daawwatan.",
                    "ጫልቱና ገዳ በባቡሩ ተሳፍረው በመስኮት ከተማዋን ተመለከቱ።",
                    listOf("train", "window", "city", "children")
                ),
                StoryParagraph(
                    3,
                    "They arrive at the great Unity Park to see colorful birds, historic halls, and green gardens. Learning new English words while exploring is pure joy!",
                    "Paarkii Tokkummaa gahanii simbiroota babbareedoo, gamoowwan seenaafi iddoo biqiltootaa daawwatan. Bakka daawwatanitti jechoota Ingilizii haaraa barachuun gammachuu guddaadha!",
                    "ወደ አንድነት ፓርክ ሄደው አዳዲስ የእንግሊዝኛ ቃላትን ተማሩ።",
                    listOf("park", "birds", "gardens", "English", "joy")
                )
            ),
            questions = listOf(
                StoryQuestion(
                    id = "q1",
                    questionEnglish = "What city are Caaltuu and Gadaa visiting in this story?",
                    questionOromo = "Seenaa kana keessatti Caaltuufi Gadaani magaalaa kam daawwatu?",
                    questionAmharic = "በዚህ ታሪክ ውስጥ የትኛውን ከተማ ነው የጎበኙት?",
                    options = listOf("Finfinne", "Nairobi", "Cairo", "London"),
                    correctIndex = 0
                ),
                StoryQuestion(
                    id = "q2",
                    questionEnglish = "How do they travel across the city?",
                    questionOromo = "Magaalattii keessa akkamiin imalan?",
                    questionAmharic = "በከተማዋ ውስጥ በምን ተጓዙ?",
                    options = listOf("On a horse", "By light rail train", "In a boat", "On an airplane"),
                    correctIndex = 1
                )
            )
        ),
        ReadingStory(
            id = "story_lion_mouse",
            title = "The Brave Lion and the Little Mouse",
            oromoTitle = "Leenca Gootaafi Hantuuta Xinnoo",
            amharicTitle = "አንበሳው እና ትንሿ አይጥ",
            coverEmoji = "🦁",
            difficulty = "Beginner",
            paragraphs = listOf(
                StoryParagraph(
                    1,
                    "A mighty lion was sleeping peacefully under a big acacia tree. A tiny mouse accidentally ran across his soft golden paw.",
                    "Leenci jabaan muka laftoo guddaa jalatti nagaan rafaa ture. Hantuunni xinnoon utuu hin beekin faana isaa irra fiigde.",
                    "አንድ ኃያል አንበሳ በዛፍ ጥላ ስር ተኝቶ ሳለ አንዲት አይጥ በእግሩ ላይ ሮጠችበት።",
                    listOf("lion", "sleeping", "tree", "mouse", "paw")
                ),
                StoryParagraph(
                    2,
                    "The lion woke up and trapped the mouse with a loud roar! 'Please spare my life,' squeaked the mouse. 'One day I might help you!' The lion smiled at the little mouse and let her go free.",
                    "Leenci aariidhaan dammaqee aade! 'Mee lubbuu koo naaf maari,' jettee hantuunni kadhatte. 'Guyyaa tokko si gargaaruu danda'a!' Leencis seeqee gad-dhiise.",
                    "አንበሳው በድምፅ ነቃ። አይጧ 'አንድ ቀን እረዳሃለሁ' ስላለችው ለቀቃት።",
                    listOf("woke", "roar", "help", "smiled", "free")
                ),
                StoryParagraph(
                    3,
                    "A week later, the lion was caught in a hunter's strong net. The little mouse heard his roar, ran quickly, and chewed the thick ropes with her sharp teeth until the lion was free!",
                    "Torbee tokko booda, leenci kiyyoo adamsitootaa keessatti qabame. Hantuunni sagalee isaa dhageessee, fiigdee dhuftee ilkaan isheetiin funyoo ciruudhaan leenca bilisa baaste!",
                    "ከቀናት በኋላ አንበሳው በመረብ ተያዘ፤ አይጧ ገመዱን በጥርስ ቆርጣ ነፃ አወጣችው!",
                    listOf("hunter", "net", "chewed", "teeth", "free")
                )
            ),
            questions = listOf(
                StoryQuestion(
                    id = "q1",
                    questionEnglish = "How did the tiny mouse rescue the mighty lion?",
                    questionOromo = "Hantuunni xinnoon leenca jabaa sana akkamiin gargaarte?",
                    questionAmharic = "ትንሿ አይጥ አንበሳውን እንዴት አዳነችው?",
                    options = listOf("She brought food", "She chewed the rope net", "She called an elephant", "She roared"),
                    correctIndex = 1
                )
            )
        )
    )

    val conversationScenarios = listOf(
        ConversationScenario(
            id = "conv_school",
            title = "First Day at School (Mana Barumsaatti)",
            oromoTitle = "Guyyaa Jalqabaa Mana Barumsaatti",
            amharicTitle = "በትምህርት ቤት የመጀመሪያ ቀን",
            location = "School Hallway & Classroom",
            bannerEmoji = "🎒",
            steps = listOf(
                ConversationStep(
                    speaker = "Teacher Caaltuu",
                    speechEnglish = "Good morning everyone! Welcome to our English class. What is your name?",
                    speechOromo = "Akkam bultan hundi keessan! Baga nagaan dhuftan. Maqaan kee eenyu?",
                    speechAmharic = "እንደምን አደራችሁ! እንኳን ደህና መጣችሁ። ስምህ ማን ነው?",
                    childPromptEnglish = "Introduce yourself politely in English.",
                    childPromptOromo = "Afaan Ingiliziitiin kabajaan maqaa kee himi.",
                    childPromptAmharic = "ስምዎን በእንግሊዝኛ በትህትና ይንገሩ።",
                    responseOptions = listOf(
                        "Good morning teacher! My name is Gadaa.",
                        "I am eating bread right now.",
                        "No English today please.",
                        "Goodbye see you tomorrow."
                    ),
                    correctOptionIndex = 0
                ),
                ConversationStep(
                    speaker = "Teacher Caaltuu",
                    speechEnglish = "Nice to meet you Gadaa! How old are you?",
                    speechOromo = "Sana beekuu kootti baay'een gammade Gadaa! Umuriin kee waggaa meeqa?",
                    speechAmharic = "ስለተዋወቅን ደስ ብሎኛል ገዳ! ዕድሜህ ስንት ነው?",
                    childPromptEnglish = "Tell the teacher your age in English.",
                    childPromptOromo = "Umurii kee barsiistuu keetti himi.",
                    childPromptAmharic = "ዕድሜዎን ይናገሩ።",
                    responseOptions = listOf(
                        "I have a red bicycle.",
                        "I am eight years old.",
                        "The dog is brown.",
                        "I live in a tall building."
                    ),
                    correctOptionIndex = 1
                ),
                ConversationStep(
                    speaker = "Teacher Caaltuu",
                    speechEnglish = "Wonderful! Take your seat next to Bontu and open your English book.",
                    speechOromo = "Baay'ee gaariidha! Boontuu bira taa'iitii kitaaba kee bani.",
                    speechAmharic = "በጣም ጥሩ! ተቀመጥና መጽሐፍህን ክፈት።",
                    childPromptEnglish = "Say thank you politely in English.",
                    childPromptOromo = "Afaan Ingiliziitiin 'Galatoomaa' jedhii deebisi.",
                    childPromptAmharic = "አመሰግናለሁ በሉ።",
                    responseOptions = listOf(
                        "Thank you very much, teacher!",
                        "I lost my shoes yesterday.",
                        "No, I want to sleep.",
                        "Look at the soccer ball."
                    ),
                    correctOptionIndex = 0
                )
            )
        ),
        ConversationScenario(
            id = "conv_gabaa",
            title = "Shopping at Gabaa Market",
            oromoTitle = "Gabaa Keessatti Meeshaa Bituu",
            amharicTitle = "በገበያ ውስጥ ግብይት",
            location = "Gabaa Fruit & Vegetables Stalls",
            bannerEmoji = "🛍️",
            steps = listOf(
                ConversationStep(
                    speaker = "Fruit Seller",
                    speechEnglish = "Hello young friend! Can I help you find sweet fresh fruits today?",
                    speechOromo = "Akkam jirtu hiriyaa koo! Har'a fuduraa mi'aawaa barbaaddaa?",
                    speechAmharic = "ሰላም ወጣት ጓደኛዬ! ዛሬ ትኩስ ፍራፍሬ ልርዳህ?",
                    childPromptEnglish = "Ask for red apples politely.",
                    childPromptOromo = "Aappilii diimaa barbaaduu kee gaafadhu.",
                    childPromptAmharic = "ቀይ ፖም እንዳላቸው ይጠይቁ።",
                    responseOptions = listOf(
                        "Yes please, do you have sweet red apples?",
                        "I am riding a big horse.",
                        "The clouds are dark.",
                        "Where is the bus station?"
                    ),
                    correctOptionIndex = 0
                ),
                ConversationStep(
                    speaker = "Fruit Seller",
                    speechEnglish = "Yes, these apples from the highlands are fresh! How many would you like?",
                    speechOromo = "Eeyyee, aappiliin kun baay'ee qulqulluudha! Meeqan siif laadha?",
                    speechAmharic = "አዎ፣ በጣም ትኩስ ናቸው! ስንት ትፈልጋለህ?",
                    childPromptEnglish = "Tell the seller you want four apples.",
                    childPromptOromo = "Aappilii afur akka barbaaddu himi.",
                    childPromptAmharic = "አራት ፖም እንደምትፈልግ ንገረው።",
                    responseOptions = listOf(
                        "I would like four sweet apples, please.",
                        "My shoes are black.",
                        "I have one sister.",
                        "Yesterday was Wednesday."
                    ),
                    correctOptionIndex = 0
                ),
                ConversationStep(
                    speaker = "Fruit Seller",
                    speechEnglish = "Here are four red apples. That will be forty birr, please.",
                    speechOromo = "Kunoo aappilii afur. Qarshii afurtama ta'a.",
                    speechAmharic = "እነሆ አራት ፖሞች። አርባ ብር ይሆናል።",
                    childPromptEnglish = "Hand over the money and say thank you.",
                    childPromptOromo = "Qarshicha laadhuutii galatoomi jedhi.",
                    childPromptAmharic = "ገንዘቡን ሰጥተው ያመስግኑ።",
                    responseOptions = listOf(
                        "Here is the money. Thank you very much!",
                        "I don't like reading homework.",
                        "Look at that flying bird.",
                        "My name is not forty."
                    ),
                    correctOptionIndex = 0
                )
            )
        ),
        ConversationScenario(
            id = "conv_buna_ceremony",
            title = "Buna Ceremony with Grandmother",
            oromoTitle = "Sirna Buna Qalaa Aayyoo Wajjin",
            amharicTitle = "የባህል ቡና ሥነ ሥርዓት ከአያት ጋር",
            location = "Living Room with Family",
            bannerEmoji = "☕",
            steps = listOf(
                ConversationStep(
                    speaker = "Grandmother (Aayyoo)",
                    speechEnglish = "Akkam jirtu my dear grandchild! Welcome to our warm home.",
                    speechOromo = "Akkam jirtu ilmoo koo jaallatamtuu! Baga nagaan dhufte.",
                    speechAmharic = "እንኳን ደህና መጣህ የልጅ ልጄ!",
                    childPromptEnglish = "Greet grandmother with respect and love.",
                    childPromptOromo = "Aayyoo kee kabajaafi jaalalaan nagaa gaafadhu.",
                    childPromptAmharic = "አያትዎን በአክብሮት ሰላም ይበሉ።",
                    responseOptions = listOf(
                        "Good afternoon Grandmother! It is wonderful to see you.",
                        "I forgot where I put my ball.",
                        "Can I go out in the rain?",
                        "The airplane is noisy."
                    ),
                    correctOptionIndex = 0
                ),
                ConversationStep(
                    speaker = "Grandmother (Aayyoo)",
                    speechEnglish = "The coffee smells wonderful. Would you like a warm bowl of popcorn?",
                    speechOromo = "Buni aadaa qalamaa jira. Fandishaa ho'aa nyaachuu barbaaddaa?",
                    speechAmharic = "ቡናው ይሸታል፤ ፈንዲሻ ትፈልጋለህ?",
                    childPromptEnglish = "Accept the hospitality with politeness.",
                    childPromptOromo = "Afaan Ingiliziitiin kabajaan 'Eeyyee' jedhii fudhadhu.",
                    childPromptAmharic = "በትህትና እሺ በሉ።",
                    responseOptions = listOf(
                        "Yes please Grandmother, the popcorn smells so delicious!",
                        "I only eat ice cream in the rain.",
                        "Turn on the radio loudly.",
                        "Monkeys live in tall trees."
                    ),
                    correctOptionIndex = 0
                ),
                ConversationStep(
                    speaker = "Grandmother (Aayyoo)",
                    speechEnglish = "Here is your popcorn. May Waaqa bless your studies with wisdom!",
                    speechOromo = "Kunoo fandishaan kee. Waaqayyo beekumsaafi eebba siif haa kennu!",
                    speechAmharic = "እነሆ ፈንዲሻህ፤ ትምህርትህ በበረከት ይሞላ!",
                    childPromptEnglish = "Say amen and thank your grandmother.",
                    childPromptOromo = "Ameen jedhii galatoomi jedhiin.",
                    childPromptAmharic = "አሜን ብለው ያመስግኑ።",
                    responseOptions = listOf(
                        "Amen Grandmother! Thank you for your kind blessing.",
                        "No more talking now.",
                        "I am running to the field.",
                        "Where is my notebook?"
                    ),
                    correctOptionIndex = 0
                )
            )
        )
    )

    val sentenceTasks = listOf(
        SentenceTask("s1", "The cat is sleeping.", "Adurreen raftee jirti.", "ድመቷ ተኝታለች።", listOf("sleeping.", "The", "cat", "is"), "🐱"),
        SentenceTask("s2", "I love my family.", "Maatii koo baay'een jaalladha.", "ቤተሰቦቼን እወዳለሁ።", listOf("love", "family.", "my", "I"), "❤️"),
        SentenceTask("s3", "The boy kicks the ball.", "Gurbichi kubbaa dhiita.", "ልጁ ኳሱን ይመታል።", listOf("kicks", "The", "ball.", "boy", "the"), "⚽"),
        SentenceTask("s4", "We drink healthy fresh milk.", "Aannan qulqulluu dhugna.", "ንጹህ ወተት እንጠጣለን።", listOf("fresh", "drink", "milk.", "healthy", "We"), "🥛"),
        SentenceTask("s5", "She reads a good story.", "Seenaa baredaa dubbisti.", "ጥሩ ታሪክ ታነባለች።", listOf("story.", "reads", "She", "a", "good"), "📖"),
        SentenceTask("s6", "The sun shines over Oromia.", "Aduun ifa bareedaa kenniti.", "ፀሐይ በደማቁ ታበራለች።", listOf("over", "The", "shines", "sun", "Oromia."), "☀️")
    )

    val offlinePacks = listOf(
        OfflinePack("pack_beginner", "Beginner Starter Pack", "Qubee & Jechoota Bu'uuraa", "የመጀመሪያ ደረጃ ጥቅል", "Alphabet, Qubee Phonics, First 50 Words, Audio", 24, true, 50),
        OfflinePack("pack_intermediate", "Intermediate Story & Words", "Ooduu fi Seenaawwan Oromiyaa", "የመካከለኛ ደረጃ ታሪኮች", "Sentences, Irreecha Story, Gabaa & Finfinne Dialogues", 48, true, 80),
        OfflinePack("pack_advanced", "Advanced Speaking & Pronunciation", "Laabii Sagaleessuu fi Waliin Dubbii", "የላቀ የአነባበብ እና ውይይት ጥቅል", "P vs B/F Lab, V vs W/B Lab, TH Lab, Fluency Meter", 35, true, 45)
    )

    val sampleStudents = listOf(
        StudentRecord("s1", "Caaltuu Tolasaa", "Grade 3", 92, 88, 120, 6, "P vs B distinction", "Today"),
        StudentRecord("s2", "Gadaa Fayyisaa", "Grade 4", 95, 94, 150, 14, "TH voiced sound", "Today"),
        StudentRecord("s3", "Boontuu Dheeressaa", "Grade 2", 84, 80, 95, 4, "V vs W distinction", "Yesterday"),
        StudentRecord("s4", "Bilisummaa Hundeessaa", "Grade 3", 90, 89, 130, 9, "Vowel length (Ship vs Sheep)", "Today"),
        StudentRecord("s5", "Sabboonaa Girmaa", "Grade 4", 86, 82, 105, 3, "Reading fluency rate", "2 days ago")
    )

    val sampleAssignments = listOf(
        TeacherAssignment("as1", "Pronunciation Lab: P vs B & F", "Pronunciation", 85, "Friday", 24, 21),
        TeacherAssignment("as2", "Read 'Irreecha Celebration at Lake Hora'", "Story Reading", 80, "Monday", 24, 23),
        TeacherAssignment("as3", "Aadaa Oromoo Vocabulary Quiz", "Vocabulary", 90, "Next Wednesday", 24, 18)
    )

    val offlineModules: List<OfflineLessonModule> = listOf(
        OfflineLessonModule(
            lesson_id = "lesson_oromo_level1_foundations",
            target_level = "Level 1",
            topic = "Foundations & Sound Master (TH, P vs B)",
            learning_objective = "Master basic high-frequency English words and short 2-3 word phrases. Target the dental fricatives /θ/, /ð/ and voiceless /p/ vs voiced /b/.",
            offline_assets_required = OfflineAssetsRequired(
                local_audio_prompts = listOf("audio_intro_oromo_l1.mp3", "audio_this_is_pen.mp3", "audio_pat_the_cat.mp3"),
                local_images = listOf("img_mascot_caalaa.png", "img_pen_cat.png")
            ),
            afaan_oromoo_intro_script = "Akkam bultani ijoollee jaallatamoo! Barsiisaa Caalaa wajjin sagalee 'TH' fi 'P' akkasumas himoota gaggabaaboo shaakallaa. Arraba keessan ilkaan jala kaa'aa!",
            target_phoneme_focus = listOf("/θ/", "/ð/", "/p/", "/b/"),
            voice_exercises = listOf(
                VoiceExercise(
                    exercise_id = "l1_ex_01_th_sound",
                    exercise_type = "repeat_after_me",
                    app_character_audio_script = "This is my pen.",
                    afaan_oromoo_instruction_script = "Arraba kee ilkaan kee jala kaa'ii 'This' jedhi: 'This is my pen.'",
                    expected_speech_target = "This is my pen.",
                    asr_keywords_required = listOf("this", "pen"),
                    phonetic_guide_for_kids = "DH-is iz mai PEN",
                    trouble_phonemes = listOf(
                        TroublePhoneme("/ð/", "Arraba kee ilkaan kee jala kaa'i. 'Dis' ykn 'Tis' hin jedhin, kokkeen akka hollatu godhii 'This' jedhi!"),
                        TroublePhoneme("/p/", "Hidhii lamaan cufii qilleensa dhoosi: 'P-P-Pen'. Akka 'Ben' hin ta'in!")
                    )
                ),
                VoiceExercise(
                    exercise_id = "l1_ex_02_p_vs_b",
                    exercise_type = "repeat_after_me",
                    app_character_audio_script = "Pat the cat.",
                    afaan_oromoo_instruction_script = "Jechoota 'Pat' fi 'Bat' adda baasi. 'Pat' yommuu jettu qilleensi afaan kee keessaa bahuu qaba.",
                    expected_speech_target = "Pat the cat.",
                    asr_keywords_required = listOf("pat", "cat"),
                    phonetic_guide_for_kids = "P-AT the K-AT",
                    trouble_phonemes = listOf(
                        TroublePhoneme("/p/", "Waraqaa afaan dura qabiitii 'Pat' jedhi; waraqichi raafamuu qaba. Sagalee /b/ irraa adda baasi!")
                    )
                )
            )
        ),
        OfflineLessonModule(
            lesson_id = "lesson_oromo_level2_building_blocks",
            target_level = "Level 2",
            topic = "Daily Routines & Labiodental Sounds (V vs F vs B)",
            learning_objective = "Practice full sentence repetitions for daily routines and feelings while mastering labiodental fricatives /v/ and /f/ and softening ejective stops /k/ and /t/.",
            offline_assets_required = OfflineAssetsRequired(
                local_audio_prompts = listOf("audio_intro_oromo_l2.mp3", "audio_i_drive_a_van.mp3"),
                local_images = listOf("img_blue_van.png")
            ),
            afaan_oromoo_intro_script = "Baga gara Sadarkaa Lammaffaatti dhuftan! Har'a sochiilee guyyaa guyyaa fi sagalee 'V' fi 'F' hidhii gubbaafi ilkaan fayyadamuun baranna.",
            target_phoneme_focus = listOf("/v/", "/f/", "/b/", "/k/", "/t/"),
            voice_exercises = listOf(
                VoiceExercise(
                    exercise_id = "l2_ex_01_v_vs_b",
                    exercise_type = "repeat_after_me",
                    app_character_audio_script = "I drive a blue van.",
                    afaan_oromoo_instruction_script = "Ilkaan gubbaa hidhii jalaatti qabiitii 'Van' jedhi. Akka 'Ban' hin ta'in.",
                    expected_speech_target = "I drive a blue van.",
                    asr_keywords_required = listOf("drive", "van"),
                    phonetic_guide_for_kids = "Ai DRAIV a BLOO V-AN",
                    trouble_phonemes = listOf(
                        TroublePhoneme("/v/", "Ilkaan kee gubbaa hidhii kee gadii irra kaa'i: /v/. Afaan Oromoo keessatti /v/ waan hin jirreef akka /b/ hin jenne!")
                    )
                )
            )
        ),
        OfflineLessonModule(
            lesson_id = "lesson_oromo_level3_active_conversation",
            target_level = "Level 3",
            topic = "Active Conversations & Sentence Structure (SVO vs SOV)",
            learning_objective = "Practice interactive conversational responses and switch smoothly from Afaan Oromoo SOV to English SVO while accurately using pronouns He/She/They.",
            offline_assets_required = OfflineAssetsRequired(
                local_audio_prompts = listOf("audio_intro_oromo_l3.mp3", "audio_she_reads_a_book.mp3"),
                local_images = listOf("img_school_friends.png")
            ),
            afaan_oromoo_intro_script = "Sadarkaa 3ffaatti baga nagaan dhuftan! Afaan Oromoo keessatti gochimni dhuma irratti dhufa ('Inni kitaaba dubbisa'). Ingiliffa keessatti garuu gochimni gidduu gala: 'He reads a book'.",
            target_phoneme_focus = listOf("/ʃ/", "/ð/", "SVO_Structure"),
            voice_exercises = listOf(
                VoiceExercise(
                    exercise_id = "l3_ex_01_svo_pronouns",
                    exercise_type = "roleplay_response",
                    app_character_audio_script = "What is Caaltuu doing?",
                    afaan_oromoo_instruction_script = "Caaltuun maal gochaa jirti? Ingiliffaan 'Isheen kitaaba dubbisa' jechuuf: 'She reads a book' jedhii deebisi.",
                    expected_speech_target = "She reads a book.",
                    asr_keywords_required = listOf("she", "reads", "book"),
                    phonetic_guide_for_kids = "SHEE REEDZ a BUK",
                    trouble_phonemes = listOf(
                        TroublePhoneme("SVO_order", "Hubadhu: 'She a book reads' hin jedhinaa! Gochima (reads) gidduu kaa'i: 'She reads a book'!")
                    )
                )
            )
        ),
        OfflineLessonModule(
            lesson_id = "lesson_oromo_level4_natural_fluency",
            target_level = "Level 4",
            topic = "Natural Fluency & Story Narration",
            learning_objective = "Achieve spoken fluency with connected speech, clear intonation, and native-like flow.",
            offline_assets_required = OfflineAssetsRequired(
                local_audio_prompts = listOf("audio_intro_oromo_l4.mp3", "audio_sun_shines_lake.mp3"),
                local_images = listOf("img_lake_harsadi.png")
            ),
            afaan_oromoo_intro_script = "Sadarkaa 4ffaa - Qaxalee Fluency! Now speak in English with full sentences, clear rhythm, and natural speed.",
            target_phoneme_focus = listOf("Connected_Speech", "/ð/", "Intonation"),
            voice_exercises = listOf(
                VoiceExercise(
                    exercise_id = "l4_ex_01_lake_narration",
                    exercise_type = "picture_narration",
                    app_character_audio_script = "Describe the beautiful lake in Bishoftu.",
                    afaan_oromoo_instruction_script = "Fakkii ilaaliitii hima kanaan ibsi: 'The morning sun shines over the lake.'",
                    expected_speech_target = "The morning sun shines over the lake.",
                    asr_keywords_required = listOf("morning", "sun", "shines", "lake"),
                    phonetic_guide_for_kids = "The MOR-NING SUN SHAINZ OH-VER the LEIK",
                    trouble_phonemes = listOf(
                        TroublePhoneme("Connected_Speech", "Jechoota walitti hidhiitii sagalee bareedina qabuun dubbadhu: 'shines over the lake'.")
                    )
                )
            )
        )
    )

    val levelJourneys: List<LevelJourney> = listOf(
        LevelJourney(
            levelNumber = 1,
            title = "Part 1: Alphabet & Foundational Sounds",
            oromoTitle = "Kutaa 1: Qubee A hanga F fi Sagaleewwan Bu'uuraa",
            description = "Master letters A-F, short vowel sounds, first 6 everyday words, simple SVO sentences, and pass the Deep Exam to unlock Part 2.",
            oromoDescription = "Qubee A hanga F, sagalee /æ/ fi /b/, jechoota bu'uuraa fi hima gabaabaa baradhuutii Qormaata Gad-fagoon Kutaa 2ffaa bani.",
            targetLetters = alphabetList.take(6), // A, B, C, D, E, F
            targetPhonics = listOf(
                PhonicsSound(
                    id = "short_a",
                    category = "Sagalee Bu'uuraa",
                    pattern = "Short A /æ/",
                    pronunciationTip = "Open mouth wide like smiling and say /æ/ as in Apple.",
                    oromoComparison = "Afaan Oromoo keessatti 'A' akka 'Abbaa' ta'a; Afaan Ingilizii keessatti immoo afaan bal'isanii akka 'Apple' ykn 'Cat' jedhama.",
                    amharicComparison = "እንደ 'አፕል' ክፍት ያለ ድምፅ",
                    words = listOf("Apple", "Cat", "Hat", "Bat", "Map"),
                    sampleSentences = listOf("An apple is sweet.", "The cat sat on the mat.")
                ),
                PhonicsSound(
                    id = "stop_b_p",
                    category = "Sagalee Hidhii (Stops)",
                    pattern = "P vs B",
                    pronunciationTip = "P uses a quick puff of air without vocal cords. B vibrates your throat softly.",
                    oromoComparison = "Qubee 'P' yoo sagaleessitu qilleensa qofa hidhii lamaan baasi (ejective consonant hin godhinaa). 'B' immoo kokkee hollachiisa.",
                    amharicComparison = "በ 'ፕ' እና 'ብ' መካከል ያለ ልዩነት",
                    words = listOf("Ball", "Boy", "Pet", "Pen", "Pan"),
                    sampleSentences = listOf("The boy kicks the ball.", "I write with a pen.")
                )
            ),
            targetWords = listOf(
                VocabularyWord("w_cat", "Animals", "Cat", "Adurree", "ድመት", "/kæt/", "The cat is sleeping.", "Adurreen raftee jirti.", "ድመቷ ተኝታለች።", "🐱"),
                VocabularyWord("w_apple", "Food", "Apple", "Aappilii", "ፖም", "/ˈæp.əl/", "I like the red apple.", "Aappilii diimaa nan jaalladha.", "ቀዩን ፖም እወዳለሁ።", "🍎"),
                VocabularyWord("w_ball", "Toys", "Ball", "Kubbaa", "ኳስ", "/bɔːl/", "Kick the round ball.", "Kubbaa geengoo dhiiti.", "ኳሱን ምታው።", "⚽"),
                VocabularyWord("w_dog", "Animals", "Dog", "Saree", "ውሻ", "/dɒɡ/", "The dog is happy.", "Sareen gammadaadha.", "ውሻው ደስተኛ ነው።", "🐶"),
                VocabularyWord("w_fish", "Animals", "Fish", "Qurxummii", "ዓሳ", "/fɪʃ/", "The fish swims fast.", "Qurxummiin daaka.", "ዓሳው ይዋኛል።", "🐟"),
                VocabularyWord("w_boy", "People", "Boy", "Mucaa", "ወንድ ልጅ", "/bɔɪ/", "The boy runs to school.", "Mucaan mana barumsaa fiiga.", "ልጁ ወደ ትምህርት ቤት ይሮጣል።", "👦")
            ),
            targetSentences = listOf(
                SentenceTask("s_cat", "This is a cat.", "Kun adurreedha.", "ይህች ድመት ናት።", listOf("This", "is", "a", "cat."), "🐱"),
                SentenceTask("s_ball", "The boy has a ball.", "Mucichi kubbaa qaba.", "ልጁ ኳስ አለው።", listOf("The", "boy", "has", "a", "ball."), "⚽"),
                SentenceTask("s_apple", "I see a red apple.", "Aappilii diimaan arga.", "ቀይ ፖም አያለሁ።", listOf("I", "see", "a", "red", "apple."), "🍎"),
                SentenceTask("s_dog", "The dog runs fast.", "Sareen saffisaan fiiga.", "ውሻው በፍጥነት ይሮጣል።", listOf("The", "dog", "runs", "fast."), "🐶")
            ),
            deepExam = DeepExam(
                levelNumber = 1,
                title = "Part 1 Deep Level Exam",
                oromoTitle = "Qormaata Gad-fagoo Kutaa 1ffaa",
                passScore = 75,
                xpReward = 120,
                starsReward = 5,
                coinsReward = 25,
                questions = listOf(
                    ExamQuestion(
                        id = "l1_q1_letter",
                        stageCategory = LevelStage.LETTER,
                        questionText = "Which letter in English has the /k/ sound in 'Cat' (different from Qubee 'Caaltuu')?",
                        oromoInstruction = "Qubee Afaan Ingilizii keessatti sagalee /k/ akka 'Cat' kennee fi Qubee Oromoo 'Caaltuu' irraa adda ta'e filadhaa:",
                        options = listOf("Letter C", "Letter S", "Letter B", "Letter M"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Ingiliffaan 'C' baay'inaan akka /k/ ('Cat') dubbifama."
                    ),
                    ExamQuestion(
                        id = "l1_q2_sound",
                        stageCategory = LevelStage.SOUND,
                        questionText = "Which word starts with the short /æ/ sound?",
                        oromoInstruction = "Jechoonni armaan gadii keessaa kamtu sagalee gabaabaa /æ/ qaba?",
                        options = listOf("Apple", "Ball", "Dog", "Fish"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Sagaleen 'A' akka 'Apple' /æ/ dha."
                    ),
                    ExamQuestion(
                        id = "l1_q3_word_speech",
                        stageCategory = LevelStage.WORD,
                        questionText = "Pronunciation Test: Tap the mic and speak the word 'Cat' clearly!",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo foona tuquun jecha 'Cat' jedhii sagaleen dubbadhu!",
                        spokenTarget = "Cat",
                        asrKeywords = listOf("cat"),
                        tipAfaanOromoo = "Sagalee 'C' akka /k/ gootee 'Cat' jedhii dubbadhu."
                    ),
                    ExamQuestion(
                        id = "l1_q4_sentence_order",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Unscramble into correct English SVO order: 'This is a dog.'",
                        oromoInstruction = "Jechoota tartiiba sirriin qindeessi (Afaan Oromoo 'Kun sareedha'):",
                        scrambledWords = listOf("dog.", "is", "a", "This"),
                        spokenTarget = "This is a dog.",
                        tipAfaanOromoo = "Tartiiba Ingilizii SVO: This (Mata-duree) -> is (Gochima) -> a dog (Antoo)."
                    ),
                    ExamQuestion(
                        id = "l1_q5_sentence_speech",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Speaking Fluency Test: Tap mic and read: 'The boy has a ball.'",
                        oromoInstruction = "Qormaata Dubbii Guutuu: Maaykiroo fooniin hima guutuu dubbadhu: 'The boy has a ball.'",
                        spokenTarget = "The boy has a ball.",
                        asrKeywords = listOf("boy", "has", "ball"),
                        tipAfaanOromoo = "Saffisaan fi iftoominaan 'The boy has a ball' jedhii dubbadhu."
                    )
                )
            )
        ),
        LevelJourney(
            levelNumber = 2,
            title = "Part 2: Dental Fricatives & Everyday Life",
            oromoTitle = "Kutaa 2: Sagalee TH fi Jireenya Guyyaa Guyyaa",
            description = "Master letters G-L, the dental fricatives /θ/ & /ð/ (TH in 'Think' & 'Mother'), everyday words, and pass the Deep Exam to unlock Part 3.",
            oromoDescription = "Qubee G hanga L, sagalee TH (Think, Mother), jechoota maatii fi bishaanii baradhuutii Qormaata Gad-fagoon Kutaa 3ffaa bani.",
            targetLetters = alphabetList.subList(6, 12), // G, H, I, J, K, L
            targetPhonics = listOf(
                PhonicsSound(
                    id = "th_dental",
                    category = "Qubee Dachaa TH",
                    pattern = "TH /θ/ & /ð/",
                    pronunciationTip = "Gently put your tongue tip between teeth: 'Arraba kee ilkaan gidduu muraasa baasii qilleensa baasi'. Do not say /t/ or /d/!",
                    oromoComparison = "Afaan Oromoo keessatti sagaleen TH hin jiru. Kanaaf 'Think' yoo jettu 'Tink' hin jedhinaa; 'Mother' yoo jettu 'Moder' hin jedhinaa!",
                    amharicComparison = "ምላስ በጥርስ መሀል በማድረግ የሚወጣ ድምፅ",
                    words = listOf("Think", "Three", "This", "Mother", "Father"),
                    sampleSentences = listOf("This is my mother.", "I have three books.")
                )
            ),
            targetWords = listOf(
                VocabularyWord("w_mother", "Family", "Mother", "Haadha", "እናት", "/ˈmʌð.ər/", "I love my mother.", "Haadha koo nan jaalladha.", "እናቴን እወዳለሁ።", "👩"),
                VocabularyWord("w_father", "Family", "Father", "Abbaa", "አባት", "/ˈfɑː.ðər/", "My father is strong.", "Abbaan koo jabaadha.", "አባቴ ጠንካራ ነው።", "👨"),
                VocabularyWord("w_three", "Numbers", "Three", "Sadii", "ሦስት", "/θriː/", "I count three stars.", "Urjiiwwan sadiin lakaa'a.", "ሦስት ከዋክብትን እቆጥራለሁ።", "3️⃣"),
                VocabularyWord("w_water", "Daily", "Water", "Bishaan", "ውሃ", "/ˈwɔː.tər/", "Water is fresh and clean.", "Bishaan qulqulluudha.", "ውሃው ንጹህ ነው።", "💧"),
                VocabularyWord("w_milk", "Daily", "Milk", "Aannan", "ወተት", "/mɪlk/", "I drink sweet milk.", "Aannan mi'aawaan dhuga.", "ጣፋጭ ወተት እጠጣለሁ።", "🥛"),
                VocabularyWord("w_house", "Home", "House", "Mana", "ቤት", "/haʊs/", "Our house is warm.", "Manni keenya ho'aadha.", "ቤታችን ሞቅ ያለ ነው።", "🏠")
            ),
            targetSentences = listOf(
                SentenceTask("s_mother", "This is my mother.", "Kun haadha kooti.", "ይህች እናቴ ናት።", listOf("This", "is", "my", "mother."), "👩"),
                SentenceTask("s_water", "I drink clean water.", "Bishaan qulqulluu dhuga.", "ንጹህ ውሃ እጠጣለሁ።", listOf("I", "drink", "clean", "water."), "💧"),
                SentenceTask("s_three", "I see three birds.", "Simbirroo sadiin arga.", "ሦስት ወፎችን አያለሁ።", listOf("I", "see", "three", "birds."), "🐦"),
                SentenceTask("s_house", "Our house is big.", "Manni keenya guddaadha.", "ቤታችን ትልቅ ነው።", listOf("Our", "house", "is", "big."), "🏠")
            ),
            deepExam = DeepExam(
                levelNumber = 2,
                title = "Part 2 Deep Level Exam",
                oromoTitle = "Qormaata Gad-fagoo Kutaa 2ffaa",
                passScore = 75,
                xpReward = 130,
                starsReward = 5,
                coinsReward = 30,
                questions = listOf(
                    ExamQuestion(
                        id = "l2_q1_letter",
                        stageCategory = LevelStage.LETTER,
                        questionText = "Which letter in this group starts the word 'Goat'?",
                        oromoInstruction = "Qubeewwan kana keessaa kamtu jecha 'Goat' (Re'ee) jalqaba?",
                        options = listOf("Letter G", "Letter H", "Letter J", "Letter L"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Qubee 'G' akka 'Goat' dubbifama."
                    ),
                    ExamQuestion(
                        id = "l2_q2_sound",
                        stageCategory = LevelStage.SOUND,
                        questionText = "Physical Mouth Positioning: Where do you put your tongue when pronouncing 'TH' in 'Think'?",
                        oromoInstruction = "Boca Afaanii: Jecha 'Think' keessatti sagalee 'TH' baasuuf arraba kee eessa keessa?",
                        options = listOf("Between your front teeth (Arraba ilkaan gidduu)", "Back of your throat (Kokkee keessatti)", "Against your lower lip (Hidhii gadii)", "Touching your nose"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Arraba kee ilkaan kee jala/gidduu muraasa baasii qilleensa baasi."
                    ),
                    ExamQuestion(
                        id = "l2_q3_word_speech",
                        stageCategory = LevelStage.WORD,
                        questionText = "Pronunciation Test: Tap mic and say 'Mother' with a clear /ð/ sound!",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo foona tuquun 'Mother' jedhii sagaleen dubbadhu ('Moder' hin jedhinaa)!",
                        spokenTarget = "Mother",
                        asrKeywords = listOf("mother"),
                        tipAfaanOromoo = "Arraba ilkaan gidduu kaawwadhuutii 'Mother' jedhi."
                    ),
                    ExamQuestion(
                        id = "l2_q4_sentence_order",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Unscramble into English SVO order: 'I drink clean water.'",
                        oromoInstruction = "Afaan Oromoo 'Bishaan qulqulluu dhuga' gara SVO Ingiliffaatti qindeessi:",
                        scrambledWords = listOf("clean", "water.", "I", "drink"),
                        spokenTarget = "I drink clean water.",
                        tipAfaanOromoo = "Mata-duree (I) -> Gochima (drink) -> Antoo (clean water)."
                    ),
                    ExamQuestion(
                        id = "l2_q5_sentence_speech",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Speaking Fluency Test: Tap mic and read: 'This is my mother.'",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo fooniin 'This is my mother' jedhii dubbadhu!",
                        spokenTarget = "This is my mother.",
                        asrKeywords = listOf("this", "my", "mother"),
                        tipAfaanOromoo = "'This is my mother' jechuun sirriitti dubbadhu."
                    )
                )
            )
        ),
        LevelJourney(
            levelNumber = 3,
            title = "Part 3: Labiodental Sounds & Nature",
            oromoTitle = "Kutaa 3: Sagalee V vs B fi Uumama",
            description = "Master letters M-V, distinguish /v/ vs /f/ vs /b/, learn nature & transport words, and pass the Deep Exam to unlock Part 4.",
            oromoDescription = "Qubee M hanga V, sagalee V fi B addaan baasuu, jechoota uumamaa baradhuutii Qormaata Gad-fagoon Kutaa 4ffaa bani.",
            targetLetters = alphabetList.subList(12, 22), // M to V
            targetPhonics = listOf(
                PhonicsSound(
                    id = "v_vs_b",
                    category = "Labiodental Distinction",
                    pattern = "V /v/ vs B /b/",
                    pronunciationTip = "For /v/, touch upper teeth to lower lip: 'Van'. Do not close both lips like /b/!",
                    oromoComparison = "Xiyyeeffannaa: Ilkaan gubbaa hidhii gadii irra kaawwadhu. 'Van' jechuuf 'Ban' hin jedhinaa!",
                    amharicComparison = "በ 'ቭ' እና 'ብ' መካከል ያለ ልዩነት",
                    words = listOf("Van", "Voice", "Village", "Very", "Vine"),
                    sampleSentences = listOf("The van is fast.", "We live in a green village.")
                )
            ),
            targetWords = listOf(
                VocabularyWord("w_van", "Vehicles", "Van", "Makiinaa Vaan", "ቫን መኪና", "/væn/", "The van is fast.", "Vaanin saffisaadha.", "ቫኑ ፈጣን ነው።", "🚐"),
                VocabularyWord("w_village", "Nature", "Village", "Ganda", "መንደር", "/ˈvɪl.ɪdʒ/", "We love our green village.", "Ganda keenya nan jaallanna.", "መንደራችንን እንወዳለን።", "🏡"),
                VocabularyWord("w_rain", "Weather", "Rain", "Bokkaa", "ዝናብ", "/reɪn/", "Cool rain falls down.", "Bokkaan qabbanaawaan rooba.", "አሪፍ ዝናብ ይዘንባል።", "🌧️"),
                VocabularyWord("w_tree", "Nature", "Tree", "Muka", "ዛፍ", "/triː/", "The green tree gives shade.", "Muki magariisni gaaddisa kenna.", "ዛፉ ጥላ ይሰጣል።", "🌳"),
                VocabularyWord("w_sun", "Nature", "Sun", "Aduu", "ፀሐይ", "/sʌn/", "The sun shines warm.", "Aduun ho'itu ifti.", "ፀሐይ ሞቅ ባለ ሁኔታ ታበራለች።", "☀️"),
                VocabularyWord("w_pencil", "School", "Pencil", "Qobdoo / Irsaasii", "እርሳስ", "/ˈpen.səl/", "I draw with my pencil.", "Irsaasii koon fakkii kaasa.", "በእርሳሴ እስላለሁ።", "✏️")
            ),
            targetSentences = listOf(
                SentenceTask("s_van", "The van is fast.", "Vaanin saffisaadha.", "ቫኑ ፈጣን ነው።", listOf("The", "van", "is", "fast."), "🚐"),
                SentenceTask("s_village", "We live in a village.", "Nuti ganda keessa jiraanna.", "መንደር ውስጥ እንኖራለን።", listOf("We", "live", "in", "a", "village."), "🏡"),
                SentenceTask("s_rain", "Rain falls on green trees.", "Bokkaan muka magariisa irra rooba.", "ዝናብ በዛፎች ላይ ይዘንባል።", listOf("Rain", "falls", "on", "green", "trees."), "🌳"),
                SentenceTask("s_sun", "The sun shines bright.", "Aduun ifa bareedaa baati.", "ፀሐይ በደመቀ ሁኔታ ታበራለች።", listOf("The", "sun", "shines", "bright."), "☀️")
            ),
            deepExam = DeepExam(
                levelNumber = 3,
                title = "Part 3 Deep Level Exam",
                oromoTitle = "Qormaata Gad-fagoo Kutaa 3ffaa",
                passScore = 75,
                xpReward = 140,
                starsReward = 5,
                coinsReward = 35,
                questions = listOf(
                    ExamQuestion(
                        id = "l3_q1_letter",
                        stageCategory = LevelStage.LETTER,
                        questionText = "Which letter in English produces a voiceless puff of air in 'Pencil' without being a 'B'?",
                        oromoInstruction = "Qubee kamtu jecha 'Pencil' keessatti qilleensa qofa dhoosa (sagalee 'B' irraa adda)?",
                        options = listOf("Letter P", "Letter B", "Letter D", "Letter F"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Qubee 'P' hidhii cufuun qilleensa baasuun dubbifama."
                    ),
                    ExamQuestion(
                        id = "l3_q2_sound",
                        stageCategory = LevelStage.SOUND,
                        questionText = "To make the correct /v/ sound in 'Van', where do your top teeth go?",
                        oromoInstruction = "Sagalee /v/ ('Van') sirriitti baasuuf ilkaan gubbaa eessa kaa'uu qabda?",
                        options = listOf("On your lower lip (Hidhii gadii irra)", "Closing both lips (Hidhii lamaan cufuu)", "Behind upper teeth (Ilkaan gubbaa duuba)", "Against your cheek"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Ilkaan gubbaa hidhii gadii irra kaawwadhuutii 'Van' jedhi."
                    ),
                    ExamQuestion(
                        id = "l3_q3_word_speech",
                        stageCategory = LevelStage.WORD,
                        questionText = "Pronunciation Test: Tap mic and speak the word 'Van' clearly!",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo fooniin jecha 'Van' jedhii sagaleen dubbadhu ('Ban' akka hin jenne)!",
                        spokenTarget = "Van",
                        asrKeywords = listOf("van"),
                        tipAfaanOromoo = "Ilkaan gubbaa hidhii gadii irra kaawwattee 'Van' jedhi."
                    ),
                    ExamQuestion(
                        id = "l3_q4_sentence_order",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Unscramble into English SVO order: 'We live in a village.'",
                        oromoInstruction = "Afaan Oromoo 'Ganda keessa jiraanna' jechuuf jechoota qindeessi:",
                        scrambledWords = listOf("village.", "live", "We", "in", "a"),
                        spokenTarget = "We live in a village.",
                        tipAfaanOromoo = "We (Mata-duree) -> live in (Gochima) -> a village (Antoo)."
                    ),
                    ExamQuestion(
                        id = "l3_q5_sentence_speech",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Speaking Fluency Test: Tap mic and read: 'The van is fast.'",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo fooniin 'The van is fast' jedhii dubbadhu!",
                        spokenTarget = "The van is fast.",
                        asrKeywords = listOf("van", "fast"),
                        tipAfaanOromoo = "'The van is fast' jechuun iftoominaan dubbadhu."
                    )
                )
            )
        ),
        LevelJourney(
            levelNumber = 4,
            title = "Part 4: School, Actions & Conversational Fluency",
            oromoTitle = "Kutaa 4: Mana Barumsaa fi Dubbii Qaxalee",
            description = "Master letters W-Z, digraphs SH and CH, school action words, conversational sentences, and complete the Deep Exam to earn your Mastery Badge.",
            oromoDescription = "Qubee W hanga Z, sagalee SH fi CH, jechoota mana barumsaa baradhuutii Qormaata Gad-fagoon Badhaasa Qaxalummaa fudhadhu.",
            targetLetters = alphabetList.subList(22, 26), // W, X, Y, Z
            targetPhonics = listOf(
                PhonicsSound(
                    id = "sh_and_ch",
                    category = "Qubee Dachaa",
                    pattern = "SH /ʃ/ vs CH /tʃ/",
                    pronunciationTip = "SH is smooth 'shhh' (like Afaan Oromoo 'Shan'). CH starts with a sharp 'T' burst (like Afaan Oromoo 'Caaltuu').",
                    oromoComparison = "SH akka 'Shan' yoo ta'u, CH immoo akka 'Caaltuu' ykn 'Caffee' dhagahama.",
                    amharicComparison = "በ 'ሸ' እና 'ቸ' መካከል ያለ ልዩነት",
                    words = listOf("School", "Teacher", "Chair", "Shoes", "Ship"),
                    sampleSentences = listOf("My teacher helps me read.", "Sit on the chair.")
                )
            ),
            targetWords = listOf(
                VocabularyWord("w_school", "School", "School", "Mana Barumsaa", "ትምህርት ቤት", "/skuːl/", "I go to school happily.", "Mana barumsaa gammachuun deema.", "በትምህርት ቤት ደስተኛ ሆኜ እሄዳለሁ።", "🏫"),
                VocabularyWord("w_teacher", "School", "Teacher", "Barsiisaa", "መምህር", "/ˈtiː.tʃər/", "My teacher helps me learn.", "Barsiisaan koo na barsiisa.", "መምህሬ ያስተምረኛል።", "👩‍🏫"),
                VocabularyWord("w_friend", "People", "Friend", "Hiriyaa", "ጓደኛ", "/frend/", "We are good friends.", "Nuti hiriyoota gaariidha.", "እኛ ጥሩ ጓደኛሞች ነን።", "👫"),
                VocabularyWord("w_book", "School", "Book", "Kitaaba", "መጽሐፍ", "/bʊk/", "She reads a colorful book.", "Isheen kitaaba dubbisti.", "ቀለም ያለው መጽሐፍ ታነባለች።", "📖"),
                VocabularyWord("w_morning", "Time", "Morning", "Bariisaa / Ganama", "ጥዋት", "/ˈmɔː.nɪŋ/", "Good morning teacher!", "Akkam bulte barsiisaa!", "እንደምን አደሩ አስተማሪ!", "🌅"),
                VocabularyWord("w_zebra", "Animals", "Zebra", "Harree Diidaa", "የሜዳ አህያ", "/ˈzeb.rə/", "The zebra runs on the plain.", "Harreen diidaa dirree irra fiigdi.", "የሜዳ አህያ ሜዳ ላይ ትሮጣለች።", "🦓")
            ),
            targetSentences = listOf(
                SentenceTask("s_school", "I walk to school every morning.", "Ganama hunda mana barumsaa deema.", "ሁልጊዜ ጥዋት ወደ ትምህርት ቤት እሄዳለሁ።", listOf("I", "walk", "to", "school", "every", "morning."), "🏫"),
                SentenceTask("s_teacher", "My teacher helps me read.", "Barsiisaan koo dubbisuu na gargaara.", "መምህሬ እንዳነብ ይረዳኛል።", listOf("My", "teacher", "helps", "me", "read."), "👩‍🏫"),
                SentenceTask("s_friend", "We play with good friends.", "Hiriyoota gaarii wajjin taphanna.", "ከጥሩ ጓደኞች ጋር እንጫወታለን።", listOf("We", "play", "with", "good", "friends."), "👫"),
                SentenceTask("s_book", "She reads an English book.", "Kitaaba Ingiliffaa dubbisti.", "የእንግሊዝኛ መጽሐፍ ታነባለች።", listOf("She", "reads", "an", "English", "book."), "📖")
            ),
            deepExam = DeepExam(
                levelNumber = 4,
                title = "Part 4 Deep Fluency Exam",
                oromoTitle = "Qormaata Gad-fagoo Kutaa 4ffaa (Qaxalee)",
                passScore = 75,
                xpReward = 160,
                starsReward = 6,
                coinsReward = 50,
                questions = listOf(
                    ExamQuestion(
                        id = "l4_q1_letter",
                        stageCategory = LevelStage.LETTER,
                        questionText = "Which letter is the last letter of the alphabet and begins 'Zebra'?",
                        oromoInstruction = "Qubeen dhumaa kan jecha 'Zebra' (Harree diidaa) jalqabu kami?",
                        options = listOf("Letter Z", "Letter X", "Letter Y", "Letter W"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "Qubee 'Z' akka 'Zebra' ykn 'Zayitii' dubbifama."
                    ),
                    ExamQuestion(
                        id = "l4_q2_sound",
                        stageCategory = LevelStage.SOUND,
                        questionText = "Which English digraph sounds like 'Caaltuu' in Afaan Oromoo?",
                        oromoInstruction = "Qubee Dachaa Ingilizii kamtu akka 'Caaltuu' Afaan Oromoo dhagahama?",
                        options = listOf("CH as in Teacher / Chair", "SH as in Ship", "TH as in Three", "WH as in What"),
                        correctOptionIndex = 0,
                        tipAfaanOromoo = "CH akka 'Caaltuu'tti sagaleessama."
                    ),
                    ExamQuestion(
                        id = "l4_q3_word_speech",
                        stageCategory = LevelStage.WORD,
                        questionText = "Pronunciation Test: Tap mic and speak the word 'Teacher' clearly!",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo fooniin 'Teacher' jedhii dubbadhu!",
                        spokenTarget = "Teacher",
                        asrKeywords = listOf("teacher"),
                        tipAfaanOromoo = "'Teacher' jechuun sagalee CH iftoominaan baasi."
                    ),
                    ExamQuestion(
                        id = "l4_q4_sentence_order",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Unscramble into English SVO order: 'My teacher helps me read.'",
                        oromoInstruction = "Afaan Oromoo 'Barsiisaan koo dubbisuu na gargaara' qindeessi:",
                        scrambledWords = listOf("me", "read.", "helps", "teacher", "My"),
                        spokenTarget = "My teacher helps me read.",
                        tipAfaanOromoo = "My teacher (Mata-duree) -> helps (Gochima) -> me read (Antoo)."
                    ),
                    ExamQuestion(
                        id = "l4_q5_sentence_speech",
                        stageCategory = LevelStage.SENTENCE,
                        questionText = "Speaking Fluency Test: Tap mic and read: 'I walk to school every morning.'",
                        oromoInstruction = "Qormaata Dubbii: Maaykiroo fooniin 'I walk to school every morning' jedhii dubbadhu!",
                        spokenTarget = "I walk to school every morning.",
                        asrKeywords = listOf("walk", "school", "morning"),
                        tipAfaanOromoo = "Iftoominaan fi saffisa gaariin dubbadhu."
                    )
                )
            )
        )
    )

    fun getLevelJourney(levelNumber: Int): LevelJourney {
        return levelJourneys.find { it.levelNumber == levelNumber } ?: levelJourneys.first()
    }
}
