package com.example.mobile.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.NavigationViewModel
import com.example.mobile.TransazioniRepository
import com.example.mobile.TransazioniViewModel
import com.example.mobile.navigation.AppNavGraph
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.components.BottomBar
import com.example.mobile.ui.components.TopBar

@Composable
fun Applicazione(
    modifier: Modifier = Modifier,

) {
    val navController = rememberNavController()
    val navigator = Navigator(navController)
    val navigationViewModel: NavigationViewModel = viewModel()
    val cassaViewModel: CassaViewModel= viewModel();
    val displayViewModel: DisplayViewModel = viewModel()

    //creazione classe per egstione transazioni
    val context = LocalContext.current
    val transazioniRepository = remember { TransazioniRepository(context) }
    val managerScambioValuta : ManagerScambioValuta = ManagerScambioValuta();
    val transazioniViewModel : TransazioniViewModel = TransazioniViewModel(transazioniRepository);

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color.Cyan)
    ) {
        TopBar(
            Modifier.weight(0.08f),
            navigator=navigator,
            navViewModel = navigationViewModel, //passo intero viewmodel per interagire con navigatore
            aggiornaTotale = displayViewModel::aggiornaTotale, //passo solo la funzione per aggiornare valore display
            eliminaTutto=cassaViewModel::rimuoviTuttiProdotti, //passo solo funzione per rimuvoere tutti i prodotti

        )
        AppNavGraph(
            Modifier.weight(0.82f),
            navigator = navigator,
            navViewModel = navigationViewModel,//passo intero viewmodel per interagire con navigatore
            cassaViewModel =cassaViewModel, //passo intero viewmodel per interagire con cassa
            displayViewModel=displayViewModel,//passo intero viewmodel per interagire con display,
            trasazioniRepository = transazioniRepository,// passo intero viewModel per interagire con file interni
            managerScambioValuta=managerScambioValuta,
            transazioniViewModel = transazioniViewModel,
        )
        BottomBar(
            Modifier.weight(0.10f),
            navigator=navigator,
            navViewModel = navigationViewModel, //passo intero viewmodel per interagire con navigatore
            transazioniViewModel = transazioniViewModel
        )
    }
}
