package com.example.mobile.ui.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContenitoreTondoOmbreggiato(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.aspectRatio(1f), // Mantiene il cerchio perfetto
        contentAlignment = contentAlignment
    ) {
        // Ombra chiara (in basso a sinistra)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = (-2).dp, y = (-2).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.5f))
        )

        // Ombra scura (in alto a destra)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 2.dp, y = 2.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // Cerchio principale con contenuto sopra
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(Color(0xFFE0DCDD)), // colore di sfondo del cerchio
            contentAlignment = contentAlignment,
            content = content
        )
    }
}
