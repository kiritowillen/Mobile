package com.example.mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    displayLarge = TextStyle( // puoi usare un nome predefinito di Material3 o crearne uno tuo
        fontSize = 52.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Unspecified // Lascia Unspecified così eredita da MaterialTheme.colorScheme
    ),
    titleLarge = TextStyle(
        fontSize = Dimens.titleText,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold, // grassetto
        fontSize = 200.sp
    ),
    labelSmall = TextStyle(
        fontSize = Dimens.subtitleText
    )
)
