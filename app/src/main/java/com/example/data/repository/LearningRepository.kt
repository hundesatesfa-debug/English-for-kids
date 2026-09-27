package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.PackEntity
import com.example.data.local.PracticeRecordEntity
import com.example.data.local.ProgressEntity
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LearningRepository(private val database: AppDatabase) {

    private val dao = database.progressDao()

    val userProgress: Flow<UserProfile> = dao.getUserProgress().map { entity ->
        if (entity == null) {
            UserProfile()
        } else {
            val langMode = try {
                InstructionLanguage.valueOf(entity.languageMode)
            } catch (_: Exception) {
                InstructionLanguage.OROMO
            }
            UserProfile(
                id = entity.id,
                name = entity.childName,
                role = when (entity.userRole) {
                    "PARENT" -> UserRole.PARENT
                    "TEACHER" -> UserRole.TEACHER
                    else -> UserRole.CHILD
                },
                currentLevel = entity.currentLevel,
                xpPoints = entity.xpPoints,
                coins = entity.coins,
                stars = entity.stars,
                streakDays = entity.streakDays,
                todayMinutesLearned = entity.minutesSpentToday,
                languageMode = langMode,
                oromoSupportEnabled = entity.oromoSupport,
                amharicSupportEnabled = entity.amharicSupport,
                slowSpeechEnabled = entity.slowSpeech,
                dyslexiaFontEnabled = entity.dyslexiaFriendly
            )
        }
    }

    val practiceHistory: Flow<List<PracticeRecordEntity>> = dao.getAllRecords()

    suspend fun initializeDefaultData() {
        val existing = dao.getUserProgressOnce()
        if (existing == null) {
            dao.insertOrUpdateProgress(
                ProgressEntity(
                    id = "primary_user",
                    childName = "Caaltuu",
                    userRole = "CHILD",
                    currentLevel = 1,
                    xpPoints = 140,
                    coins = 50,
                    stars = 22,
                    streakDays = 4,
                    completedAlphabetCount = 26,
                    completedVocabularyCount = 28,
                    completedStoriesCount = 3,
                    completedConversationsCount = 2,
                    avgReadingScore = 88,
                    avgSpeakingScore = 84,
                    minutesSpentToday = 12,
                    languageMode = "OROMO",
                    oromoSupport = true,
                    amharicSupport = false,
                    slowSpeech = false,
                    dyslexiaFriendly = false
                )
            )
            dao.insertPacks(
                EducationalContent.offlinePacks.map {
                    PackEntity(it.id, it.name, it.sizeMb, it.isInstalled)
                }
            )
        }
    }

    suspend fun addXpAndStars(xp: Int, stars: Int, coins: Int) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        val newXp = current.xpPoints + xp
        val newStars = current.stars + stars
        val newCoins = current.coins + coins

        dao.insertOrUpdateProgress(
            current.copy(
                xpPoints = newXp,
                stars = newStars,
                coins = newCoins,
                minutesSpentToday = current.minutesSpentToday + 2
            )
        )
    }

    suspend fun passLevelExam(levelNumber: Int, examScore: Int): Boolean {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        val isPassed = examScore >= 75
        if (isPassed) {
            val nextLevel = maxOf(current.currentLevel, levelNumber + 1)
            dao.insertOrUpdateProgress(
                current.copy(
                    currentLevel = nextLevel,
                    xpPoints = current.xpPoints + 120,
                    stars = current.stars + 5,
                    coins = current.coins + 25,
                    avgSpeakingScore = (current.avgSpeakingScore + examScore) / 2
                )
            )
            dao.insertRecord(
                PracticeRecordEntity(
                    moduleName = "Deep Exam Level $levelNumber",
                    targetText = "Deep Exam Level $levelNumber",
                    recognizedText = "Passed with score $examScore%",
                    score = examScore,
                    pronunciationAccuracy = examScore,
                    fluencyScore = examScore,
                    feedback = "Akkamaa! Qormaata darbee jira!"
                )
            )
        } else {
            dao.insertRecord(
                PracticeRecordEntity(
                    moduleName = "Deep Exam Level $levelNumber",
                    targetText = "Deep Exam Level $levelNumber",
                    recognizedText = "Score $examScore% (Needs 75% to pass)",
                    score = examScore,
                    pronunciationAccuracy = examScore,
                    fluencyScore = examScore,
                    feedback = "Ittuma fufi! 75% barbaachisa."
                )
            )
        }
        return isPassed
    }

    suspend fun recordPracticeSession(
        module: String,
        targetText: String,
        score: EvaluationScore
    ) {
        dao.insertRecord(
            PracticeRecordEntity(
                moduleName = module,
                targetText = targetText,
                recognizedText = score.recognizedText,
                score = score.score,
                pronunciationAccuracy = score.pronunciationAccuracy,
                fluencyScore = score.fluencyScore,
                feedback = score.feedbackLabel
            )
        )
        addXpAndStars(xp = 20, stars = 2, coins = 5)
    }

    suspend fun setRole(role: UserRole) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        dao.insertOrUpdateProgress(current.copy(userRole = role.name))
    }

    suspend fun setLanguageMode(mode: InstructionLanguage) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        val isOromo = mode == InstructionLanguage.OROMO || mode == InstructionLanguage.BOTH
        val isAmharic = mode == InstructionLanguage.AMHARIC || mode == InstructionLanguage.BOTH
        dao.insertOrUpdateProgress(
            current.copy(
                languageMode = mode.name,
                oromoSupport = isOromo,
                amharicSupport = isAmharic
            )
        )
    }

    suspend fun toggleOromoSupport(enabled: Boolean) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        dao.insertOrUpdateProgress(current.copy(oromoSupport = enabled))
    }

    suspend fun toggleAmharicSupport(enabled: Boolean) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        dao.insertOrUpdateProgress(current.copy(amharicSupport = enabled))
    }

    suspend fun toggleSlowSpeech(enabled: Boolean) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        dao.insertOrUpdateProgress(current.copy(slowSpeech = enabled))
    }

    suspend fun toggleDyslexiaFriendly(enabled: Boolean) {
        val current = dao.getUserProgressOnce() ?: ProgressEntity()
        dao.insertOrUpdateProgress(current.copy(dyslexiaFriendly = enabled))
    }

    suspend fun togglePackDownload(packId: String, isDownloaded: Boolean) {
        dao.updatePackStatus(packId, isDownloaded)
    }

    // Curriculum queries
    fun getLevels(): List<LearningLevel> = EducationalContent.levels
    fun getAlphabet(): List<AlphabetLetter> = EducationalContent.alphabetList
    fun getPhonics(): List<PhonicsSound> = EducationalContent.phonicsSounds
    fun getVocabularyCategories(): List<String> = EducationalContent.vocabularyCategories
    fun getVocabulary(category: String? = null): List<VocabularyWord> {
        return if (category == null || category == "All") {
            EducationalContent.vocabularyList
        } else {
            EducationalContent.vocabularyList.filter { it.category.equals(category, ignoreCase = true) }
        }
    }
    fun getStories(): List<ReadingStory> = EducationalContent.stories
    fun getConversations(): List<ConversationScenario> = EducationalContent.conversationScenarios
    fun getPronunciationChallenges(): List<PronunciationChallenge> = EducationalContent.pronunciationChallenges
    fun getSentenceTasks(): List<SentenceTask> = EducationalContent.sentenceTasks
    fun getPacks(): List<OfflinePack> = EducationalContent.offlinePacks
    fun getStudents(): List<StudentRecord> = EducationalContent.sampleStudents
    fun getAssignments(): List<TeacherAssignment> = EducationalContent.sampleAssignments
    fun getLevelJourney(levelNumber: Int): LevelJourney = EducationalContent.getLevelJourney(levelNumber)
    fun getLevelJourneys(): List<LevelJourney> = EducationalContent.levelJourneys
}
