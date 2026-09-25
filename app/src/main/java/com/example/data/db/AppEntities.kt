package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 0,
    val currentStreak: Int = 1,
    val longestStreak: Int = 1,
    val lastActiveDate: String = "",
    val dailyGoalMinutes: Int = 10,
    val todayMinutes: Int = 0,
    val onboardingCompleted: Boolean = false,
    val experienceLevel: String = "Beginner",
    val userName: String = "HTML Adventurer"
)

@Entity(tableName = "lesson_completions")
data class LessonCompletionEntity(
    @PrimaryKey val lessonId: String,
    val unitId: Int,
    val isCompleted: Boolean = true,
    val xpEarned: Int = 50,
    val attempts: Int = 1,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "project_completions")
data class ProjectCompletionEntity(
    @PrimaryKey val projectId: String,
    val userCode: String,
    val isCompleted: Boolean = true,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "weak_topics")
data class WeakTopicEntity(
    @PrimaryKey val topicId: String,
    val topicName: String,
    val mistakeCount: Int = 1,
    val lastMistakeTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "unlocked_badges")
data class UnlockedBadgeEntity(
    @PrimaryKey val badgeId: String,
    val unlockedAt: Long = System.currentTimeMillis()
)
