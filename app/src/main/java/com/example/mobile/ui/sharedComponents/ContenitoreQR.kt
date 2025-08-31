package com.example.mobile.ui.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mobile.ServiceClasses.FirebaseService


@Composable
fun ContenitoreQR(
    modifier: Modifier,
    firebaseService: FirebaseService,
    walletSelezionato: MutableState<String>
) {

    val bitcoinBitmap by firebaseService.bitcoinBitmap.collectAsState()
    val lightningBitmap by firebaseService.lightningBitmap.collectAsState()

    Box(
    modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.weight(0.02f))

            ContenitoreOmbreggiato(
                modifier = Modifier
                    .weight(0.83f)
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterHorizontally),

                ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val bitmap = when (walletSelezionato.value) {
                        "bitcoin" -> bitcoinBitmap
                        "lightning" -> lightningBitmap
                        else -> null
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(), // <-- conversione
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .fillMaxHeight(0.90f)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text("QR in caricamento...")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                CopyAndShareTexts(walletSelezionato=walletSelezionato)
            }
        }
    }
}