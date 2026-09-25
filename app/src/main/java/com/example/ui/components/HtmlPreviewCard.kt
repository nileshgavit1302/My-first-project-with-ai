package com.example.ui.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlPreviewCard(
    htmlContent: String,
    modifier: Modifier = Modifier,
    enableJs: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    val context = LocalContext.current

    val wrappedHtml = remember(htmlContent) {
        """
        <!DOCTYPE html>
        <html>
        <head>
          <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
          <style>
            body {
              font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
              padding: 16px;
              margin: 0;
              color: #1e293b;
              background-color: #ffffff;
              line-height: 1.5;
              word-break: break-word;
            }
            h1, h2, h3, h4, h5, h6 { color: #0f172a; margin-top: 0.5em; margin-bottom: 0.5em; }
            table { border-collapse: collapse; width: 100%; margin: 12px 0; }
            th, td { border: 1px solid #cbd5e1; padding: 8px 12px; text-align: left; }
            th { background-color: #f1f5f9; font-weight: 600; }
            a { color: #2563eb; text-decoration: underline; }
            input, textarea, select, button {
              font-family: inherit;
              padding: 8px 12px;
              border-radius: 6px;
              border: 1px solid #94a3b8;
              margin: 4px 0;
              box-sizing: border-box;
            }
            button {
              background: #f06529;
              color: white;
              border: none;
              cursor: pointer;
              font-weight: 600;
            }
            img { max-width: 100%; height: auto; border-radius: 4px; }
            pre, code { background: #f1f5f9; padding: 2px 6px; border-radius: 4px; font-family: monospace; }
          </style>
        </head>
        <body>
          $htmlContent
        </body>
        </html>
        """.trimIndent()
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Browser Simulation Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE2E8F0))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Window dots
                Box(modifier = Modifier.size(10.dp).background(Color(0xFFEF4444), CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(10.dp).background(Color(0xFFF59E0B), CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(10.dp).background(Color(0xFF10B981), CircleShape))

                Spacer(modifier = Modifier.width(12.dp))

                // Simulated URL bar
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "preview://sandbox.local",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload Preview",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF475569)
                    )
                }
            }

            // Sandboxed WebView
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        // Security sandbox configuration
                        settings.apply {
                            javaScriptEnabled = enableJs
                            allowFileAccess = false
                            allowContentAccess = false
                            allowFileAccessFromFileURLs = false
                            allowUniversalAccessFromFileURLs = false
                            cacheMode = WebSettings.LOAD_NO_CACHE
                            domStorageEnabled = false
                        }
                        setBackgroundColor(android.graphics.Color.WHITE)
                        loadDataWithBaseURL(null, wrappedHtml, "text/html", "UTF-8", null)
                    }
                },
                update = { webView ->
                    webView.loadDataWithBaseURL(null, wrappedHtml, "text/html", "UTF-8", null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}
