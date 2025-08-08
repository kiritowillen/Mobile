package com.example.mobile.ui.screens.tastiera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.mobile.CassaViewModel
import androidx.compose.ui.draw.clip
import com.example.mobile.DisplayViewModel
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.ui.components.ArticoloBox

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
            .background(Color.Blue),
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
