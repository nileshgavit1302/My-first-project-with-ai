package com.example.data.model

data class ProjectValidationRule(
    val description: String,
    val check: (String) -> Boolean
)

data class HtmlProject(
    val id: String,
    val title: String,
    val subtitle: String,
    val difficulty: String,
    val iconEmoji: String,
    val description: String,
    val requirements: List<String>,
    val starterCode: String,
    val solutionCode: String,
    val requiredTags: List<String>,
    val xpReward: Int = 150
) {
    fun evaluate(userCode: String): Pair<Boolean, List<Pair<String, Boolean>>> {
        val lowerCode = userCode.lowercase()
        val results = requirements.mapIndexed { index, req ->
            val passed = when (index) {
                0 -> requiredTags.getOrNull(0)?.let { lowerCode.contains("<$it") } ?: true
                1 -> requiredTags.getOrNull(1)?.let { lowerCode.contains("<$it") } ?: true
                2 -> requiredTags.getOrNull(2)?.let { lowerCode.contains("<$it") } ?: true
                3 -> requiredTags.getOrNull(3)?.let { lowerCode.contains("<$it") } ?: true
                4 -> requiredTags.getOrNull(4)?.let { lowerCode.contains("<$it") } ?: true
                else -> true
            }
            req to passed
        }
        val allPassed = results.all { it.second } && userCode.trim().length > 30
        return allPassed to results
    }
}
