package com.example.mobile.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ViewModel.CassaViewModel
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.ServiceClasses.FirebaseService
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ViewModel.NavigationViewModel
import com.example.mobile.ServiceClasses.TransazioniRepository
import com.example.mobile.ViewModel.TransazioniViewModel
import com.example.mobile.navigation.AppNavGraph
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.sharedComponents.BottomBar
import com.example.mobile.ui.sharedComponents.TopBar

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Applicazione(
    onLogout: () -> Unit,
    firebaseService: FirebaseService,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val navigator = Navigator(navController)
    val navigationViewModel: NavigationViewModel = viewModel()
    val cassaViewModel: CassaViewModel = viewModel();
    val displayViewModel: DisplayViewModel = viewModel()

    //creazione classe per egstione transazioni
    val context = LocalContext.current
    val transazioniRepository = remember { TransazioniRepository(context) }
    val managerScambioValuta : ManagerScambioValuta = ManagerScambioValuta();
    val transazioniViewModel : TransazioniViewModel = TransazioniViewModel(transazioniRepository);

    val totaleLocale = transazioniRepository.conteggioTransazioniTotale()
    val totaleOnlineState = firebaseService.nTransazioni.collectAsState()
    val totaleOnline = totaleOnlineState.value
    Log.w("Debug", "totale lcoale : ${totaleLocale}")
    Log.w("Debug", "totale ONline : ${totaleOnline}")
    if (totaleOnline > totaleLocale.toDouble()) {
        Log.w("Debug", "dentro funzione")
        firebaseService.getAllTransactions { transazioniNullable ->
            val transazioni = transazioniNullable ?: emptyList()
            transazioniRepository.salvaTransazioniPerMese(transazioni)
        }
    }
    firebaseService.caricaIndirizziDaServer();


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // colore dello sfondo anche sotto la status bar
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()) // padding status bar
        ) {
            TopBar(
                Modifier.weight(0.06f),
                navigator=navigator,
                navViewModel = navigationViewModel,
                aggiornaTotale = displayViewModel::aggiornaTotale,
                eliminaTutto=cassaViewModel::rimuoviTuttiProdotti,
            )
            AppNavGraph(
                Modifier.weight(0.84f),
                navigator = navigator,
                navViewModel = navigationViewModel,
                cassaViewModel = cassaViewModel,
                displayViewModel=displayViewModel,
                trasazioniRepository = transazioniRepository,
                managerScambioValuta=managerScambioValuta,
                transazioniViewModel = transazioniViewModel,
                onLogOff = onLogout,
                firebaseService=firebaseService,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
            BottomBar(
                Modifier.weight(0.10f),
                navigator=navigator,
                navViewModel = navigationViewModel,
                transazioniViewModel = transazioniViewModel
            )
        }
    }

}
