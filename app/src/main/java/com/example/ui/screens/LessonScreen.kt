package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.Lesson
import com.example.ui.components.CodeEditorView
import com.example.ui.components.FeedbackBanner
import com.example.ui.components.FeedbackState
import com.example.ui.components.HtmlPreviewCard
import com.example.ui.theme.CodeCyan
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

@Composable
fun LessonScreen(
    lesson: Lesson,
    onCompleteLesson: (lessonId: String, unitId: Int, xpEarned: Int) -> Unit,
    onExitLesson: () -> Unit,
    onRecordMistake: (topicId: String, topicName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var lessonFinished by remember { mutableStateOf(false) }
    var feedbackState by remember { mutableStateOf(FeedbackState.IDLE) }
    var feedbackExplanation by remember { mutableStateOf("") }
    var feedbackSolution by remember { mutableStateOf("") }
    var currentExerciseXp by remember { mutableIntStateOf(10) }

    val currentExercise = lesson.exercises.getOrNull(currentIndex)
    val progress = (currentIndex.toFloat() / lesson.exercises.size).coerceIn(0f, 1f)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (lessonFinished) {
            LessonCompletionView(
                lesson = lesson,
                onReturn = {
                    onCompleteLesson(lesson.id, lesson.unitId, lesson.xpReward)
                }
            )
        } else if (currentExercise != null) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header with Exit Button & Smooth Progress Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onExitLesson,
                        modifier = Modifier.testTag("exit_lesson_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit Lesson",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(10.dp)
                            .padding(horizontal = 8.dp),
                        color = QuestGreen,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Text(
                        text = "${currentIndex + 1}/${lesson.exercises.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Interactive Exercise Content Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnimatedContent(
                        targetState = currentIndex,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "exercise_transition"
                    ) { idx ->
                        val ex = lesson.exercises[idx]
                        ExerciseView(
                            exercise = ex,
                            feedbackActive = feedbackState != FeedbackState.IDLE,
                            onAnswerSubmitted = { isCorrect, explanation, solution ->
                                if (isCorrect) {
                                    feedbackState = FeedbackState.CORRECT
                                    feedbackExplanation = ""
                                    feedbackSolution = ""
                                    currentExerciseXp = 10
                                } else {
                                    feedbackState = FeedbackState.INCORRECT
                                    feedbackExplanation = explanation
                                    feedbackSolution = solution
                                    currentExerciseXp = 0
                                    onRecordMistake(ex.conceptTag.lowercase(), ex.conceptTag)
                                }
                            }
                        )
                    }
                }

                // Bottom Non-Punitive Feedback Banner
                FeedbackBanner(
                    state = feedbackState,
                    explanation = feedbackExplanation,
                    solution = feedbackSolution,
                    xpReward = currentExerciseXp,
                    onContinue = {
                        feedbackState = FeedbackState.IDLE
                        if (currentIndex + 1 < lesson.exercises.size) {
                            currentIndex++
                        } else {
                            lessonFinished = true
                        }
                    },
                    onRetry = {
                        feedbackState = FeedbackState.IDLE
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExerciseView(
    exercise: Exercise,
    feedbackActive: Boolean,
    onAnswerSubmitted: (Boolean, String, String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        // Concept Badge
        Surface(
            color = HtmlOrange.copy(alpha = 0.15f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = exercise.conceptTag.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = HtmlOrange,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Exercise Prompt
        Text(
            text = exercise.prompt,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (exercise.type) {
            ExerciseType.EXPLANATION -> {
                Text(
                    text = exercise.explanation,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (exercise.codeSnippet.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = exercise.codeSnippet,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = Color(0xFF38BDF8),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onAnswerSubmitted(true, "", "") },
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("explanation_continue_btn")
                ) {
                    Text("Got it! Continue", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            ExerciseType.MULTIPLE_CHOICE, ExerciseType.OUTPUT_PREDICTION, ExerciseType.FIND_MISTAKE -> {
                if (exercise.codeSnippet.isNotEmpty()) {
                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = exercise.codeSnippet,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = Color(0xFFE2E8F0),
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                var selectedOption by remember(exercise.id) { mutableStateOf<String?>(null) }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    exercise.options.forEach { option ->
                        val isSelected = selectedOption == option
                        Surface(
                            onClick = {
                                if (!feedbackActive) selectedOption = option
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) HtmlOrange.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                if (isSelected) HtmlOrange else Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val isCorrect = selectedOption?.trim() == exercise.correctAnswer.trim()
                        onAnswerSubmitted(isCorrect, exercise.solutionExplanation, exercise.correctAnswer)
                    },
                    enabled = selectedOption != null && !feedbackActive,
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("check_answer_btn")
                ) {
                    Text("Check Answer", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            ExerciseType.FILL_BLANK -> {
                var selectedChoice by remember(exercise.id) { mutableStateOf<String?>(null) }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = exercise.blanksPrefix,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )
                        Surface(
                            color = if (selectedChoice != null) HtmlOrange.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, HtmlOrange),
                            modifier = Modifier.padding(horizontal = 6.dp)
                        ) {
                            Text(
                                text = selectedChoice ?: "  ___  ",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedChoice != null) HtmlOrange else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Text(
                            text = exercise.blanksSuffix,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )
                    }
                }

                Text(
                    text = "Select the missing tag:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    exercise.options.forEach { opt ->
                        val isChosen = selectedChoice == opt
                        Surface(
                            onClick = { if (!feedbackActive) selectedChoice = opt },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isChosen) HtmlOrange else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = opt,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isChosen) Color.White else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val isCorrect = selectedChoice?.trim() == exercise.correctAnswer.trim()
                        onAnswerSubmitted(isCorrect, exercise.solutionExplanation, exercise.correctAnswer)
                    },
                    enabled = selectedChoice != null && !feedbackActive,
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("check_blank_btn")
                ) {
                    Text("Check Answer", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            ExerciseType.CODE_ORDERING -> {
                val currentList = remember(exercise.id) {
                    mutableStateListOf<String>().apply {
                        addAll(exercise.correctOrder.shuffled())
                    }
                }

                Text(
                    text = "Tap items to reorder into the correct sequence:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    currentList.forEachIndexed { index, item ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF38BDF8)
                                )

                                Row {
                                    if (index > 0) {
                                        Text(
                                            text = "▲",
                                            modifier = Modifier
                                                .clickable {
                                                    val temp = currentList[index]
                                                    currentList[index] = currentList[index - 1]
                                                    currentList[index - 1] = temp
                                                }
                                                .padding(6.dp),
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    if (index < currentList.size - 1) {
                                        Text(
                                            text = "▼",
                                            modifier = Modifier
                                                .clickable {
                                                    val temp = currentList[index]
                                                    currentList[index] = currentList[index + 1]
                                                    currentList[index + 1] = temp
                                                }
                                                .padding(6.dp),
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val isCorrect = currentList.toList() == exercise.correctOrder
                        onAnswerSubmitted(isCorrect, exercise.solutionExplanation, exercise.correctOrder.joinToString(" -> "))
                    },
                    enabled = !feedbackActive,
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("check_order_btn")
                ) {
                    Text("Check Order", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            ExerciseType.CODE_CHALLENGE -> {
                var userCode by remember(exercise.id) { mutableStateOf(exercise.starterCode) }
                var previewHtml by remember(exercise.id) { mutableStateOf(exercise.starterCode) }

                Column(modifier = Modifier.fillMaxWidth()) {
                    CodeEditorView(
                        code = userCode,
                        onCodeChange = { userCode = it },
                        onRun = { previewHtml = userCode },
                        onReset = {
                            userCode = exercise.starterCode
                            previewHtml = exercise.starterCode
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Live Preview:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    HtmlPreviewCard(
                        htmlContent = previewHtml,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            previewHtml = userCode
                            val lower = userCode.lowercase()
                            val passedRules = exercise.evaluationRules.all { rule ->
                                lower.contains(rule.lowercase())
                            }
                            val hasTag = if (exercise.expectedTag.isNotEmpty()) {
                                lower.contains("<${exercise.expectedTag.lowercase()}")
                            } else true

                            val isSuccess = passedRules && hasTag
                            onAnswerSubmitted(
                                isSuccess,
                                exercise.solutionExplanation.ifEmpty { "Ensure you included the required tag and content!" },
                                exercise.expectedTag
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = QuestGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("check_code_challenge_btn")
                    ) {
                        Text("Verify & Run Challenge", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonCompletionView(
    lesson: Lesson,
    onReturn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = XpGold.copy(alpha = 0.2f),
            modifier = Modifier.size(90.dp)
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Success",
                tint = XpGold,
                modifier = Modifier.padding(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Lesson Complete! 🎉",
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = lesson.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HtmlOrange
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "+${lesson.xpReward} XP", fontSize = 20.sp, fontWeight = FontWeight.Black, color = XpGold)
                    Text(text = "XP Earned", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "100%", fontSize = 20.sp, fontWeight = FontWeight.Black, color = QuestGreen)
                    Text(text = "Mastery", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onReturn,
            colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("lesson_finish_continue_btn")
        ) {
            Text("Continue Journey 🗺️", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
