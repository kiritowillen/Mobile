package com.example.mobile.ui.screens.impostazioni.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import com.example.mobile.ViewModel.TransazioniViewModel
import androidx.compose.material3.MaterialTheme
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato


@Composable
fun TransazioniList(
    transazioniViewModel: TransazioniViewModel,
) {
    val listaTransazioni by transazioniViewModel.listaTransazioni

    var listHeightPx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    ContenitoreOmbreggiato(
        modifier = Modifier
            .fillMaxHeight(0.98f)
            .fillMaxWidth(0.95f)
            //.background(Color.Gray),
        ,
        //contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val itemHeightDp: Dp = if (listHeightPx > 0) {
                with(density) { (listHeightPx / 2).toDp() }
            } else {
                80.dp
            }
            items(listaTransazioni) { transazione ->

                Spacer(modifier = Modifier.height(16.dp))

                RigaTransazione(transazione = transazione)


            }

        }
    }

}
