package com.example.data.model

enum class UserRole {
    CHILD,
    PARENT,
    TEACHER
}

enum class InstructionLanguage(val label: String) {
    OROMO("Afaan Oromoo (Qubee)"),
    AMHARIC("አማርኛ (Amharic)"),
    BOTH("Afaan Oromoo + አማርኛ")
}

data class UserProfile(
    val id: String = "primary_user",
    val name: String = "Caaltuu",
    val role: UserRole = UserRole.CHILD,
    val avatarId: Int = 1,
    val currentLevel: Int = 1,
    val xpPoints: Int = 120,
    val coins: Int = 45,
    val stars: Int = 18,
    val streakDays: Int = 3,
    val dailyGoalMinutes: Int = 15,
    val todayMinutesLearned: Int = 8,
    val languageMode: InstructionLanguage = InstructionLanguage.OROMO,
    val oromoSupportEnabled: Boolean = true,
    val amharicSupportEnabled: Boolean = false,
    val slowSpeechEnabled: Boolean = false,
    val dyslexiaFontEnabled: Boolean = false,
    val highContrastEnabled: Boolean = false
)

data class LearningLevel(
    val levelNumber: Int,
    val title: String,
    val oromoTitle: String,
    val amharicTitle: String,
    val description: String,
    val oromoDescription: String,
    val isUnlocked: Boolean,
    val xpReward: Int,
    val iconName: String
)

data class AlphabetLetter(
    val letter: Char,
    val lowercase: Char,
    val phoneticSound: String,
    val exampleWord: String,
    val oromoWord: String,
    val amharicWord: String,
    val exampleSentence: String,
    val oromoSentence: String,
    val qubeeComparison: String,
    val emoji: String
)

data class PhonicsSound(
    val id: String,
    val category: String, // "Digraph", "Blend", "Vowel"
    val pattern: String,  // "sh", "ch", "th", "ph", "wh"
    val pronunciationTip: String,
    val oromoComparison: String,
    val amharicComparison: String,
    val words: List<String>,
    val sampleSentences: List<String>
)

data class VocabularyWord(
    val id: String,
    val category: String,
    val englishWord: String,
    val oromoWord: String,
    val amharicWord: String,
    val phonetic: String,
    val exampleSentence: String,
    val oromoSentence: String,
    val amharicSentence: String,
    val emoji: String,
    val isEthiopianCulture: Boolean = false
)

data class SentenceTask(
    val id: String,
    val targetSentence: String,
    val oromoTranslation: String,
    val amharicTranslation: String,
    val scrambledWords: List<String>,
    val emoji: String
)

data class StoryParagraph(
    val paragraphIndex: Int,
    val englishText: String,
    val oromoText: String,
    val amharicText: String,
    val highlightWords: List<String>
)

data class StoryQuestion(
    val id: String,
    val questionEnglish: String,
    val questionOromo: String,
    val questionAmharic: String,
    val options: List<String>,
    val correctIndex: Int
)

data class ReadingStory(
    val id: String,
    val title: String,
    val oromoTitle: String,
    val amharicTitle: String,
    val paragraphs: List<StoryParagraph>,
    val questions: List<StoryQuestion>,
    val coverEmoji: String,
    val difficulty: String
)

data class ConversationStep(
    val speaker: String,
    val speechEnglish: String,
    val speechOromo: String,
    val speechAmharic: String,
    val childPromptEnglish: String,
    val childPromptOromo: String,
    val childPromptAmharic: String,
    val responseOptions: List<String>,
    val correctOptionIndex: Int
)

data class ConversationScenario(
    val id: String,
    val title: String,
    val oromoTitle: String,
    val amharicTitle: String,
    val location: String,
    val bannerEmoji: String,
    val steps: List<ConversationStep>
)

data class PronunciationChallenge(
    val id: String,
    val title: String, // "P vs F Challenge", "P vs B", "V vs W"
    val oromoTitle: String,
    val amharicTitle: String,
    val contrastExplanation: String,
    val oromoTip: String,
    val amharicTip: String,
    val mouthShapeGuidance: String,
    val targetWords: List<Pair<String, String>>
)

data class EvaluationScore(
    val score: Int, // 0 - 100
    val pronunciationAccuracy: Int,
    val wordAccuracy: Int,
    val fluencyScore: Int,
    val confidenceScore: Int,
    val feedbackLabel: String,
    val recognizedText: String,
    val targetText: String,
    val matchedWords: List<String>,
    val missedWords: List<String>
)

data class StudentRecord(
    val id: String,
    val name: String,
    val grade: String,
    val readingScore: Int,
    val speakingScore: Int,
    val vocabularyCount: Int,
    val streakDays: Int,
    val weakArea: String,
    val lastActive: String
)

data class TeacherAssignment(
    val id: String,
    val title: String,
    val module: String,
    val targetScore: Int,
    val dueDate: String,
    val assignedCount: Int,
    val completedCount: Int
)

data class OfflinePack(
    val id: String,
    val name: String,
    val oromoName: String,
    val amharicName: String,
    val description: String,
    val sizeMb: Int,
    val isInstalled: Boolean,
    val itemCount: Int
)

data class TroublePhoneme(
    val phoneme: String,
    val afaan_oromoo_tip: String
)

data class OfflineScoringThresholds(
    val pass_score: Int = 60,
    val star_score: Int = 85
)

data class FeedbackLocalAudioKeys(
    val high_score_key: String = "audio_praise_high.mp3",
    val medium_score_key: String = "audio_praise_med.mp3",
    val retry_key: String = "audio_retry.mp3"
)

data class VoiceExercise(
    val exercise_id: String,
    val exercise_type: String, // repeat_after_me | sentence_completion | roleplay_response | picture_narration
    val app_character_audio_script: String,
    val afaan_oromoo_instruction_script: String,
    val expected_speech_target: String,
    val asr_keywords_required: List<String>,
    val phonetic_guide_for_kids: String,
    val trouble_phonemes: List<TroublePhoneme>,
    val offline_scoring_thresholds: OfflineScoringThresholds = OfflineScoringThresholds(),
    val feedback_local_audio_keys: FeedbackLocalAudioKeys = FeedbackLocalAudioKeys()
)

data class OfflineAssetsRequired(
    val local_audio_prompts: List<String>,
    val local_images: List<String>
)

data class OfflineLessonModule(
    val lesson_id: String,
    val target_level: String, // Level 1 | Level 2 | Level 3 | Level 4
    val topic: String,
    val learning_objective: String,
    val offline_assets_required: OfflineAssetsRequired,
    val afaan_oromoo_intro_script: String,
    val target_phoneme_focus: List<String>,
    val voice_exercises: List<VoiceExercise>
)

enum class LevelStage(val title: String, val oromoTitle: String, val icon: String) {
    LETTER("1. Letter", "1. Qubee", "abc"),
    SOUND("2. Sound", "2. Sagalee", "volume_up"),
    WORD("3. Word", "3. Jecha", "menu_book"),
    SENTENCE("4. Sentence", "4. Hima", "sort_by_alpha"),
    EXAM("5. Deep Exam", "5. Qormaata Gad-fagoo", "quiz")
}

data class ExamQuestion(
    val id: String,
    val stageCategory: LevelStage,
    val questionText: String,
    val oromoInstruction: String,
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = -1,
    val spokenTarget: String = "",
    val asrKeywords: List<String> = emptyList(),
    val scrambledWords: List<String> = emptyList(),
    val tipAfaanOromoo: String = ""
)

data class DeepExam(
    val levelNumber: Int,
    val title: String,
    val oromoTitle: String,
    val passScore: Int = 75,
    val xpReward: Int = 100,
    val starsReward: Int = 5,
    val coinsReward: Int = 20,
    val questions: List<ExamQuestion>
)

data class LevelJourney(
    val levelNumber: Int,
    val title: String,
    val oromoTitle: String,
    val description: String,
    val oromoDescription: String,
    val targetLetters: List<AlphabetLetter>,
    val targetPhonics: List<PhonicsSound>,
    val targetWords: List<VocabularyWord>,
    val targetSentences: List<SentenceTask>,
    val deepExam: DeepExam
)

