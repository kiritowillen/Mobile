package com.example.mobile.ui.screens.tastiera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.ui.components.Dispaly
import com.example.mobile.ui.components.TastierinoNumerico
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.mobile.ManagerScambioValuta

@Composable
fun TastieraScreen(
    modifier: Modifier=Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    managerScambioValuta: ManagerScambioValuta,
)
{
    // Colleziona gli stati per avere aggiornamenti automatici

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f) // 95% larghezza
                .fillMaxHeight(0.95f) // 90% altezza
                .background(Color.Red)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f) // 20% dell'altezza disponibile
                        .background(Color.Yellow),
                    contentAlignment = Alignment.Center
                ){
                    Dispaly(cassaViewModel,displayViewModel, managerScambioValuta = managerScambioValuta)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight() // occupa il resto della colonna (80%)
                        .background(Color.Magenta)
                ){
                    TastierinoNumerico(cassaViewModel=cassaViewModel,displayViewModel=displayViewModel, managerScambioValuta = managerScambioValuta)
                }
            }
        }
    }
}