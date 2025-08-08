package com.example.mobile


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class ManagerScambioValuta {

    // Tassi rispetto ai SATS (1 SAT = base)
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
    val valute: List<String> = _valute

    private val _valutaSelezionata = mutableStateOf("SATS")
    val valutaSelezionata: State<String> = _valutaSelezionata

    private var sequentialId = 0 // lo tieni se ti serve, o rimuovi

    fun setValutaSelezionata(nuovaValuta: String) {
        _valutaSelezionata.value = nuovaValuta
    }

    fun converti(importo: Double, valutaOrigine: String, valutaDestinazione: String): Double {
        val tassi = _conversionRates.value
        val tassoOrigine = tassi[valutaOrigine] ?: error("Valuta origine non trovata")
        val tassoDestinazione = tassi[valutaDestinazione] ?: error("Valuta destinazione non trovata")
        val importoInSats = importo / tassoOrigine
        return importoInSats * tassoDestinazione
    }

    fun convertiDaValutaCorrente(importo: Double, valutaOrigine: String): Double {
        return converti(importo, valutaOrigine, valutaSelezionata.value)
    }

    fun aggiornaTassoConversione(valuta: String, nuovoTasso: Double) {
        val mappaAggiornata = _conversionRates.value.toMutableMap()
        mappaAggiornata[valuta] = nuovoTasso
        _conversionRates.value = mappaAggiornata
        //aggiornaTotaleVisibile() // MODIFICA DA FARE IN CASSA E DISPLAY
    }
}