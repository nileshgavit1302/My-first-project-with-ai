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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.ProjectsData
import com.example.data.model.HtmlProject
import com.example.ui.components.CodeEditorView
import com.example.ui.components.HtmlPreviewCard
import com.example.ui.theme.CodeCyan
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.XpGold

@Composable
fun ProjectsScreen(
    completedProjectIds: Set<String>,
    onCompleteProject: (String, String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedProject by remember { mutableStateOf<HtmlProject?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Guided Projects, 1: Free Sandbox

    if (selectedProject != null) {
        ProjectEditorView(
            project = selectedProject!!,
            isCompleted = selectedProject!!.id in completedProjectIds,
            onBack = { selectedProject = null },
            onProjectValidated = { project, code ->
                onCompleteProject(project.id, code, project.xpReward)
            }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Project Studio 🛠️",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Build real websites, test code & earn mastery XP",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs: Guided Projects vs Free Sandbox
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("7 Guided Projects", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Free HTML Sandbox", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTab == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProjectsData.allProjects.forEach { project ->
                        val isDone = project.id in completedProjectIds
                        Surface(
                            onClick = { selectedProject = project },
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isDone) QuestGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("project_card_${project.id}")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = project.iconEmoji, fontSize = 28.sp)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = project.title,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = project.subtitle,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    if (isDone) {
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = QuestGreen.copy(alpha = 0.15f)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Completed",
                                                    tint = QuestGreen,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Completed",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = QuestGreen
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = project.description,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = project.difficulty,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Star, contentDescription = null, tint = XpGold, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "+${project.xpReward} XP",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = XpGold
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Button(
                                            onClick = { selectedProject = project },
                                            colors = ButtonDefaults.buttonColors(containerColor = HtmlOrange),
                                            shape = RoundedCornerShape(10.dp),
                                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text(if (isDone) "Reopen" else "Build", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                FreeSandboxView()
            }
        }
    }
}

@Composable
private fun ProjectEditorView(
    project: HtmlProject,
    isCompleted: Boolean,
    onBack: () -> Unit,
    onProjectValidated: (HtmlProject, String) -> Unit
) {
    var userCode by remember(project.id) { mutableStateOf(project.starterCode) }
    var previewHtml by remember(project.id) { mutableStateOf(project.starterCode) }
    var validationResults by remember(project.id) { mutableStateOf<List<Pair<String, Boolean>>?>(null) }
    var passedAll by remember(project.id) { mutableStateOf(isCompleted) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Back Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = project.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${project.iconEmoji} ${project.difficulty} Challenge",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                color = XpGold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "+${project.xpReward} XP",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = XpGold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Requirements Checklist
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Project Objectives & Checklist:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val checks = validationResults ?: project.requirements.map { it to false }
                    checks.forEach { (req, passed) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (passed) Icons.Default.CheckCircle else Icons.Default.Code,
                                contentDescription = null,
                                tint = if (passed) QuestGreen else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = req,
                                fontSize = 12.sp,
                                color = if (passed) QuestGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (passed) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Code Editor
            CodeEditorView(
                code = userCode,
                onCodeChange = { userCode = it },
                onRun = { previewHtml = userCode },
                onReset = {
                    userCode = project.starterCode
                    previewHtml = project.starterCode
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            // Live Preview Card
            Text(
                text = "Live Sandboxed Preview:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HtmlPreviewCard(
                htmlContent = previewHtml,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Validate & Finish Button
            Button(
                onClick = {
                    previewHtml = userCode
                    val (allOk, checklist) = project.evaluate(userCode)
                    validationResults = checklist
                    passedAll = allOk
                    if (allOk) {
                        onProjectValidated(project, userCode)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (passedAll) QuestGreen else HtmlOrange
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("verify_project_btn")
            ) {
                Text(
                    text = if (passedAll) "Project Completed! 🎉" else "Verify & Complete Project",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun FreeSandboxView() {
    var sandboxCode by remember {
        mutableStateOf(
            """<!DOCTYPE html>
<html>
<head>
  <title>Free Sandbox</title>
</head>
<body>
  <h1>HTML Playground</h1>
  <p>Experiment with anything you've learned! No rules, pure creation.</p>
  <button onclick="alert('Hello')">Click Me</button>
</body>
</html>"""
        )
    }
    var previewCode by remember { mutableStateOf(sandboxCode) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CodeEditorView(
            code = sandboxCode,
            onCodeChange = { sandboxCode = it },
            onRun = { previewCode = sandboxCode },
            onReset = {
                sandboxCode = "<h1>Hello Playground</h1>\n<p>Write your HTML here!</p>"
                previewCode = sandboxCode
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )

        Text(
            text = "Rendered Output:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HtmlPreviewCard(
            htmlContent = previewCode,
            enableJs = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )
    }
}
