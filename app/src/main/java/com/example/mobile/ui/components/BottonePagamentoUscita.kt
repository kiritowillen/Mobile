package com.example.mobile.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.TransazioniRepository
import com.example.mobile.EffettuaPagamentoUscita
import com.example.mobile.FirebaseService


@Composable
fun BottonePagamentoUscita(
    modifier: Modifier,
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    transazioniRepository: TransazioniRepository,
    enabled: Boolean,
    valore:Double,
    firebaseService: FirebaseService,

) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Button(
            onClick = {

                Log.d("Debug", "onclick pre funzione")
                EffettuaPagamentoUscita(valore = valore, transazioniRepository = transazioniRepository,firebaseService=firebaseService)
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
                Text("Pagamento Effettuato")
            }
        }
    }
}
