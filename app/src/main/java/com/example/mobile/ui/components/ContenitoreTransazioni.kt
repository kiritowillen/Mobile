package com.example.mobile.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.TransazioniRepository
import com.example.mobile.TransazioniViewModel
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.screens.impostazioni.components.RigaTransazione


@Composable
fun ContenitoreTransazioni(
    modifier: Modifier,
    navigator: Navigator,
    transazioniRepository: TransazioniRepository,
    transazioniViewModel : TransazioniViewModel,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        ContenitoreOmbreggiato(
            modifier = Modifier
                .fillMaxWidth(0.99f)
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
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onPrimary,
                            text="Ultime Transazioni",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            )
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight(0.9f),
                            painter = painterResource(id = R.drawable.forward),
                            contentDescription = "Icona carrello",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        ) // Icona a destra
                    }

                    // Column alta 90%, larga 100%
                    val transazioni by transazioniViewModel.ultimeTransazioni.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.9f) // 90% dell'altezza del contenitore principale
                            .background(MaterialTheme.colorScheme.primary),
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



}
