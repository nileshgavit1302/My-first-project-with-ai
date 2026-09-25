package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.curriculum.CurriculumData
import com.example.data.db.UserStatsEntity
import com.example.data.model.ALL_BADGES
import com.example.data.model.BadgeDefinition
import com.example.data.model.LevelSystem
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.StreakFire
import com.example.ui.theme.XpGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    userStats: UserStatsEntity?,
    completedLessonIds: Set<String>,
    completedProjectIds: Set<String>,
    unlockedBadgeIds: Set<String>,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var selectedBadgeForDetail by remember { mutableStateOf<BadgeDefinition?>(null) }
    var showResetDialog by remember { mutableStateOf(false) }

    val currentXp = userStats?.xp ?: 0
    val level = LevelSystem.getLevelForXp(currentXp)
    val nextLevel = LevelSystem.getNextLevel(currentXp)
    val levelProgress = LevelSystem.getLevelProgress(currentXp)

    val totalLessons = CurriculumData.units.sumOf { it.lessons.size }
    val completedUnitsCount = CurriculumData.units.count { unit ->
        unit.lessons.all { it.id in completedLessonIds }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // User Identity Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_lion_mascot),
                    contentDescription = "Profile Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(3.dp, HtmlOrange, CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userStats?.userName ?: "HTML Adventurer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = HtmlOrange.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${level.rankBadge} ${level.title} (Level ${level.levelNumber})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = HtmlOrange,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // XP to next level bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$currentXp Total XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = XpGold
                    )
                    Text(
                        text = if (nextLevel != null) "${nextLevel.minXp - currentXp} XP to ${nextLevel.title}" else "Master Level Reached!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { levelProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = XpGold,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        // Stats Matrix Grid
        Text(
            text = "Learning Statistics",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                title = "Current Streak",
                value = "${userStats?.currentStreak ?: 1} Days",
                icon = Icons.Default.LocalFireDepartment,
                iconColor = StreakFire,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Longest Streak",
                value = "${userStats?.longestStreak ?: 1} Days",
                icon = Icons.Default.EmojiEvents,
                iconColor = XpGold,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                title = "Lessons Done",
                value = "${completedLessonIds.size} / $totalLessons",
                icon = Icons.Default.School,
                iconColor = QuestGreen,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Projects Built",
                value = "${completedProjectIds.size} / 7",
                icon = Icons.Default.Star,
                iconColor = HtmlOrange,
                modifier = Modifier.weight(1f)
            )
        }

        // Badges & Achievements
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Badges & Achievements (${unlockedBadgeIds.size}/${ALL_BADGES.size})",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            FlowRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ALL_BADGES.forEach { badge ->
                    val isUnlocked = badge.id in unlockedBadgeIds
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(74.dp)
                            .clickable { selectedBadgeForDetail = badge }
                            .testTag("badge_${badge.id}")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isUnlocked) XpGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isUnlocked) XpGold else Color.Transparent
                            ),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isUnlocked) badge.iconEmoji else "🔒",
                                    fontSize = 24.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = badge.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            lineHeight = 14.sp,
                            color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Reset Settings Option
        OutlinedButton(
            onClick = { showResetDialog = true },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reset_progress_btn")
        ) {
            Text("Reset Learning Journey", color = MaterialTheme.colorScheme.outline)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Badge Detail Dialog
    if (selectedBadgeForDetail != null) {
        val badge = selectedBadgeForDetail!!
        val isUnlocked = badge.id in unlockedBadgeIds

        AlertDialog(
            onDismissRequest = { selectedBadgeForDetail = null },
            icon = {
                Text(text = if (isUnlocked) badge.iconEmoji else "🔒", fontSize = 42.sp)
            },
            title = {
                Text(
                    text = badge.title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = badge.description,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (isUnlocked) "Status: Unlocked! 🏆" else "Status: In Progress",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) QuestGreen else HtmlOrange
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedBadgeForDetail = null }) {
                    Text("Close")
                }
            }
        )
    }

    // Reset Progress Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Progress?") },
            text = {
                Text("This will reset your completed lessons, projects, and XP back to the start so you can experience the quest again.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        onResetProgress()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange)
                ) {
                    Text("Yes, Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = iconColor.copy(alpha = 0.15f),
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
