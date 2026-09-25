package com.example.data.model

enum class ExerciseType {
    EXPLANATION,
    MULTIPLE_CHOICE,
    FILL_BLANK,
    CODE_ORDERING,
    FIND_MISTAKE,
    OUTPUT_PREDICTION,
    CODE_CHALLENGE
}

data class Exercise(
    val id: String,
    val type: ExerciseType,
    val prompt: String,
    val explanation: String = "",
    val codeSnippet: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
    val blanksPrefix: String = "",
    val blanksSuffix: String = "",
    val correctOrder: List<String> = emptyList(),
    val solutionExplanation: String = "",
    val hint: String = "",
    val conceptTag: String = "Basics",
    val starterCode: String = "",
    val expectedTag: String = "",
    val evaluationRules: List<String> = emptyList() // tags or phrases required in code challenge
)

data class Lesson(
    val id: String,
    val unitId: Int,
    val title: String,
    val subtitle: String,
    val iconName: String = "code",
    val exercises: List<Exercise>,
    val xpReward: Int = 50
)

data class LearningUnit(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val iconName: String,
    val lessons: List<Lesson>,
    val xpReward: Int = 100,
    val requiredUnitToUnlock: Int = 0
)

enum class UnitStatus {
    LOCKED,
    AVAILABLE,
    IN_PROGRESS,
    COMPLETED
}
