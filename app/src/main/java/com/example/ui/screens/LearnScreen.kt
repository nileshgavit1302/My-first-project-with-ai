package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.CurriculumData
import com.example.data.model.LearningUnit
import com.example.data.model.Lesson
import com.example.data.model.UnitStatus
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

@Composable
fun LearnScreen(
    completedLessonIds: Set<String>,
    onSelectLesson: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedUnitId by remember {
        val firstIncompleteUnit = CurriculumData.units.firstOrNull { unit ->
            unit.lessons.any { it.id !in completedLessonIds }
        }?.id ?: 1
        mutableStateOf<Int?>(firstIncompleteUnit)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = "HTML Learning Path 🗺️",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "10 comprehensive units from beginner to web architect. Unlimited practice on every unlocked lesson!",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        itemsIndexed(CurriculumData.units) { index, unit ->
            // Determine unit status
            val allCompleted = unit.lessons.all { it.id in completedLessonIds }
            val anyCompleted = unit.lessons.any { it.id in completedLessonIds }

            // Unit is unlocked if unit 1 or if previous unit has at least one completed lesson
            val isUnlocked = if (unit.id == 1) {
                true
            } else {
                val prevUnit = CurriculumData.getUnitById(unit.id - 1)
                prevUnit?.lessons?.any { it.id in completedLessonIds } ?: true
            }

            val status = when {
                allCompleted -> UnitStatus.COMPLETED
                anyCompleted -> UnitStatus.IN_PROGRESS
                isUnlocked -> UnitStatus.AVAILABLE
                else -> UnitStatus.LOCKED
            }

            UnitCard(
                unit = unit,
                status = status,
                completedLessonIds = completedLessonIds,
                isExpanded = expandedUnitId == unit.id,
                onToggleExpand = {
                    if (isUnlocked) {
                        expandedUnitId = if (expandedUnitId == unit.id) null else unit.id
                    }
                },
                onSelectLesson = onSelectLesson,
                isLast = index == CurriculumData.units.size - 1
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun UnitCard(
    unit: LearningUnit,
    status: UnitStatus,
    completedLessonIds: Set<String>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onSelectLesson: (Lesson) -> Unit,
    isLast: Boolean
) {
    val completedCount = unit.lessons.count { it.id in completedLessonIds }
    val progress = (completedCount.toFloat() / unit.lessons.size).coerceIn(0f, 1f)

    val isLocked = status == UnitStatus.LOCKED

    val borderColor = when (status) {
        UnitStatus.COMPLETED -> QuestGreen
        UnitStatus.IN_PROGRESS -> HtmlOrange
        UnitStatus.AVAILABLE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        UnitStatus.LOCKED -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = if (isLocked) MaterialTheme.colorScheme.surface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isLocked) { onToggleExpand() }
                .testTag("unit_card_${unit.id}")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Status Badge Icon
                    Surface(
                        shape = CircleShape,
                        color = when (status) {
                            UnitStatus.COMPLETED -> QuestGreen.copy(alpha = 0.2f)
                            UnitStatus.IN_PROGRESS -> HtmlOrange.copy(alpha = 0.2f)
                            UnitStatus.AVAILABLE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            UnitStatus.LOCKED -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            when (status) {
                                UnitStatus.COMPLETED -> Icon(Icons.Default.Check, contentDescription = "Completed", tint = QuestGreen)
                                UnitStatus.LOCKED -> Icon(Icons.Default.Lock, contentDescription = "Locked", tint = MaterialTheme.colorScheme.outline)
                                else -> Text(text = "${unit.id}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = HtmlOrange)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = unit.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isLocked) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = unit.subtitle,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (!isLocked) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (!isLocked) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedCount of ${unit.lessons.size} lessons completed",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (status == UnitStatus.COMPLETED) {
                            Text(
                                text = "Unit Mastered ⭐",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuestGreen
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (status == UnitStatus.COMPLETED) QuestGreen else HtmlOrange,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                // Expanded Lessons List
                AnimatedVisibility(visible = isExpanded && !isLocked) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        unit.lessons.forEachIndexed { lIndex, lesson ->
                            val isCompleted = lesson.id in completedLessonIds
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isCompleted) QuestGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isCompleted) QuestGreen.copy(alpha = 0.3f) else Color.Transparent
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelectLesson(lesson) }
                                    .testTag("lesson_item_${lesson.id}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = if (isCompleted) QuestGreen else HtmlOrange,
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                if (isCompleted) {
                                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                                } else {
                                                    Text(text = "${lIndex + 1}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = lesson.title,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = lesson.subtitle,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    Button(
                                        onClick = { onSelectLesson(lesson) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isCompleted) MaterialTheme.colorScheme.surfaceVariant else HtmlOrange
                                        ),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(34.dp)
                                    ) {
                                        Text(
                                            text = if (isCompleted) "Practice" else "Start",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isCompleted) MaterialTheme.colorScheme.primary else Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Connecting Path Line
        if (!isLast) {
            Box(
                modifier = Modifier
                    .padding(start = 38.dp)
                    .width(4.dp)
                    .height(20.dp)
                    .background(
                        color = if (status == UnitStatus.COMPLETED) QuestGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}
