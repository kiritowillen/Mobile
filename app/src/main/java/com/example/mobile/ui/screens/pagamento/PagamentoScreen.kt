package com.example.mobile.ui.screens.pagamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mobile.CassaViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mobile.DisplayViewModel
import com.example.mobile.EffettuaPagamentoIngresso
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.ui.components.BottoneCarrelloQuadrato
import com.example.mobile.ui.components.BottonePagamentoIngresso
import com.example.mobile.ui.components.ContenitoreOmbreggiato
import com.example.mobile.ui.components.DisplayTotale
import com.example.mobile.ui.components.RigaPulsantiPagamento
import java.util.Calendar

@Composable
fun PagamentoScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    managerScambioValuta: ManagerScambioValuta,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        // parte superiroe
        Box(
            modifier = Modifier
                .weight(0.6f)              // 60% altezza del contenitore
                .fillMaxWidth()
                .background(Color.Red)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                //spacer sorpa 2%
                Spacer(modifier = Modifier.weight(0.02f))

                // Parte alta (83%)
                ContenitoreOmbreggiato(
                    modifier = Modifier
                        .weight(0.83f)
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Blue)
                        .align(Alignment.CenterHorizontally),

                ) {
                    Text("Contenuto nella Box colorata")
                }

                // Parte bassa (15%) con due righe
                Column(
                    modifier = Modifier
                        .weight(0.15f)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Copia")
                        Text("Condividi")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text((1..10).map { (0..9).random() }.joinToString(""))
                    }
                }
            }

        }

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
                RigaPulsantiPagamento( modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 1/4 altezza della colonna se sei in una colonna con weight , // colore della Row
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
                    )


            }
        }

    }
}


