package com.example.ui.navigation

sealed class Screen(val title: String) {
    object Home : Screen("Home")
    object LevelsMap : Screen("Learning Levels")
    object Alphabet : Screen("Alphabet")
    object Phonics : Screen("Phonics Sounds")
    object Vocabulary : Screen("Vocabulary")
    object SentenceBuilder : Screen("Sentence Builder")
    object ReadingFluency : Screen("Reading Fluency")
    object Stories : Screen("Stories")
    object StoryDetail : Screen("Story Reading")
    object Conversations : Screen("Conversations")
    object ConversationDetail : Screen("Conversation Practice")
    object PronunciationLab : Screen("Pronunciation Lab")
    object MiniGames : Screen("Mini Games")
    data class LevelJourney(val levelNumber: Int, val initialStageIndex: Int = 0) : Screen("Level Journey")
    object ParentDashboard : Screen("Parent Dashboard")
    object TeacherDashboard : Screen("Teacher Dashboard")
    object Settings : Screen("Settings")
}
