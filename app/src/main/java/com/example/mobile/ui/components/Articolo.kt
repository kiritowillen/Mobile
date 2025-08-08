package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.example.mobile.data.Articolo
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.BoxWithConstraints
import com.example.mobile.CassaViewModel


@Composable
fun ArticoloBox(
    modifier: Modifier = Modifier,
    cassaViewModel: CassaViewModel,
    widthPercent: Float = 0.9f,
    height: Dp, // esempio
    articolo: Articolo,
    onEliminaClick: (Articolo) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(widthPercent)
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // Primo blocco (Icona rossa)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "icona",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.6f)
                )
            }

            // Secondo blocco (Testi gialli)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.60f)
                    .background(Color.Yellow)
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val density = LocalDensity.current
                    val totalHeight = maxHeight

                    val bigFontSize = (totalHeight * 0.30f).value.sp
                    val smallFontSize = (totalHeight * 0.15f).value.sp


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Parte alta (2/3 altezza)
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Articolo: ${articolo.id}",
                                color = Color.Black,
                                fontSize = bigFontSize
                            )
                        }

                        // Parte bassa (1/3 altezza)
                        Box(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Prezzo: ${articolo.prezzo} ${articolo.valuta}",
                                color = Color.Black,
                                fontSize = smallFontSize
                            )
                        }
                    }
                }
            }



            // Terzo blocco (Icona Elimina)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
                    .background(Color.Magenta),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { onEliminaClick(articolo) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.8f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bin),
                        contentDescription = "Elimina",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
