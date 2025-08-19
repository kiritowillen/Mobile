package com.example.mobile.ui.screens.impostazioni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.FirebaseService
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.components.BottonePagamentoUscita
import com.example.mobile.ui.components.CampoImporto
import com.example.mobile.ui.components.ContenitoreOmbreggiato
import com.example.mobile.ui.components.ContenitoreQR
import com.example.mobile.ui.screens.pagamento.components.DisplayTotale
import com.example.mobile.ui.components.RigaPulsantiPagamento

@Composable
fun TrasferimentoScreen(
    modifier: Modifier=Modifier,
    navigator: Navigator,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    managerScambioValuta: ManagerScambioValuta,
    firebaseService: FirebaseService,
    )
{
    val fondiTotali = firebaseService.saldo.collectAsState()
    val walletSelezionato = remember { mutableStateOf("bitcoin") }

    ContenitoreOmbreggiato(
        modifier = Modifier
            .fillMaxWidth(0.95f) // 95% larghezza
            .fillMaxHeight(0.98f) // 90% altezza
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.Red)
        ) {
            // parte superiroe
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
                        .padding(horizontal = 16.dp, vertical = 8.dp), // margini
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    // --- 1. Riga con due pulsanti ---
                    RigaPulsantiPagamento( modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // 1/4 altezza della colonna se sei in una colonna con weight , // colore della Row
                        onLightningClick = { walletSelezionato.value = "lightning" },
                        onBitcoinClick = { walletSelezionato.value = "bitcoin" }
                        )

                    Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale

                    // Display del totale con cambio unità di msiura
                    DisplayTotale(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        cassaViewModel=cassaViewModel,
                        managerScambioValuta = managerScambioValuta,
                        testo="Totale Fondi",
                        valore=managerScambioValuta.converti(fondiTotali.value,"SATS",managerScambioValuta.valutaSelezionata.value),
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale

                    // --- 4. Contenitore importo
                    var importo by remember { mutableStateOf("") }
                    CampoImporto(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        valore = importo,
                        onValoreChange = { importo = it },

                        )

                    Spacer(modifier = Modifier.height(8.dp)) // Spazio verticale
                    //pusalnte epr emulare pagamento
                    var importoDouble = importo.toDoubleOrNull() ?: 0.0
                    var importoInSats = managerScambioValuta.converti(importoDouble, managerScambioValuta.valutaSelezionata.value, "SATS")
                    var FondiInSats= managerScambioValuta.converti(fondiTotali.value,"SATS",managerScambioValuta.valutaSelezionata.value)
                    var fondiConvertiti = managerScambioValuta.converti(fondiTotali.value,"SATS",managerScambioValuta.valutaSelezionata.value)
                    val enabledBottone = importoDouble > 0 && importoDouble <= fondiConvertiti

                    BottonePagamentoUscita(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        cassaViewModel = cassaViewModel,
                        displayViewModel = displayViewModel,
                        transazioniRepository=transazioniRepository,
                        enabled = enabledBottone,
                        valore=importoInSats,
                        firebaseService = firebaseService,
                    )



                }
            }

        }
    }


}