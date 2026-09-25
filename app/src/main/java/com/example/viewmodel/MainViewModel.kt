package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.curriculum.CurriculumData
import com.example.data.db.AppDatabase
import com.example.data.db.HtmlQuestRepository
import com.example.data.db.UserStatsEntity
import com.example.data.db.WeakTopicEntity
import com.example.data.model.ALL_BADGES
import com.example.data.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppScreen {
    HOME,
    LEARN,
    PRACTICE,
    PROJECTS,
    PROFILE
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HtmlQuestRepository

    val userStats: StateFlow<UserStatsEntity?>
    val completedLessonIds: StateFlow<Set<String>>
    val completedProjectIds: StateFlow<Set<String>>
    val unlockedBadgeIds: StateFlow<Set<String>>
    val weakTopics: StateFlow<List<WeakTopicEntity>>

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _activeLesson = MutableStateFlow<Lesson?>(null)
    val activeLesson: StateFlow<Lesson?> = _activeLesson.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = HtmlQuestRepository(database.userProgressDao())

        userStats = repository.userStats.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        completedLessonIds = repository.completedLessons.map { list ->
            list.map { it.lessonId }.toSet()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

        completedProjectIds = repository.completedProjects.map { list ->
            list.map { it.projectId }.toSet()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

        unlockedBadgeIds = repository.unlockedBadges.map { list ->
            list.map { it.badgeId }.toSet()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

        weakTopics = repository.weakTopics.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun startLesson(lesson: Lesson) {
        _activeLesson.value = lesson
    }

    fun exitActiveLesson() {
        _activeLesson.value = null
    }

    fun finishOnboarding(experienceLevel: String, dailyGoalMinutes: Int) {
        viewModelScope.launch {
            repository.initializeUserStatsIfNeeded(experienceLevel, dailyGoalMinutes)
        }
    }

    fun completeLesson(lessonId: String, unitId: Int, xpEarned: Int) {
        viewModelScope.launch {
            repository.completeLesson(lessonId, unitId, xpEarned)

            // Update stats & XP
            val current = userStats.value ?: UserStatsEntity()
            val newXp = current.xp + xpEarned
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val isNewDay = current.lastActiveDate != today
            val updatedStreak = if (isNewDay) current.currentStreak + 1 else current.currentStreak
            val maxStreak = maxOf(current.longestStreak, updatedStreak)
            val updatedMinutes = current.todayMinutes + 3

            repository.saveStats(
                current.copy(
                    xp = newXp,
                    currentStreak = updatedStreak,
                    longestStreak = maxStreak,
                    lastActiveDate = today,
                    todayMinutes = updatedMinutes
                )
            )

            // Check badges
            checkBadges(newXp, completedLessonIds.value.size + 1, completedProjectIds.value.size, updatedStreak)

            _activeLesson.value = null
        }
    }

    fun completeProject(projectId: String, code: String, xpEarned: Int) {
        viewModelScope.launch {
            repository.completeProject(projectId, code)

            val current = userStats.value ?: UserStatsEntity()
            val newXp = current.xp + xpEarned
            repository.saveStats(
                current.copy(
                    xp = newXp,
                    todayMinutes = current.todayMinutes + 5
                )
            )

            // Unload first_project / project_builder badges
            repository.unlockBadge("first_project")
            if (completedProjectIds.value.size + 1 >= 3) {
                repository.unlockBadge("project_builder")
            }
            if (completedProjectIds.value.size + 1 >= 7) {
                repository.unlockBadge("html_complete")
            }
        }
    }

    fun recordMistake(topicId: String, topicName: String) {
        viewModelScope.launch {
            val existing = weakTopics.value.find { it.topicId == topicId }
            repository.recordMistake(topicId, topicName, existing?.mistakeCount ?: 0)
        }
    }

    private suspend fun checkBadges(xp: Int, lessonsCount: Int, projectsCount: Int, streak: Int) {
        if (lessonsCount >= 1) repository.unlockBadge("first_lesson")
        repository.unlockBadge("first_code")
        if (xp >= 100) repository.unlockBadge("xp_100")
        if (xp >= 500) repository.unlockBadge("xp_500")
        if (xp >= 1000) repository.unlockBadge("xp_1000")
        if (streak >= 7) repository.unlockBadge("streak_7")
        if (projectsCount >= 1) repository.unlockBadge("first_project")
        if (projectsCount >= 3) repository.unlockBadge("project_builder")
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
        }
    }
}
