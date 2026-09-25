package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.GamificationTopBar
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.LessonScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.theme.HtmlQuestTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

data class NavItem(
    val screen: AppScreen,
    val label: String,
    val icon: ImageVector,
    val testTag: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HtmlQuestTheme {
                HtmlQuestApp()
            }
        }
    }
}

@Composable
fun HtmlQuestApp(viewModel: MainViewModel = viewModel()) {
    val userStats by viewModel.userStats.collectAsStateWithLifecycle()
    val completedLessonIds by viewModel.completedLessonIds.collectAsStateWithLifecycle()
    val completedProjectIds by viewModel.completedProjectIds.collectAsStateWithLifecycle()
    val unlockedBadgeIds by viewModel.unlockedBadgeIds.collectAsStateWithLifecycle()
    val weakTopics by viewModel.weakTopics.collectAsStateWithLifecycle()
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val activeLesson by viewModel.activeLesson.collectAsStateWithLifecycle()

    val navItems = listOf(
        NavItem(AppScreen.HOME, "Home", Icons.Default.Home, "nav_home"),
        NavItem(AppScreen.LEARN, "Learn", Icons.Default.Map, "nav_learn"),
        NavItem(AppScreen.PRACTICE, "Practice", Icons.Default.FitnessCenter, "nav_practice"),
        NavItem(AppScreen.PROJECTS, "Projects", Icons.Default.Code, "nav_projects"),
        NavItem(AppScreen.PROFILE, "Profile", Icons.Default.Person, "nav_profile")
    )

    // Handle back button
    if (activeLesson != null) {
        BackHandler {
            viewModel.exitActiveLesson()
        }
    } else if (currentScreen != AppScreen.HOME) {
        BackHandler {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    // First-run Onboarding
    val needsOnboarding = userStats == null || !userStats!!.onboardingCompleted
    if (needsOnboarding) {
        OnboardingScreen(
            onFinishOnboarding = { expLevel, dailyGoal ->
                viewModel.finishOnboarding(expLevel, dailyGoal)
            }
        )
        return
    }

    // Active Lesson Screen (Full screen overlay with gamification)
    if (activeLesson != null) {
        LessonScreen(
            lesson = activeLesson!!,
            onCompleteLesson = { lessonId, unitId, xpEarned ->
                viewModel.completeLesson(lessonId, unitId, xpEarned)
            },
            onExitLesson = {
                viewModel.exitActiveLesson()
            },
            onRecordMistake = { topicId, topicName ->
                viewModel.recordMistake(topicId, topicName)
            }
        )
        return
    }

    // Main App Scaffold
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            GamificationTopBar(
                streakDays = userStats?.currentStreak ?: 1,
                totalXp = userStats?.xp ?: 0,
                onProfileClick = { viewModel.navigateTo(AppScreen.PROFILE) }
            )
        },
        bottomBar = {
            NavigationBar(modifier = Modifier.testTag("bottom_nav_bar")) {
                navItems.forEach { item ->
                    val selected = currentScreen == item.screen
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.navigateTo(item.screen) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(text = item.label, fontSize = 11.sp)
                        },
                        modifier = Modifier.testTag(item.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_navigation"
            ) { target ->
                when (target) {
                    AppScreen.HOME -> HomeScreen(
                        userStats = userStats,
                        completedLessonIds = completedLessonIds,
                        unlockedBadgeIds = unlockedBadgeIds,
                        onContinueLearning = { lesson -> viewModel.startLesson(lesson) },
                        onNavigateToLearn = { viewModel.navigateTo(AppScreen.LEARN) },
                        onNavigateToPractice = { viewModel.navigateTo(AppScreen.PRACTICE) },
                        onNavigateToProjects = { viewModel.navigateTo(AppScreen.PROJECTS) }
                    )

                    AppScreen.LEARN -> LearnScreen(
                        completedLessonIds = completedLessonIds,
                        onSelectLesson = { lesson -> viewModel.startLesson(lesson) }
                    )

                    AppScreen.PRACTICE -> PracticeScreen(
                        weakTopics = weakTopics,
                        onStartPracticeSession = { lesson -> viewModel.startLesson(lesson) }
                    )

                    AppScreen.PROJECTS -> ProjectsScreen(
                        completedProjectIds = completedProjectIds,
                        onCompleteProject = { id, code, xp ->
                            viewModel.completeProject(id, code, xp)
                        }
                    )

                    AppScreen.PROFILE -> ProfileScreen(
                        userStats = userStats,
                        completedLessonIds = completedLessonIds,
                        completedProjectIds = completedProjectIds,
                        unlockedBadgeIds = unlockedBadgeIds,
                        onResetProgress = { viewModel.resetAllProgress() }
                    )
                }
            }
        }
    }
}
