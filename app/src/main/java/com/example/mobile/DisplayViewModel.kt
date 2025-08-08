package com.example.mobile

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DisplayViewModel : ViewModel() {



    // Numero che stai digitando con il tastierino
    private val _inputCorrente = MutableStateFlow("")
    val inputCorrente: StateFlow<String> = _inputCorrente.asStateFlow()

    // Totale (es. importo del carrello)
    private val _totale = MutableStateFlow(0.0)
    val totale: StateFlow<Double> = _totale.asStateFlow()

    fun aggiungiNumero(input: String) {
        var current = _inputCorrente.value

        // Impedisci più di un punto
        if (input == "." && current.contains(".")) return

        // Limita la lunghezza
        if (current.length >= 16) return

        // Aggiungi il nuovo input
        current += input

        // Normalizzazione
        current = normalizeZeros(current)

        _inputCorrente.value = current
    }

    private fun normalizeZeros(input: String): String {
        return if (input.contains(".")) {
            val parts = input.split(".", limit = 2)
            val integerPart = parts[0].trimStart('0').ifEmpty { "0" }
            val decimalPart = parts[1]
            "$integerPart.$decimalPart"
        } else {
            input.trimStart('0').ifEmpty { "0" }
        }
    }


    // Cancella l'ultimo carattere
    fun cancellaUltimo() {
        _inputCorrente.value = _inputCorrente.value.dropLast(1)
    }

    // Resetta completamente l'input
    fun azzeraInput() {
        _inputCorrente.value = ""
    }

    // Convalida l'input e lo somma al totale
    fun confermaInput(numero:Double) {
        _totale.value += numero
        _inputCorrente.value = ""
    }

    fun aggiornaTotale(nuovoTotale:Double){

        _totale.value=nuovoTotale;
    }

    fun sottraiDaTotale(prezzoDaSottrarre:Double){
        _totale.value-=prezzoDaSottrarre;
    }
}