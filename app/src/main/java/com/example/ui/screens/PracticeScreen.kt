package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.PracticeQuestionsData
import com.example.data.db.WeakTopicEntity
import com.example.data.model.Lesson
import com.example.ui.theme.CodeCyan
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.MistakeHelp
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PracticeScreen(
    weakTopics: List<WeakTopicEntity>,
    onStartPracticeSession: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = CodeCyan.copy(alpha = 0.2f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = "Practice",
                        tint = CodeCyan,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Unlimited Practice",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Free, unlimited practice by topic with zero penalties. Learn, make mistakes, retry, and conquer every concept!",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Section: Review Weak Topics (Feature 18)
        if (weakTopics.isNotEmpty()) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MistakeHelp.copy(alpha = 0.1f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MistakeHelp.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth().testTag("weak_topics_section")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Review",
                                tint = MistakeHelp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Your Practice Weak Topics",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = MistakeHelp,
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${weakTopics.size}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Concepts you stumbled on during lessons. Practice them now to turn weaknesses into mastery!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        weakTopics.forEach { topic ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = topic.topicName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "${topic.mistakeCount} review opportunities",
                                            fontSize = 11.sp,
                                            color = MistakeHelp
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            val questions = PracticeQuestionsData.getQuestionsForTopic(topic.topicName)
                                                .ifEmpty { PracticeQuestionsData.getAllQuestions().take(3) }
                                            val practiceLesson = Lesson(
                                                id = "weak_${topic.topicId}",
                                                unitId = 0,
                                                title = "Review: ${topic.topicName}",
                                                subtitle = "Targeted Weak Topic Practice",
                                                exercises = questions,
                                                xpReward = 30
                                            )
                                            onStartPracticeSession(practiceLesson)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MistakeHelp),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(34.dp).testTag("practice_weak_${topic.topicId}")
                                    ) {
                                        Text("Practice", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // All strong card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = QuestGreen.copy(alpha = 0.1f),
                border = androidx.compose.foundation.BorderStroke(1.dp, QuestGreen.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = QuestGreen)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "No weak topics recorded yet! Keep exploring lessons or practice any topic below.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Section: Practice by Topic
        Column {
            Text(
                text = "Select a Topic to Practice",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PracticeQuestionsData.topics.forEach { topic ->
                    val questions = PracticeQuestionsData.getQuestionsForTopic(topic)
                    Surface(
                        onClick = {
                            val practiceLesson = Lesson(
                                id = "practice_$topic",
                                unitId = 0,
                                title = "Practice: $topic",
                                subtitle = "Unlimited Question Drill",
                                exercises = questions,
                                xpReward = 25
                            )
                            onStartPracticeSession(practiceLesson)
                        },
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth().testTag("topic_practice_$topic")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = HtmlOrange.copy(alpha = 0.15f),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "🎯", fontSize = 16.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = topic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${questions.size} exercises available",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "+25 XP",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = XpGold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Start",
                                    tint = HtmlOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
