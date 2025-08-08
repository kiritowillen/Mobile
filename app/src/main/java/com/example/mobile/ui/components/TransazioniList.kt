package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.layout.onSizeChanged
import com.example.mobile.TransazioniViewModel
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.ui.unit.dp


@Composable
fun TransazioniList(
    transazioniViewModel: TransazioniViewModel,
) {
    val listaTransazioni by transazioniViewModel.listaTransazioni

    var listHeightPx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(Color.Green)
        ) {
            val itemHeightDp: Dp = if (listHeightPx > 0) {
                with(density) { (listHeightPx / 2).toDp() }
            } else {
                80.dp
            }
            items(listaTransazioni) { transazione ->

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally  // centro orizzontale
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.97f)  // 97% della larghezza
                            .padding(vertical = 4.dp)  // spazio sopra e sotto
                            .height(itemHeightDp)
                            .background(Color(0xFFFFA500))
                    ) {
                        Text(
                            text = transazione.toString(),
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }
            }

        }
    }

}
