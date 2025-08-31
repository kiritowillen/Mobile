package com.example.mobile.ui.screens.tastiera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobile.ViewModel.CassaViewModel
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.ui.screens.tastiera.components.Dispaly
import com.example.mobile.ui.screens.tastiera.components.TastierinoNumerico
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato

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
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        ContenitoreOmbreggiato(
            modifier = Modifier
                .fillMaxWidth(0.95f) // 95% larghezza
                .fillMaxHeight(0.98f) // 90% altezza
                //.background(MaterialTheme.colorScheme.surface)
                //.clip(RoundedCornerShape(1.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f) // 20% dell'altezza disponibile
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ){
                    Dispaly(cassaViewModel,displayViewModel, managerScambioValuta = managerScambioValuta)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight() // occupa il resto della colonna (80%)

                ){
                    TastierinoNumerico(cassaViewModel=cassaViewModel,displayViewModel=displayViewModel, managerScambioValuta = managerScambioValuta)
                }
            }
        }
    }
}