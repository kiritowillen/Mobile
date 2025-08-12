package com.example.mobile.ui.screens.impostazioni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mobile.TransazioniViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

import androidx.compose.ui.unit.dp
import com.example.mobile.FirebaseService
import com.example.mobile.generaQrCode
import com.example.mobile.ui.components.FiltroConDatePicker
import com.example.mobile.ui.components.TransazioniList

@Composable
fun TransazioniScreen(
    modifier: Modifier=Modifier,
    transazioniViewModel : TransazioniViewModel,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Riga alta 10%
            FiltroConDatePicker(
                modifier = Modifier
                .fillMaxWidth()
                .weight(0.07f)
                .background(Color.Red)
                .padding(horizontal = 8.dp, vertical = 4.dp),
                transazioniViewModel = transazioniViewModel,
            )

            // Colonna alta 90%
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.93f)
                    .background(Color.Yellow)
            ) {
                TransazioniList(transazioniViewModel = transazioniViewModel)
            }
        }
    }

}