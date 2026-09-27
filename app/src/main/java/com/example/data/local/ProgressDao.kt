package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = :id LIMIT 1")
    fun getUserProgress(id: String = "primary_user"): Flow<ProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE id = :id LIMIT 1")
    suspend fun getUserProgressOnce(id: String = "primary_user"): ProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: ProgressEntity)

    @Query("SELECT * FROM practice_records ORDER BY timestamp DESC LIMIT 50")
    fun getAllRecords(): Flow<List<PracticeRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: PracticeRecordEntity)

    @Query("SELECT * FROM offline_packs")
    fun getPacks(): Flow<List<PackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPacks(packs: List<PackEntity>)

    @Query("UPDATE offline_packs SET isDownloaded = :isDownloaded WHERE id = :id")
    suspend fun updatePackStatus(id: String, isDownloaded: Boolean)
}
