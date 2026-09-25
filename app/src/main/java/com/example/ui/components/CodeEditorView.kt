package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HtmlOrange
import com.example.ui.theme.QuestGreen
import com.example.ui.theme.SlateNavy

@Composable
fun CodeEditorView(
    code: String,
    onCodeChange: (String) -> Unit,
    onRun: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    initialStarterCode: String = "",
    allowQuickTags: Boolean = true
) {
    val context = LocalContext.current
    val lineCount = remember(code) { code.lines().size.coerceAtLeast(1) }

    val quickSnippets = listOf(
        "<h1></h1>", "<p></p>", "<a href=\"\"></a>", "<img src=\"\" alt=\"\">",
        "<ul>\n  <li></li>\n</ul>", "<ol>\n  <li></li>\n</ol>",
        "<div></div>", "class=\"\"", "id=\"\"", "<!-- -->"
    )

    Column(
        modifier = modifier
            .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "HTML Editor",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$lineCount lines",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("HTML Code", code))
                        Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(32.dp).testTag("copy_code_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy code",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onReset,
                    modifier = Modifier.size(32.dp).testTag("reset_code_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset code",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = { onCodeChange("") },
                    modifier = Modifier.size(32.dp).testTag("clear_code_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear code",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Button(
                    onClick = onRun,
                    colors = ButtonDefaults.buttonColors(containerColor = QuestGreen),
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("run_code_button"),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Run",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Run", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Quick Tag Insert Toolbar for mobile convenience
        if (allowQuickTags) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                quickSnippets.forEach { snippet ->
                    Surface(
                        onClick = {
                            val newCode = if (code.isEmpty()) snippet else "$code\n$snippet"
                            onCodeChange(newCode)
                        },
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF1E293B),
                        modifier = Modifier.height(26.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = snippet.lines().first(),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Code Area with Line Numbers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF030712), RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            // Line numbers column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                for (i in 1..lineCount) {
                    Text(
                        text = "$i",
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFF475569),
                        lineHeight = 20.sp
                    )
                }
            }

            // Code Input Field
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                if (code.isEmpty()) {
                    Text(
                        text = "<!-- Write HTML here... -->",
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFF64748B),
                        lineHeight = 20.sp
                    )
                }
                BasicTextField(
                    value = code,
                    onValueChange = onCodeChange,
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        color = Color(0xFFF8FAFC),
                        lineHeight = 20.sp
                    ),
                    cursorBrush = SolidColor(HtmlOrange),
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("code_editor_input")
                )
            }
        }
    }
}
