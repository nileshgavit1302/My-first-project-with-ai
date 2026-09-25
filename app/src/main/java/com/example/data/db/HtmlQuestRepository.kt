package com.example.data.db

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HtmlQuestRepository(private val dao: UserProgressDao) {

    val userStats: Flow<UserStatsEntity?> = dao.getUserStats()
    val completedLessons: Flow<List<LessonCompletionEntity>> = dao.getAllCompletedLessons()
    val completedProjects: Flow<List<ProjectCompletionEntity>> = dao.getAllCompletedProjects()
    val weakTopics: Flow<List<WeakTopicEntity>> = dao.getAllWeakTopics()
    val unlockedBadges: Flow<List<UnlockedBadgeEntity>> = dao.getAllUnlockedBadges()

    suspend fun initializeUserStatsIfNeeded(experienceLevel: String = "Beginner", dailyGoal: Int = 10) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val defaultStats = UserStatsEntity(
            id = 1,
            xp = 0,
            currentStreak = 1,
            longestStreak = 1,
            lastActiveDate = today,
            dailyGoalMinutes = dailyGoal,
            todayMinutes = 0,
            onboardingCompleted = true,
            experienceLevel = experienceLevel,
            userName = "HTML Adventurer"
        )
        dao.saveUserStats(defaultStats)
    }

    suspend fun addXpAndRecordActivity(xpToAdd: Int, additionalMinutes: Int = 2) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        // Since we can query current stats, let's load or save directly
        // Note: we can read current from flow or update via entity
    }

    suspend fun saveStats(stats: UserStatsEntity) {
        dao.saveUserStats(stats)
    }

    suspend fun completeLesson(lessonId: String, unitId: Int, xpEarned: Int) {
        dao.saveLessonCompletion(
            LessonCompletionEntity(
                lessonId = lessonId,
                unitId = unitId,
                isCompleted = true,
                xpEarned = xpEarned,
                attempts = 1,
                completedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun completeProject(projectId: String, code: String) {
        dao.saveProjectCompletion(
            ProjectCompletionEntity(
                projectId = projectId,
                userCode = code,
                isCompleted = true,
                completedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun recordMistake(topicId: String, topicName: String, currentCount: Int = 0) {
        dao.saveWeakTopic(
            WeakTopicEntity(
                topicId = topicId,
                topicName = topicName,
                mistakeCount = currentCount + 1,
                lastMistakeTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun resolveWeakTopic(topicId: String) {
        dao.removeWeakTopic(topicId)
    }

    suspend fun unlockBadge(badgeId: String) {
        dao.unlockBadge(
            UnlockedBadgeEntity(
                badgeId = badgeId,
                unlockedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun resetAllProgress() {
        dao.clearLessons()
        dao.clearProjects()
        dao.clearBadges()
        dao.clearWeakTopics()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dao.saveUserStats(
            UserStatsEntity(
                id = 1,
                xp = 0,
                currentStreak = 1,
                longestStreak = 1,
                lastActiveDate = today,
                dailyGoalMinutes = 10,
                todayMinutes = 0,
                onboardingCompleted = true,
                experienceLevel = "Beginner",
                userName = "HTML Adventurer"
            )
        )
    }
}
