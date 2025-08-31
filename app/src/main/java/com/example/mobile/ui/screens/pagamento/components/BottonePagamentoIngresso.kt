package com.example.mobile.ui.screens.pagamento.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ViewModel.CassaViewModel
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.funzioni.EffettuaPagamentoIngresso
import com.example.mobile.ServiceClasses.TransazioniRepository
import com.example.mobile.ServiceClasses.FirebaseService
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato


@Composable
fun BottonePagamentoIngresso(
    modifier: Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    enabled: Boolean,
    firebaseService: FirebaseService,
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Button(
            onClick = {
                Log.d("Debug", "onclick pre funzione")
                EffettuaPagamentoIngresso(cassaViewModel=cassaViewModel, displayViewModel = displayViewModel, transazioniRepository = transazioniRepository, firebaseService = firebaseService)
                Log.d("Debug", "onclick post funzione!")
            },
            modifier = Modifier.fillMaxSize(),
            elevation = null,
            enabled = enabled,
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(0.dp) // Elimina padding predefinito
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), // Margine interno ai lati
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text="Pagamento Effettuato",
                    style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),)
            }
        }
    }
}
