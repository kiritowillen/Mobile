package com.example.mobile.ui.screens.pagamento

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mobile.CassaViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.example.mobile.navigation.Navigator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.mobile.DisplayViewModel
import com.example.mobile.FirebaseService
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.generaQrCode
import com.example.mobile.ui.components.BottoneCarrelloQuadrato
import com.example.mobile.ui.components.BottonePagamentoIngresso
import com.example.mobile.ui.components.DisplayTotale
import com.example.mobile.ui.components.RigaPulsantiPagamento
import androidx.compose.runtime.mutableStateOf
import com.example.mobile.ui.components.ContenitoreQR

@Composable
fun PagamentoScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    managerScambioValuta: ManagerScambioValuta,
    firebaseService: FirebaseService,
) {

    val walletSelezionato = remember { mutableStateOf("bitcoin") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {

        ContenitoreQR(
            modifier = Modifier
                .weight(0.6f)              // 60% altezza del contenitore padre
                .fillMaxWidth()
                .background(Color.Red),
            firebaseService=firebaseService,
            walletSelezionato = walletSelezionato.value,
        )

        // Parte centrale occupa 40% con pulsanti
        Box(
            modifier = Modifier
                .weight(0.4f) // 40% altezza
                .fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp), // margini
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                // --- 1. Riga con due pulsanti ---
                RigaPulsantiPagamento(
                    modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 1/4 altezza della colonna se sei in una colonna con weight , // colore della Row
                    onLightningClick = { walletSelezionato.value = "lightning" },
                    onBitcoinClick = { walletSelezionato.value = "bitcoin" }
                )

                Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale

                // Display del totale con cambio unità di msiura
                val totale by cassaViewModel.totale.collectAsState()
                DisplayTotale(
                    modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    cassaViewModel=cassaViewModel,
                    managerScambioValuta = managerScambioValuta,
                    testo = "Totale",
                    valore = totale,
                )

                Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale

                // --- 4. Pulsante con Carrello e icona ---
                BottoneCarrelloQuadrato(
                    modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                    navigator = navigator
                )

                Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale
                //pusalnte epr emulare pagamento
                BottonePagamentoIngresso(
                    modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    cassaViewModel = cassaViewModel,
                    displayViewModel = displayViewModel,
                    transazioniRepository=transazioniRepository,
                    enabled = true,
                    firebaseService=firebaseService,
                    )


            }
        }

    }
}


