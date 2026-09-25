package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

enum class FeedbackState {
    IDLE,
    CORRECT,
    INCORRECT
}

@Composable
fun FeedbackBanner(
    state: FeedbackState,
    explanation: String,
    solution: String,
    xpReward: Int,
    onContinue: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = state != FeedbackState.IDLE,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        modifier = modifier
    ) {
        val isCorrect = state == FeedbackState.CORRECT

        val bgColor = if (isCorrect) Color(0xFF064E3B) else Color(0xFF451A03)
        val borderColor = if (isCorrect) QuestGreen else HtmlOrange
        val titleColor = if (isCorrect) Color(0xFF6EE7B7) else Color(0xFFFDBA74)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, borderColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = bgColor,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = borderColor.copy(alpha = 0.2f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Lightbulb,
                                contentDescription = if (isCorrect) "Correct" else "Hint",
                                tint = if (isCorrect) QuestGreen else HtmlOrange,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isCorrect) "Correct! Excellent work!" else "Not quite! That's how we learn.",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = titleColor
                            )
                            if (isCorrect && xpReward > 0) {
                                Text(
                                    text = "+$xpReward XP earned ⭐",
                                    fontSize = 13.sp,
                                    color = XpGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                if (!isCorrect) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = explanation.ifEmpty { "Mistakes are part of learning — never punish the learner for making one!" },
                        fontSize = 14.sp,
                        color = Color(0xFFFED7AA),
                        lineHeight = 20.sp
                    )
                    if (solution.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Concept: $solution",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isCorrect) {
                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = QuestGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("feedback_continue_btn")
                    ) {
                        Text(
                            text = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("feedback_retry_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Retry",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Try Again (Unlimited)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
