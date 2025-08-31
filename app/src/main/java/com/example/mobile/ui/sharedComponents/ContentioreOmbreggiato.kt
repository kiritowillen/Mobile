package com.example.mobile.ui.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip

/*
* Attenzione l'oggetto che viene inserito dentro deve avere
*
*
*/
@Composable
fun ContenitoreOmbreggiato(
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(15.dp),
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,

) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        // Ombra bianca a sinistra
        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .width(8.dp)
                .align(Alignment.CenterStart)
                .offset(x = (-4).dp)
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = shape
                )
        )

        // Ombra bianca sopra
        Box(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth(1f)
                .align(Alignment.TopCenter)
                .offset(y = (-4).dp)
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = shape
                )
        )

        // Ombra nera a destra
        Box(
            modifier = Modifier
                .fillMaxHeight(0.99f)
                .width(8.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 3.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = shape
                )
        )

        // Ombra nera sotto
        Box(
            modifier = Modifier
                .fillMaxWidth(0.99f)
                .height(8.dp)
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
            contentAlignment=contentAlignment,
            content = content
        )
    }
}
