package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.example.mobile.NavigationViewModel
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navViewModel: NavigationViewModel,
    aggiornaTotale: (Double) -> Unit,
    eliminaTutto: () -> Unit,
) {

    val IndietroButton = navViewModel.currentScreen.collectAsState(Screen.Tastiera).value.route in listOf(
        Screen.CambioPin.route,
        Screen.Transazioni.route,
        Screen.Trasferimento.route,
        Screen.Carrello.route
    )
    val CarrelloButton = navViewModel.currentScreen.collectAsState(Screen.Tastiera).value.route in listOf(
        Screen.Tastiera.route,
    )
    val DeleteAllButton = navViewModel.currentScreen.collectAsState(Screen.Tastiera).value.route in listOf(
        Screen.Carrello.route,
        Screen.Tastiera.route,
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            //.padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color.Green)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pulsante sinistro, visibile solo se la condizione è vera
            Box(
                modifier = Modifier
                    .width(100.dp) // Fissa la larghezza a una percentuale, per esempio 80dp per il pulsante sinistro
            ) {
                if (IndietroButton) {
                    BackButton(navigator)
                }
                if (CarrelloButton) {
                    CartButton(navigator)
                }

            }

            // Il Box per il testo centrale, sempre centrato
            Box(
                modifier = Modifier
                    .weight(1f) // La zona centrale occupa tutto lo spazio rimanente
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "La mia App", color = Color.White, fontSize = 18.sp)
            }

            // Pulsante destro,
            Box(
                modifier = Modifier
                    .width(80.dp) // Fissa la larghezza a una percentuale, per esempio 80dp per il pulsante destro
            ) {
                if(DeleteAllButton){
                    TrashButton(navigator=navigator,aggiornaTotale=aggiornaTotale, eliminaTutto = eliminaTutto)
                }

            }
        }

    }
}
