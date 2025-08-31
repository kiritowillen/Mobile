package com.example.mobile.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.ServiceClasses.TransazioniRepository
import com.example.mobile.data.Transazione
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import androidx.compose.runtime.State

class TransazioniViewModel(
    private val transazioniRepository: TransazioniRepository
) : ViewModel() {

    private val _ultimeTransazioni = MutableStateFlow<List<Transazione>>(emptyList())
    val ultimeTransazioni: StateFlow<List<Transazione>> = _ultimeTransazioni

    // Stato osservabile della lista
    private val _listaTransazioni = mutableStateOf<List<Transazione>>(emptyList())
    val listaTransazioni: State<List<Transazione>> = _listaTransazioni

    init {
        caricaUltimeTransazioni()

    }

    fun caricaUltimeTransazioni() {
        viewModelScope.launch {
            val filePiuRecente = transazioniRepository
                .ottieniTuttiIFiles()
                .sortedByDescending { it.lastModified() }
                .firstOrNull()

            if (filePiuRecente != null && filePiuRecente.exists()) {
                try {
                    val json = filePiuRecente.readText()
                    val transazioni = Json.decodeFromString<List<Transazione>>(json)
                        .sortedByDescending { it.dataTimestamp }
                        .take(5)

                    _ultimeTransazioni.value = transazioni
                } catch (e: Exception) {
                    // Log or handle error
                    _ultimeTransazioni.value = emptyList()
                }
            } else {
                _ultimeTransazioni.value = emptyList()
            }
        }
    }

    fun getTransazioni(dataInizio: String, dataFine: String) {
        viewModelScope.launch {
            val nuoveTransazioni = transazioniRepository.getTransazioni(dataInizio, dataFine)
            _listaTransazioni.value = nuoveTransazioni
            Log.d("Debug", "Transazioni trovate:")
            nuoveTransazioni.forEach { transazione ->
                Log.d("Debug", transazione.toString())
            }
        }
    }

}
