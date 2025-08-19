package com.example.mobile.funzioni

import android.util.Log
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.FirebaseService
import com.example.mobile.TransazioniRepository
import com.example.mobile.data.Transazione
import java.util.Calendar
import java.util.Date
import java.util.UUID

fun EffettuaPagamentoIngresso(cassaViewModel: CassaViewModel, displayViewModel: DisplayViewModel, transazioniRepository: TransazioniRepository, firebaseService: FirebaseService){
    if (cassaViewModel.totale.value > 0) {
        val calendar = Calendar.getInstance()
        val anno = calendar.get(Calendar.YEAR)
        val mese = calendar.get(Calendar.MONTH) + 1 // 0 = Gennaio, quindi aggiungi 1
        val nome= String.format("%04d_%02d", anno, mese)
        //creazione transazione
        val nuovaTransazione= Transazione(
            id= UUID.randomUUID().toString(),
            dataTimestamp= Date().time,
            quantita = cassaViewModel.totale.value,
            articoli = cassaViewModel.prodotti.value,
            isEntrata = true,
            from = "from",
            to="to",

            )
        transazioniRepository.aggiugiTransizione(transazione = nuovaTransazione)//creo la transazione
        displayViewModel.sottraiDaTotale(cassaViewModel.totale.value)//modifico graficamente il valore che si vede nel display
        displayViewModel.azzeraInput()//azzero input
        cassaViewModel.rimuoviTuttiProdotti()//rimuovo tutti i prodotti dalla casa
        firebaseService.addTransaction(nuovaTransazione)

    }
}
fun EffettuaPagamentoUscita(valore:Double, transazioniRepository: TransazioniRepository, firebaseService: FirebaseService){
    Log.d("Debug", "Effettuapagetno inizio")
    if (valore > 0) {
        val nuovaTransazione= Transazione(
            id= UUID.randomUUID().toString(),
            dataTimestamp= Date().time,
            quantita = valore,
            articoli = emptyList(),
            isEntrata = false,
            from = "from interno",
            to="to esterno",

            )
        transazioniRepository.aggiugiTransizione(nuovaTransazione)//creo la transazione
        Log.d("Debug", "siamo in effettuapagemtnouscita")
        firebaseService.addTransaction(nuovaTransazione)

    }
}