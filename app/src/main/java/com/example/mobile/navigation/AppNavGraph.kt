package com.example.mobile.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobile.NavigationViewModel
import com.example.mobile.data.Screen
import com.example.mobile.ui.screens.impostazioni.ImpostazioniScreen
import com.example.mobile.ui.screens.impostazioni.TransazioniScreen
import com.example.mobile.ui.screens.impostazioni.TrasferimentoScreen
import com.example.mobile.ui.screens.pagamento.PagamentoScreen
import com.example.mobile.ui.screens.tastiera.CarrelloScreen
import com.example.mobile.ui.screens.tastiera.TastieraScreen
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.TransazioniViewModel

/*
Il NavGraph è diviso in 3 sezioni e mi dice quali sono le mie route disponibili
che tra di loro non hanno memoria ovvero ogni volta che premo uno dei 3 pulsanti
principali resetto lo stack.
*/
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navViewModel: NavigationViewModel,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    trasazioniRepository:TransazioniRepository,
    managerScambioValuta: ManagerScambioValuta,
    transazioniViewModel : TransazioniViewModel,
) {
    //questo blocco di cose serve a gestire il cambio del valore CurrentScreen nel NavigationViewModel
    val navBackStackEntry = navigator.navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry.value?.destination?.route) {
        when (val route = navBackStackEntry.value?.destination?.route) {
            Screen.Tastiera.route -> navViewModel.navigateTo(Screen.Tastiera)
            Screen.Carrello.route -> navViewModel.navigateTo(Screen.Carrello)
            Screen.Pagamento.route -> navViewModel.navigateTo(Screen.Pagamento)
            Screen.Impostazioni.route -> navViewModel.navigateTo(Screen.Impostazioni)
            Screen.Transazioni.route -> navViewModel.navigateTo(Screen.Transazioni)
            Screen.Trasferimento.route -> navViewModel.navigateTo(Screen.Trasferimento)
            Screen.CambioPin.route -> navViewModel.navigateTo(Screen.CambioPin)
        }
    }

    Box(
        modifier = modifier
        .fillMaxWidth()
        .background(Color.Magenta),
        contentAlignment = Alignment.Center
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = Screen.Tastiera.route
        ) {
            //route impostazioni
            composable(Screen.Impostazioni.route) {
                ImpostazioniScreen(
                    navigator=navigator,
                    cassaViewModel=cassaViewModel,
                    transazioniRepository = trasazioniRepository,
                    managerScambioValuta = managerScambioValuta,
                    displayViewModel = displayViewModel,
                    transazioniViewModel = transazioniViewModel,
                )
            }
            composable(Screen.Transazioni.route) { TransazioniScreen(
                transazioniViewModel = transazioniViewModel,
            ) }
            composable(Screen.Trasferimento.route) { TrasferimentoScreen(
                navigator=navigator,
                cassaViewModel=cassaViewModel,
                displayViewModel = displayViewModel,
                transazioniRepository = trasazioniRepository,
                managerScambioValuta = managerScambioValuta
            ) }
            //route pagamento
            composable(Screen.Pagamento.route) {
                PagamentoScreen(
                    navigator=navigator,
                    cassaViewModel=cassaViewModel,
                    displayViewModel = displayViewModel,
                    transazioniRepository = trasazioniRepository,
                    managerScambioValuta = managerScambioValuta)
            }
            //route tastiera
            composable(Screen.Tastiera.route) {
                TastieraScreen(
                    cassaViewModel=cassaViewModel,
                    displayViewModel = displayViewModel,
                    managerScambioValuta = managerScambioValuta)
            }
            composable(Screen.Carrello.route) {
                CarrelloScreen(
                    cassaViewModel=cassaViewModel,
                    displayViewModel = displayViewModel,
                    managerScambioValuta = managerScambioValuta)
            }
        }
    }
}

