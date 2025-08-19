package com.example.mobile.ui.screens.pagamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobile.CassaViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import com.example.mobile.navigation.Navigator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.mobile.DisplayViewModel
import com.example.mobile.FirebaseService
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.ui.screens.pagamento.components.BottoneCarrelloQuadrato
import com.example.mobile.ui.screens.pagamento.components.BottonePagamentoIngresso
import com.example.mobile.ui.screens.pagamento.components.DisplayTotale
import com.example.mobile.ui.components.RigaPulsantiPagamento
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import com.example.mobile.ui.components.ContenitoreOmbreggiato
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

    ContenitoreOmbreggiato(
            modifier = Modifier
            .fillMaxWidth(0.95f) // 95% larghezza
            .fillMaxHeight(0.98f) // 90% altezza
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            ContenitoreQR(
                modifier = Modifier
                    .weight(0.6f)              // 60% altezza del contenitore padre
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
                firebaseService=firebaseService,
                walletSelezionato = walletSelezionato,
            )

            // Parte centrale occupa 40% con pulsanti
            Box(
                modifier = Modifier
                    .weight(0.4f) // 40% altezza
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp), // margini
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
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 2.dp, vertical = 2.dp),
                        cassaViewModel=cassaViewModel,
                        managerScambioValuta = managerScambioValuta,
                        testo = "Totale",
                        valore = totale,
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale

                    // --- 4. Pulsante con Carrello e icona ---
                    BottoneCarrelloQuadrato(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 2.dp, vertical = 2.dp),
                        navigator = navigator
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale
                    //pusalnte epr emulare pagamento
                    BottonePagamentoIngresso(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 2.dp, vertical = 2.dp),
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


}


