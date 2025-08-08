package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobile.NavigationViewModel
import com.example.mobile.TransazioniViewModel
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navViewModel: NavigationViewModel,
    transazioniViewModel: TransazioniViewModel,
) {
    val currentScreen = navViewModel.currentScreen.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        ContenitoreOmbreggiato(
            modifier=Modifier
                .fillMaxWidth(0.97f)
                .fillMaxHeight(0.93f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
                    .background(Color.Red),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: tastiera")
                    if (currentScreen != Screen.Tastiera) {
                        navigator.navigateClearingStack(Screen.Tastiera.route)
                        navViewModel.navigateTo(Screen.Tastiera)
                    }
                }) {
                    Text("Tastiera")
                }
                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: pagamento")
                    if (currentScreen != Screen.Pagamento) {
                        navigator.navigateClearingStack(Screen.Pagamento.route)
                        navViewModel.navigateTo(Screen.Pagamento)
                    }
                }) {
                    Text("Pagamento")
                }

                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: impostazioni")
                    if (currentScreen != Screen.Impostazioni) {
                        navigator.navigateClearingStack(Screen.Impostazioni.route)
                        navViewModel.navigateTo(Screen.Impostazioni)
                        transazioniViewModel.caricaUltimeTransazioni();
                    }
                }) {
                    Text("Impostazioni")
                }
            }
        }


    }
}
