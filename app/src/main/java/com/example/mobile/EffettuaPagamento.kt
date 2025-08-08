package com.example.mobile

import android.util.Log

fun EffettuaPagamentoIngresso(cassaViewModel: CassaViewModel,displayViewModel: DisplayViewModel,transazioniRepository: TransazioniRepository){
    if (cassaViewModel.totale.value > 0) {
        transazioniRepository.aggiugiTransizione(cassaViewModel.totale.value,cassaViewModel.prodotti.value,true)//creo la transazione
        displayViewModel.sottraiDaTotale(cassaViewModel.totale.value)//modifico graficamente il valore che si vede nel display
        displayViewModel.azzeraInput()//azzero input
        cassaViewModel.rimuoviTuttiProdotti()//rimuovo tutti i prodotti dalla casa


    }
}
fun EffettuaPagamentoUscita(valore:Double,transazioniRepository: TransazioniRepository){
    Log.d("Debug", "Effettuapagetno inizio")
    if (valore > 0) {
        transazioniRepository.aggiugiTransizione(valore, emptyList(),false)//creo la transazione
        Log.d("Debug", "siamo in effettuapagemtnouscita")

    }
}