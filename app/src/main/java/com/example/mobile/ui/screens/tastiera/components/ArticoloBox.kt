package com.example.mobile.ui.screens.tastiera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.mobile.data.Articolo
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.mobile.CassaViewModel
import com.example.mobile.ui.components.ContenitoreOmbreggiato


@Composable
fun ArticoloBox(
    modifier: Modifier = Modifier,
    cassaViewModel: CassaViewModel,
    widthPercent: Float = 0.9f,
    height: Dp, // esempio
    articolo: Articolo,
    onEliminaClick: (Articolo) -> Unit
) {
    ContenitoreOmbreggiato(
        modifier = modifier
            .fillMaxWidth(widthPercent)
            .height(height)
            //.clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.background),

        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // Primo blocco (Icona rossa)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tag),
                    contentDescription = "icona",
                    tint = MaterialTheme.colorScheme.onPrimary,
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

                    .background(MaterialTheme.colorScheme.background)
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
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Articolo: ${articolo.id}",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
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
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Prezzo: ${articolo.prezzo} ${articolo.valuta}",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                ),
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
                    .background(MaterialTheme.colorScheme.background),
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
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
