package com.example.lab_week_09.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleSecondary,
    tertiary = AccentCoral,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = OnBackgroundLight,
    onSurface = OnBackgroundLight
)

@Composable
fun LAB_WEEK_09Theme(
    darkTheme: Boolean = true, // default ke dark biar beda
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = CustomTypography,
        content = content
    )
}
