package com.example.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.navigation.Screen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.alphabet.AlphabetScreen
import com.example.ui.screens.conversation.ConversationScreen
import com.example.ui.screens.games.MiniGamesScreen
import com.example.ui.screens.levels.LevelJourneyScreen
import com.example.ui.screens.levels.LevelsMapScreen
import com.example.ui.screens.parent.ParentDashboardScreen
import com.example.ui.screens.phonics.PhonicsScreen
import com.example.ui.screens.pronunciation.PronunciationLabScreen
import com.example.ui.screens.reading.ReadingScreen
import com.example.ui.screens.sentence.SentenceBuilderScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.stories.StoryScreen
import com.example.ui.screens.teacher.TeacherDashboardScreen
import com.example.ui.screens.vocabulary.VocabularyScreen

@Composable
fun SpeakEngApp(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val practiceRecords by viewModel.practiceRecords.collectAsStateWithLifecycle()

    val selectedAlphabetLetter by viewModel.selectedAlphabetLetter.collectAsStateWithLifecycle()
    val selectedVocabCategory by viewModel.selectedVocabCategory.collectAsStateWithLifecycle()
    val selectedStory by viewModel.selectedStory.collectAsStateWithLifecycle()
    val selectedConversation by viewModel.selectedConversation.collectAsStateWithLifecycle()
    val selectedPronunciation by viewModel.selectedPronunciation.collectAsStateWithLifecycle()

    val isListening by viewModel.isListening.collectAsStateWithLifecycle()
    val lastEvaluation by viewModel.lastEvaluation.collectAsStateWithLifecycle()

    // Runtime Permission for Microphone
    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasAudioPermission = isGranted
    }

    fun ensureAudioAndStartListening(targetText: String, moduleName: String) {
        if (hasAudioPermission) {
            viewModel.startVoiceEvaluation(targetText, moduleName)
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    fun ensureAudioAndStartListeningWithKeywords(targetText: String, keywords: List<String>, moduleName: String) {
        if (hasAudioPermission) {
            viewModel.startVoiceEvaluation(targetText, moduleName, keywords)
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Hardware/Gesture Back Handling for all sub-screens
    if (currentScreen != Screen.Home) {
        BackHandler {
            viewModel.navigateBack()
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        val screen = currentScreen
        when (screen) {
            Screen.Home -> {
                HomeScreen(
                    user = userProfile,
                    onNavigate = { dest -> viewModel.navigateTo(dest) },
                    onSpeakTeacher = { text -> viewModel.speak(text) },
                    onRoleClick = { viewModel.navigateTo(Screen.Settings) },
                    onSettingsClick = { viewModel.navigateTo(Screen.Settings) }
                )
            }
            Screen.LevelsMap -> {
                LevelsMapScreen(
                    levels = viewModel.getLevels(),
                    currentUnlockedLevel = userProfile.currentLevel,
                    showOromo = userProfile.oromoSupportEnabled,
                    onBack = { viewModel.navigateBack() },
                    onSelectLevel = { _, targetScreen -> viewModel.navigateTo(targetScreen) }
                )
            }
            is Screen.LevelJourney -> {
                val journey = viewModel.getLevelJourney(screen.levelNumber)
                LevelJourneyScreen(
                    levelJourney = journey,
                    initialStage = screen.initialStageIndex,
                    currentUnlockedLevel = userProfile.currentLevel,
                    onSpeak = { text -> viewModel.speak(text) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { target, keywords ->
                        ensureAudioAndStartListeningWithKeywords(target, keywords, "Level ${screen.levelNumber}")
                    },
                    onStopListening = { viewModel.stopListening() },
                    onPassExam = { levelNum, score, onResult ->
                        viewModel.passLevelExam(levelNum, score, onResult)
                    },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 1, 3) },
                    onNavigateNextLevel = { nextLvl ->
                        viewModel.navigateTo(Screen.LevelJourney(nextLvl))
                    },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.Alphabet -> {
                AlphabetScreen(
                    alphabet = viewModel.getAlphabet(),
                    selectedLetter = selectedAlphabetLetter,
                    onSelectLetter = { viewModel.selectedAlphabetLetter.value = it },
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Alphabet") },
                    onStopListening = { viewModel.stopListening() },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 1, 3) },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.Phonics -> {
                PhonicsScreen(
                    phonicsList = viewModel.getPhonics(),
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Phonics") },
                    onStopListening = { viewModel.stopListening() },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.Vocabulary -> {
                VocabularyScreen(
                    categories = viewModel.getCategories(),
                    allWords = viewModel.getVocabulary(),
                    selectedCategory = selectedVocabCategory,
                    onSelectCategory = { cat: String -> viewModel.selectedVocabCategory.value = cat },
                    onSpeak = { wordText: String -> viewModel.speak(wordText) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text: String -> ensureAudioAndStartListening(text, "Vocabulary") },
                    onStopListening = { viewModel.stopListening() },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.PronunciationLab -> {
                PronunciationLabScreen(
                    challenges = viewModel.getPronunciationChallenges(),
                    selectedChallenge = selectedPronunciation,
                    onSelectChallenge = { viewModel.selectedPronunciation.value = it },
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Pronunciation Lab") },
                    onStopListening = { viewModel.stopListening() },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.ReadingFluency -> {
                ReadingScreen(
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Reading Fluency") },
                    onStopListening = { viewModel.stopListening() },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 2, 4) },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.Stories, Screen.StoryDetail -> {
                StoryScreen(
                    stories = viewModel.getStories(),
                    selectedStory = selectedStory,
                    onSelectStory = { viewModel.selectedStory.value = it },
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Story Reading") },
                    onStopListening = { viewModel.stopListening() },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 2, 5) },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.Conversations, Screen.ConversationDetail -> {
                ConversationScreen(
                    scenarios = viewModel.getConversations(),
                    selectedScenario = selectedConversation,
                    onSelectScenario = { viewModel.selectedConversation.value = it },
                    onSpeak = { viewModel.speak(it) },
                    isListening = isListening,
                    lastScore = lastEvaluation,
                    onStartListening = { text -> ensureAudioAndStartListening(text, "Conversations") },
                    onStopListening = { viewModel.stopListening() },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 2, 5) },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.SentenceBuilder -> {
                SentenceBuilderScreen(
                    sentenceTasks = viewModel.getSentenceTasks(),
                    onSpeak = { viewModel.speak(it) },
                    onAwardXp = { xp -> viewModel.awardXp(xp, 1, 3) },
                    onBack = { viewModel.navigateBack() },
                    showOromo = userProfile.oromoSupportEnabled,
                    showAmharic = userProfile.amharicSupportEnabled
                )
            }
            Screen.MiniGames -> {
                MiniGamesScreen(
                    onSpeak = { viewModel.speak(it) },
                    onAwardReward = { xp, stars, coins -> viewModel.awardXp(xp, stars, coins) },
                    onBack = { viewModel.navigateBack() }
                )
            }
            Screen.ParentDashboard -> {
                ParentDashboardScreen(
                    user = userProfile,
                    records = practiceRecords,
                    onBack = { viewModel.navigateBack() }
                )
            }
            Screen.TeacherDashboard -> {
                TeacherDashboardScreen(
                    students = viewModel.getStudents(),
                    assignments = viewModel.getAssignments(),
                    onBack = { viewModel.navigateBack() }
                )
            }
            Screen.Settings -> {
                SettingsScreen(
                    user = userProfile,
                    packs = viewModel.getPacks(),
                    onSelectLanguageMode = { viewModel.setLanguageMode(it) },
                    onToggleOromo = { viewModel.toggleOromo(it) },
                    onToggleAmharic = { viewModel.toggleAmharic(it) },
                    onToggleSlowSpeech = { viewModel.toggleSlowSpeech(it) },
                    onToggleDyslexia = { viewModel.toggleDyslexia(it) },
                    onSelectRole = { viewModel.setUserRole(it) },
                    onTogglePack = { id, installed -> viewModel.togglePack(id, installed) },
                    onBack = { viewModel.navigateBack() }
                )
            }
        }
    }
}
