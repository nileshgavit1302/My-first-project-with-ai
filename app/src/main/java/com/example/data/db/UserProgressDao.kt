package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {

    @Query("SELECT * FROM user_stats WHERE id = 1 LIMIT 1")
    fun getUserStats(): Flow<UserStatsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserStats(stats: UserStatsEntity)

    @Query("SELECT * FROM lesson_completions")
    fun getAllCompletedLessons(): Flow<List<LessonCompletionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLessonCompletion(completion: LessonCompletionEntity)

    @Query("SELECT * FROM project_completions")
    fun getAllCompletedProjects(): Flow<List<ProjectCompletionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProjectCompletion(completion: ProjectCompletionEntity)

    @Query("SELECT * FROM weak_topics ORDER BY mistakeCount DESC")
    fun getAllWeakTopics(): Flow<List<WeakTopicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeakTopic(topic: WeakTopicEntity)

    @Query("DELETE FROM weak_topics WHERE topicId = :topicId")
    suspend fun removeWeakTopic(topicId: String)

    @Query("SELECT * FROM unlocked_badges")
    fun getAllUnlockedBadges(): Flow<List<UnlockedBadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockBadge(badge: UnlockedBadgeEntity)

    @Query("DELETE FROM lesson_completions")
    suspend fun clearLessons()

    @Query("DELETE FROM project_completions")
    suspend fun clearProjects()

    @Query("DELETE FROM unlocked_badges")
    suspend fun clearBadges()

    @Query("DELETE FROM weak_topics")
    suspend fun clearWeakTopics()
}
