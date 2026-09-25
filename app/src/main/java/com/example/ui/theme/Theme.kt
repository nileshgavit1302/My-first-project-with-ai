package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = HtmlOrange,
    onPrimary = Color.White,
    primaryContainer = HtmlOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = CodeCyan,
    onSecondary = Color.Black,
    secondaryContainer = CodeCyanDark,
    onSecondaryContainer = Color.White,
    tertiary = XpGold,
    onTertiary = Color.Black,
    background = SlateDark,
    onBackground = TextLight,
    surface = SlateNavy,
    onSurface = TextLight,
    surfaceVariant = SlateCard,
    onSurfaceVariant = SlateGrayLight,
    outline = CardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = HtmlOrangeDark,
    onPrimary = Color.White,
    primaryContainer = HtmlOrangeLight,
    onPrimaryContainer = Color.Black,
    secondary = CodeCyanDark,
    onSecondary = Color.White,
    secondaryContainer = CodeCyan,
    onSecondaryContainer = Color.Black,
    tertiary = XpGold,
    onTertiary = Color.Black,
    background = SlateLight,
    onBackground = SlateDark,
    surface = Color.White,
    onSurface = SlateDark,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    outline = Color(0xFFCBD5E1)
)

@Composable
fun HtmlQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set default false so HTML Quest's signature gaming palette stays cohesive
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
