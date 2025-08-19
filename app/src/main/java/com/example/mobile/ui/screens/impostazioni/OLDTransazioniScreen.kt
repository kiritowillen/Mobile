package com.example.mobile.ui.screens.impostazioni

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobile.TransazioniViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.ui.components.FiltroConDatePicker
import com.example.mobile.ui.screens.impostazioni.components.TransazioniList

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun OldTransazioniScreen(
    modifier: Modifier=Modifier,
    transazioniViewModel : TransazioniViewModel,
    managerScambioValuta: ManagerScambioValuta,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Riga alta 10%
            FiltroConDatePicker(
                modifier = Modifier
                .fillMaxWidth(0.98f)
                .weight(0.12f)

                .padding(horizontal = 8.dp, vertical = 4.dp),
                transazioniViewModel = transazioniViewModel,
                managerScambioValuta=managerScambioValuta,
            )

            // Colonna alta 90%
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.88f)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TransazioniList(transazioniViewModel = transazioniViewModel)
            }
        }
    }

}