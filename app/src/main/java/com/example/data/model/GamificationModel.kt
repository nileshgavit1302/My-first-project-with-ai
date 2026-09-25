package com.example.data.model

data class BadgeDefinition(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val requiredXp: Int = 0,
    val requiredLessons: Int = 0,
    val requiredProjects: Int = 0,
    val requiredStreak: Int = 0
)

data class LearnerLevel(
    val levelNumber: Int,
    val title: String,
    val minXp: Int,
    val maxXp: Int,
    val rankBadge: String
)

object LevelSystem {
    val levels = listOf(
        LearnerLevel(1, "HTML Beginner", 0, 150, "🌱"),
        LearnerLevel(2, "HTML Explorer", 150, 400, "🧭"),
        LearnerLevel(3, "HTML Builder", 400, 800, "🔨"),
        LearnerLevel(4, "HTML Coder", 800, 1400, "💻"),
        LearnerLevel(5, "HTML Developer", 1400, 2200, "🚀"),
        LearnerLevel(6, "HTML Master", 2200, 5000, "👑")
    )

    fun getLevelForXp(xp: Int): LearnerLevel {
        return levels.lastOrNull { xp >= it.minXp } ?: levels.first()
    }

    fun getNextLevel(xp: Int): LearnerLevel? {
        val current = getLevelForXp(xp)
        val index = levels.indexOf(current)
        return if (index + 1 < levels.size) levels[index + 1] else null
    }

    fun getLevelProgress(xp: Int): Float {
        val current = getLevelForXp(xp)
        val next = getNextLevel(xp) ?: return 1f
        val range = (next.minXp - current.minXp).toFloat()
        val currentWithin = (xp - current.minXp).coerceAtLeast(0).toFloat()
        return (currentWithin / range).coerceIn(0f, 1f)
    }
}

val ALL_BADGES = listOf(
    BadgeDefinition("first_lesson", "First Lesson", "Completed your very first HTML lesson", "🎯", requiredLessons = 1),
    BadgeDefinition("first_code", "First Code", "Ran and verified your first HTML code snippet", "⚡"),
    BadgeDefinition("xp_100", "100 XP Club", "Earned 100 XP from lessons and practice", "⭐", requiredXp = 100),
    BadgeDefinition("xp_500", "500 XP Scholar", "Earned 500 XP through dedication", "🌟", requiredXp = 500),
    BadgeDefinition("xp_1000", "1,000 XP Veteran", "Crossed the 1,000 XP milestone", "🏆", requiredXp = 1000),
    BadgeDefinition("streak_7", "7 Day Streak", "Learned HTML 7 days in a row", "🔥", requiredStreak = 7),
    BadgeDefinition("first_project", "First Project", "Built and passed your first real HTML project", "🛠️", requiredProjects = 1),
    BadgeDefinition("forms_master", "Forms Master", "Completed all form exercises and validation", "📝"),
    BadgeDefinition("semantic_html", "Semantic Hero", "Mastered semantic tags for cleaner web structure", "🏛️"),
    BadgeDefinition("accessibility_explorer", "Accessibility Explorer", "Learned web accessibility and meaningful markup", "🌐"),
    BadgeDefinition("project_builder", "Project Builder", "Completed at least 3 HTML portfolio projects", "🏗️", requiredProjects = 3),
    BadgeDefinition("html_complete", "HTML Complete", "Conquered all HTML Quest curriculum units", "👑")
)
