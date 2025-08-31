package com.example.mobile.ViewModel

import androidx.lifecycle.ViewModel
import com.example.mobile.data.Articolo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CassaViewModel : ViewModel() {
    private val _prodotti = MutableStateFlow<List<Articolo>>(emptyList())
    val prodotti: StateFlow<List<Articolo>> = _prodotti.asStateFlow()

    private val _totale = MutableStateFlow(0.0)       // VALORE PUBBLICATO
    val totale: StateFlow<Double> = _totale.asStateFlow()
    private var sequentialId = 0
    /*
    private val _conversionRates = mutableStateOf(
        mapOf(
            "SATS" to 1.0,
            "EUR" to 0.0001,
            "USD" to 0.0002,
            "BTC" to 0.00000001
        )
    )
    val conversionRates: State<Map<String, Double>> = _conversionRates

    private val _valute = listOf("USD", "EUR", "SATS", "BTC")
    private val _valutaSelezionata = mutableStateOf("SATS")
    val valutaSelezionata: State<String> = _valutaSelezionata
    val valute: List<String> = _valute

    fun setValutaSelezionata(nuovaValuta: String) {
        _valutaSelezionata.value = nuovaValuta
        aggiornaTotaleVisibile() // aggiorna quando cambio valuta
    }

    fun converti(importo: Double, valutaOrigine: String, valutaDestinazione: String): Double {
        val tassi = _conversionRates.value
        val tassoOrigine = tassi[valutaOrigine] ?: error("Valuta origine non trovata")
        val tassoDestinazione = tassi[valutaDestinazione] ?: error("Valuta destinazione non trovata")
        val importoInSats = importo / tassoOrigine
        return importoInSats * tassoDestinazione
    }*/

    /* INUTILE EPRCHÈ QUESTO NON MOSTRA PIÙ NIENTE
    private fun aggiornaTotaleVisibile() {
        val valuta = _valutaSelezionata.value
        _totale.value = _totaleInSats.value * tasso
    }*/

    fun aggiungiProdotto(prezzo: Double,valuta:String,prezzoInSats:Double) {
        val nuovo = Articolo(id = sequentialId, nome = "Prodotto $sequentialId", prezzo = prezzo,valuta=valuta)
        val current = _prodotti.value.toMutableList()
        current.add(nuovo)
        _prodotti.value = current
        sequentialId++

        // Converti il prezzo in SATS prima di sommare
        _totale.value += prezzoInSats

        //aggiornaTotaleVisibile()
    }

    fun rimuoviProdotto(prodotto: Articolo,prezzoInSats:Double) {
        val current = _prodotti.value.toMutableList()
        if (current.remove(prodotto)) {
            _prodotti.value = current
            _totale.value -= prezzoInSats

            ///aggiornaTotaleVisibile()
        }
    }

    fun rimuoviTuttiProdotti() {
        _prodotti.value = emptyList()
        _totale.value = 0.0
        //aggiornaTotaleVisibile()
    }

}
