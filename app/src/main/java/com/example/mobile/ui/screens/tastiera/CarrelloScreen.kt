package com.example.mobile.ui.screens.tastiera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.mobile.ViewModel.CassaViewModel
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ui.screens.tastiera.components.ArticoloBox

@Composable
fun CarrelloScreen(
    modifier: Modifier = Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    managerScambioValuta: ManagerScambioValuta
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val spacing = screenHeightDp * 0.01f // 1% dello schermo

    val prodotti by cassaViewModel.prodotti.collectAsState()

    LazyColumn( //lista scorrevole contenete la lista di oggetti
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(prodotti, key = { it.id }) { articolo ->  //ad ogni oggetto passo solo le funzioni dei vari viewmodel
            ArticoloBox(
                height = screenHeightDp * 0.08f,
                articolo = articolo,
                onEliminaClick = {
                    cassaViewModel.rimuoviProdotto(it,managerScambioValuta.converti(it.prezzo,it.valuta,"SATS"))
                    displayViewModel.sottraiDaTotale(managerScambioValuta.converti(it.prezzo,it.valuta,managerScambioValuta.valutaSelezionata.value))
                },
                cassaViewModel = cassaViewModel

            )
        }
    }
}
