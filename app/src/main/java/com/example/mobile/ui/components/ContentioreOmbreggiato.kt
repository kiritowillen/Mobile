package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape

/*
* Attenzione l'oggetto che viene inserito dentro deve avere
*
*
*/
@Composable
fun ContenitoreOmbreggiato(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(15.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        // Ombra bianca a sinistra
        Box(
            modifier = Modifier
                .fillMaxHeight(0.97f)
                .width(7.dp)
                .align(Alignment.CenterStart)
                .offset(x = (-3).dp)
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = shape
                )
        )

        // Ombra bianca sopra
        Box(
            modifier = Modifier
                .height(6.5.dp)
                .fillMaxWidth(0.98f)
                .align(Alignment.TopCenter)
                .offset(y = (-3).dp)
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = shape
                )
        )

        // Ombra nera a destra
        Box(
            modifier = Modifier
                .fillMaxHeight(0.97f)
                .width(7.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 2.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = shape
                )
        )

        // Ombra nera sotto
        Box(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .height(7.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 3.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = shape
                )
        )

        // Contenuto interno (personalizzabile)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape), // Applica lo stesso arrotondamento anche al contenuto
            content = content
        )
    }
}
