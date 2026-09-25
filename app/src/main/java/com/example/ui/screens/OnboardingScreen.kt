package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CodeCyan
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

@Composable
fun OnboardingScreen(
    onFinishOnboarding: (experienceLevel: String, dailyGoalMinutes: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(1) }
    var selectedLevel by remember { mutableStateOf("Complete Beginner") }
    var selectedGoal by remember { mutableIntStateOf(10) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Step Dots Progress Indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                for (i in 1..5) {
                    Box(
                        modifier = Modifier
                            .size(if (i == step) 24.dp else 10.dp, 10.dp)
                            .background(
                                color = if (i == step) HtmlOrange else if (i < step) QuestGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Step Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (step) {
                    1 -> StepWelcome()
                    2 -> StepWhatIsHtml()
                    3 -> StepChooseLevel(selectedLevel) { selectedLevel = it }
                    4 -> StepChooseGoal(selectedGoal) { selectedGoal = it }
                    5 -> StepStartFirstLesson(selectedLevel, selectedGoal)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Navigation Buttons
            Button(
                onClick = {
                    if (step < 5) {
                        step++
                    } else {
                        onFinishOnboarding(selectedLevel, selectedGoal)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_next_button")
            ) {
                Text(
                    text = if (step == 5) "Start Lesson 1 🚀" else "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun StepWelcome() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // App Mascot / Hero
        Image(
            painter = painterResource(id = R.drawable.ic_lion_mascot),
            contentDescription = "HTML Quest Lion Mascot",
            modifier = Modifier
                .size(160.dp, 160.dp)
                .clip(RoundedCornerShape(32.dp))
                .border(3.dp, HtmlOrange, RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to HTML Quest!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Learn HTML. Build. Level Up.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HtmlOrange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Core Philosophy Card
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🌱 Unlimited Learning Promise",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = QuestGreen
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mistakes are part of learning — never punish the learner for making one. No hearts, no lives, no cooldown timers, ever.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StepWhatIsHtml() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Code,
            contentDescription = null,
            tint = CodeCyan,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What is HTML?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "HTML (HyperText Markup Language) is the universal skeleton of the web. Every webpage on Earth — from Wikipedia to Google — uses HTML to organize text, buttons, images, and links.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            color = Color(0xFF0F172A),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "<h1>Hello World</h1>",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38BDF8)
                )
                Text(
                    text = "<p>I am learning to code!</p>",
                    fontSize = 14.sp,
                    color = Color(0xFF4ADE80)
                )
            }
        }
    }
}

@Composable
private fun StepChooseLevel(selected: String, onSelect: (String) -> Unit) {
    val levels = listOf(
        "Complete Beginner" to "Never written HTML before. Start from ground zero!",
        "I know a little HTML" to "Familiar with some basic tags like <p> and <a>.",
        "Intermediate" to "Looking to master Semantics, Forms, and build projects!"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Choose your experience",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "We will tailor your learning path.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            levels.forEach { (title, subtitle) ->
                val isSelected = selected == title
                Surface(
                    onClick = { onSelect(title) },
                    shape = RoundedCornerShape(16.dp),
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (isSelected) HtmlOrange else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = subtitle,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = HtmlOrange
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepChooseGoal(selectedMinutes: Int, onSelect: (Int) -> Unit) {
    val goals = listOf(
        5 to "Casual (5 min/day)",
        10 to "Regular (10 min/day)",
        15 to "Serious (15 min/day)",
        20 to "Intense (20 min/day)"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Timer,
            contentDescription = null,
            tint = XpGold,
            modifier = Modifier.size(54.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Set a Daily Goal",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Goals are purely motivational. Never punished if missed!",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            goals.forEach { (mins, label) ->
                val isSelected = selectedMinutes == mins
                Surface(
                    onClick = { onSelect(mins) },
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) XpGold.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(
                        2.dp,
                        if (isSelected) XpGold else Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = XpGold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepStartFirstLesson(level: String, goal: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = CircleShape,
            color = QuestGreen.copy(alpha = 0.2f),
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = QuestGreen,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "You are all set!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your adventure begins with Unit 1: HTML Basics. Earn XP, collect badges, run code, and build your developer portfolio!",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
