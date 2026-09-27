package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class ProgressEntity(
    @PrimaryKey val id: String = "primary_user",
    val childName: String = "Caaltuu",
    val userRole: String = "CHILD",
    val currentLevel: Int = 1,
    val xpPoints: Int = 120,
    val coins: Int = 45,
    val stars: Int = 18,
    val streakDays: Int = 3,
    val lastActiveTimestamp: Long = System.currentTimeMillis(),
    val completedAlphabetCount: Int = 26,
    val completedVocabularyCount: Int = 32,
    val completedStoriesCount: Int = 3,
    val completedConversationsCount: Int = 2,
    val avgReadingScore: Int = 85,
    val avgSpeakingScore: Int = 82,
    val minutesSpentToday: Int = 14,
    val languageMode: String = "OROMO",
    val oromoSupport: Boolean = true,
    val amharicSupport: Boolean = false,
    val slowSpeech: Boolean = false,
    val dyslexiaFriendly: Boolean = false
)

@Entity(tableName = "practice_records")
data class PracticeRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val moduleName: String,
    val targetText: String,
    val recognizedText: String,
    val score: Int,
    val pronunciationAccuracy: Int,
    val fluencyScore: Int,
    val feedback: String
)

@Entity(tableName = "offline_packs")
data class PackEntity(
    @PrimaryKey val id: String,
    val title: String,
    val sizeMb: Int,
    val isDownloaded: Boolean
)
