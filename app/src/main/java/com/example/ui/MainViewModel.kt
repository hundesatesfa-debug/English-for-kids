package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SpeechEvaluator
import com.example.audio.TtsManager
import com.example.data.local.AppDatabase
import com.example.data.local.PracticeRecordEntity
import com.example.data.model.*
import com.example.data.repository.EducationalContent
import com.example.data.repository.LearningRepository
import com.example.ui.navigation.Screen
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = LearningRepository(database)

    val ttsManager = TtsManager(application)
    val speechEvaluator = SpeechEvaluator(application)

    val userProfile: StateFlow<UserProfile> = repository.userProgress
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    val practiceRecords: StateFlow<List<PracticeRecordEntity>> = repository.practiceHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Navigation and Selection States
    val currentScreen = MutableStateFlow<Screen>(Screen.Home)
    private val screenStack = mutableListOf<Screen>(Screen.Home)

    val selectedAlphabetLetter = MutableStateFlow(EducationalContent.alphabetList.first())
    val selectedVocabCategory = MutableStateFlow("All")
    val selectedStory = MutableStateFlow(EducationalContent.stories.first())
    val selectedConversation = MutableStateFlow(EducationalContent.conversationScenarios.first())
    val selectedPronunciation = MutableStateFlow(EducationalContent.pronunciationChallenges.first())

    val isListening: StateFlow<Boolean> = speechEvaluator.isListening
    val audioRms: StateFlow<Float> = speechEvaluator.audioRmsDb
    val lastEvaluation: StateFlow<EvaluationScore?> = speechEvaluator.lastScore

    init {
        viewModelScope.launch {
            repository.initializeDefaultData()
        }
    }

    fun navigateTo(screen: Screen) {
        if (currentScreen.value != screen) {
            screenStack.add(screen)
            currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.size - 1)
            currentScreen.value = screenStack.last()
            return true
        }
        return false
    }

    fun speak(text: String, isSlow: Boolean = false) {
        val userSlow = userProfile.value.slowSpeechEnabled || isSlow
        ttsManager.speak(text, userSlow)
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    fun startVoiceEvaluation(targetText: String, moduleName: String, keywords: List<String> = emptyList()) {
        speechEvaluator.startListening(targetText, keywords) { score ->
            viewModelScope.launch {
                repository.recordPracticeSession(moduleName, targetText, score)
            }
        }
    }

    fun stopListening() {
        speechEvaluator.stopListening()
    }

    fun awardXp(xp: Int, stars: Int, coins: Int) {
        viewModelScope.launch {
            repository.addXpAndStars(xp, stars, coins)
        }
    }

    fun setUserRole(role: UserRole) {
        viewModelScope.launch {
            repository.setRole(role)
        }
    }

    fun setLanguageMode(mode: InstructionLanguage) {
        viewModelScope.launch {
            repository.setLanguageMode(mode)
        }
    }

    fun toggleOromo(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleOromoSupport(enabled)
        }
    }

    fun toggleAmharic(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleAmharicSupport(enabled)
        }
    }

    fun toggleSlowSpeech(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleSlowSpeech(enabled)
        }
    }

    fun toggleDyslexia(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleDyslexiaFriendly(enabled)
        }
    }

    fun togglePack(packId: String, isDownloaded: Boolean) {
        viewModelScope.launch {
            repository.togglePackDownload(packId, isDownloaded)
        }
    }

    fun getLevels() = repository.getLevels()
    fun getAlphabet() = repository.getAlphabet()
    fun getPhonics() = repository.getPhonics()
    fun getCategories() = repository.getVocabularyCategories()
    fun getVocabulary(category: String? = null) = repository.getVocabulary(category)
    fun getStories() = repository.getStories()
    fun getConversations() = repository.getConversations()
    fun getPronunciationChallenges() = repository.getPronunciationChallenges()
    fun getSentenceTasks() = repository.getSentenceTasks()
    fun getPacks() = repository.getPacks()
    fun getStudents() = repository.getStudents()
    fun getAssignments() = repository.getAssignments()
    fun getLevelJourney(levelNumber: Int) = repository.getLevelJourney(levelNumber)
    fun getLevelJourneys() = repository.getLevelJourneys()

    fun passLevelExam(levelNumber: Int, score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val passed = repository.passLevelExam(levelNumber, score)
            onResult(passed)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
        speechEvaluator.destroy()
    }
}
