package com.example.mobile.ui.screens.impostazioni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.ViewModel.CassaViewModel
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.ServiceClasses.FirebaseService
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ServiceClasses.TransazioniRepository
import com.example.mobile.ViewModel.TransazioniViewModel
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.screens.impostazioni.components.BottoneLogOff
import com.example.mobile.ui.screens.impostazioni.components.ContenitoreTransazioni
import com.example.mobile.ui.screens.impostazioni.components.ContenitoreTrasferimentoFondi
import com.example.mobile.ui.screens.impostazioni.components.ContenitoreMenuTenda
import com.example.mobile.ui.screens.impostazioni.components.ContenitoreModalita
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato


@Composable
fun ImpostazioniScreen(
    modifier: Modifier=Modifier,
    navigator: Navigator,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    managerScambioValuta: ManagerScambioValuta,
    transazioniViewModel : TransazioniViewModel,
    onLogOff: () -> Unit,
    firebaseService: FirebaseService,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
    ) {
    ContenitoreOmbreggiato(
        modifier = Modifier
            .fillMaxWidth(0.95f) // 95% larghezza
            .fillMaxHeight(0.98f) // 90% altezza
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp)
        ) {
            val paddingVertical = 8.dp
            val paddingHorizontal = 16.dp

            ContenitoreTransazioni(
                modifier = modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    //.background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
                navigator=navigator,
                transazioniRepository = transazioniRepository,
                transazioniViewModel=transazioniViewModel,
            )


            ContenitoreTrasferimentoFondi(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
                navigator = navigator,
                )


            ContenitoreMenuTenda(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
                cassaViewModel = cassaViewModel,
                managerScambioValuta = managerScambioValuta,
                displayViewModel = displayViewModel,
            )

            ContenitoreModalita(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
            )

            BottoneLogOff (
                modifier = Modifier
                    //.clip(RoundedCornerShape(12.dp))
                    .weight(0.1f)
                    .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
                firebaseService=firebaseService,
                onLogOff = onLogOff,
            )
        }
    }
}