package com.example.mobile.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.TransazioniRepository
import com.example.mobile.TransazioniViewModel
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator


@Composable
fun ContenitoreTransazioni(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    transazioniRepository: TransazioniRepository,
    transazioniViewModel : TransazioniViewModel,
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
            .clickable { navigator.navigateTo(Screen.Transazioni.route) },
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                // Row alta 10%, larga 100%
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .background(Color.Red)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Transazioni")
                    Image(
                        painter = painterResource(id = R.drawable.forward),
                        contentDescription = "Freccia avanti"
                    )
                }

                // Column alta 90%, larga 100%
                val transazioni by transazioniViewModel.ultimeTransazioni.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.9f) // 90% dell'altezza del contenitore principale
                        .background(Color.Green),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val righeDaMostrare = 5

                    repeat(righeDaMostrare) { index ->
                        if (index < transazioni.size) {
                            val transazione = transazioni[index]
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f / righeDaMostrare)
                            ) {
                                RigaTransazione(transazione)
                            }
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f / righeDaMostrare)
                            )
                        }
                    }
                }
            }
        }
    }


}
