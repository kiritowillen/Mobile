package com.example.mobile.ui.theme


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

object AppColors {
    // Light mode
    val LightBackground = Color(0xFFE0DCDD)
    val LightContainer = Color(0xFFC9C4C3)
    val LightButton = Color(0xFF7AB9B8)
    val LightText = Color(0xFF0C3644)

    // Dark mode
    val DarkBackground = Color(0xFF0C3644)
    val DarkContainer = Color(0xFF3B7D81)
    val DarkButton = Color(0xFF57A7A6)
    val DarkText = Color(0xFFE0DCDD)
}

private val LightColorScheme = lightColorScheme(
    background = AppColors.LightBackground,
    primary = AppColors.LightButton,
    onPrimary = AppColors.LightText,
    surface = AppColors.LightContainer,
    onBackground = AppColors.LightText
)

private val DarkColorScheme = darkColorScheme(
    background = AppColors.DarkBackground,
    primary = AppColors.DarkButton,
    onPrimary = AppColors.DarkText,
    surface = AppColors.DarkContainer,
    onBackground = AppColors.DarkText
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}