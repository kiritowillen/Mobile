package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobile.NavigationViewModel
import com.example.mobile.TransazioniViewModel
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.mobile.R


@Composable
fun BottomBar(
    modifier: Modifier ,
    navigator: Navigator,
    navViewModel: NavigationViewModel,
    transazioniViewModel: TransazioniViewModel,
) {
    val currentScreen = navViewModel.currentScreen.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        ContenitoreOmbreggiato(
            modifier=Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.93f)
                .clip(RoundedCornerShape(5.dp))
                //.background(MaterialTheme.colorScheme.primary)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
                    .background(MaterialTheme.colorScheme.surface),
                    //.background(Color.Red),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier=Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    PulsanteConIcon(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight(0.9f)
                        //.background(MaterialTheme.colorScheme.primary)
                        ,
                        onClick =  {if (currentScreen != Screen.Tastiera) {
                            navigator.navigateClearingStack(Screen.Tastiera.route)
                            navViewModel.navigateTo(Screen.Tastiera)
                        }
                        },
                        imagePainter = painterResource(id = R.drawable.tastierino),
                        highlighted = currentScreen == Screen.Tastiera
                    )
                }
                Box(
                    modifier=Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    PulsanteConIcon(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight(0.9f)
                        //.background(MaterialTheme.colorScheme.primary)
                        ,
                        onClick =  {
                            if (currentScreen != Screen.Pagamento) {
                                navigator.navigateClearingStack(Screen.Pagamento.route)
                                navViewModel.navigateTo(Screen.Pagamento)
                            }
                        },
                        imagePainter = painterResource(id = R.drawable.card),
                        highlighted = currentScreen == Screen.Pagamento
                    )
                }

                Box(
                    modifier=Modifier.weight(1f) ,
                    contentAlignment = Alignment.Center
                ){
                    PulsanteConIcon(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight(0.9f)
                        //.background(MaterialTheme.colorScheme.primary)
                        ,
                        onClick =  {
                            if (currentScreen != Screen.Impostazioni) {
                                navigator.navigateClearingStack(Screen.Impostazioni.route)
                                navViewModel.navigateTo(Screen.Impostazioni)
                                transazioniViewModel.caricaUltimeTransazioni();
                            }
                        },
                        imagePainter = painterResource(id = R.drawable.list),
                        highlighted = currentScreen == Screen.Impostazioni
                    )
                }

                /*
                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: tastiera")
                    if (currentScreen != Screen.Tastiera) {
                        navigator.navigateClearingStack(Screen.Tastiera.route)
                        navViewModel.navigateTo(Screen.Tastiera)
                    }
                }) {
                    Text("Tastiera")
                }
                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: pagamento")
                    if (currentScreen != Screen.Pagamento) {
                        navigator.navigateClearingStack(Screen.Pagamento.route)
                        navViewModel.navigateTo(Screen.Pagamento)
                    }
                }) {
                    Text("Pagamento")
                }

                Button(onClick = {
                    //Log.d("BottomBar", "Current screen: ${currentScreen.route}, Target: impostazioni")
                    if (currentScreen != Screen.Impostazioni) {
                        navigator.navigateClearingStack(Screen.Impostazioni.route)
                        navViewModel.navigateTo(Screen.Impostazioni)
                        transazioniViewModel.caricaUltimeTransazioni();
                    }
                }) {
                    Text("Impostazioni")
                }*/
            }
        }


    }
}

