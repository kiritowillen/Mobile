package com.example.mobile

import android.R.id
import android.content.Context
import android.util.Log
import com.example.mobile.data.Articolo
import com.example.mobile.data.Transazione
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Calendar
import java.util.Date
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Locale


class TransazioniRepository(private val context: Context) {

    private val nomeCartella = "transazioni"
    private val cartellaTransazioni: File = File(context.filesDir, nomeCartella)

    init {
        creaCartellaSeNonEsiste()
    }

    // 🔍 Controlla se la cartella esiste
    fun cartellaEsiste(): Boolean {
        val exists = cartellaTransazioni.exists()
        Log.d("Repo", "📁 Cartella '${cartellaTransazioni.name}' esiste: $exists")
        return exists
    }

    // 🆕 Crea la cartella se non esiste
    fun creaCartellaSeNonEsiste() {
        if (!cartellaTransazioni.exists()) {
            val created = cartellaTransazioni.mkdirs()
            Log.d("Repo", if (created) "✅ Cartella '$nomeCartella' creata." else "❌ Errore nella creazione della cartella.")
        } else {
            Log.d("Repo", "📁 Cartella '$nomeCartella' già esistente.")
        }
    }

    // 📜 Restituisce tutti i file presenti nella cartella
    fun ottieniTuttiIFiles(): List<File> {
        if (!cartellaEsiste()) {
            Log.d("Repo", "⚠️ La cartella non esiste, restituisco lista vuota.")
            return emptyList()
        }

        val files = cartellaTransazioni.listFiles()?.filter { it.isFile } ?: emptyList()
        Log.d("Repo", "📂 Trovati ${files.size} file nella cartella '$nomeCartella'.")
        files.forEach { Log.d("Repo", "📄 ${it.name}") }
        return files
    }

    // 🗑️ Elimina l'intera cartella e i file al suo interno
    fun eliminaCartella() {
        if (cartellaTransazioni.exists()) {
            val files = cartellaTransazioni.listFiles()
            files?.forEach { it.delete() }
            val deleted = cartellaTransazioni.delete()
            Log.d("Repo", if (deleted) "🗑️ Cartella eliminata con successo." else "❌ Errore nell'eliminazione della cartella.")
        } else {
            Log.d("Repo", "⚠️ Cartella '$nomeCartella' non esiste.")
        }
    }

    // 💾 Esempio: salva una lista di transazioni in un file con nome specifico
    fun salvaTransazioni(transazioni: List<Transazione>, nomeFile: String) {
        val file = File(cartellaTransazioni, nomeFile)

        // 1. Ordina le nuove transazioni per data (timestamp)
        val nuoveTransazioniOrdinate = transazioni.sortedBy { it.dataTimestamp }

        // 2. Carica le transazioni esistenti se il file esiste
        val transazioniEsistenti = if (file.exists()) {
            try {
                val json = file.readText()
                Json.decodeFromString<List<Transazione>>(json)
            } catch (e: Exception) {
                Log.e("Repo", "❌ Errore durante il parsing del file: ${e.message}")
                emptyList()
            }
        } else {
            emptyList()
        }

        // 3. Crea una mappa per evitare duplicati basandosi sull'ID
        val mappaTransazioni = mutableMapOf<String, Transazione>()
        (transazioniEsistenti + nuoveTransazioniOrdinate).forEach { tx ->
            mappaTransazioni[tx.id] = tx
        }

        // 4. Ordina tutte le transazioni risultanti
        val transazioniFinali = mappaTransazioni.values.sortedBy { it.dataTimestamp }

        // 5. Scrive le transazioni finali nel file
        val jsonFinale = Json.encodeToString(transazioniFinali)
        file.writeText(jsonFinale)

        Log.d("Repo", "💾 Salvate ${transazioniFinali.size} transazioni in '${file.name}' (dopo merge e ordinamento)")
    }

    fun aggiugiTransizione(
        valore: Double,
        prodotti: List<Articolo>,
        isEntrata:Boolean
    ){
        //generazione nome file da data
        val calendar = Calendar.getInstance()
        val anno = calendar.get(Calendar.YEAR)
        val mese = calendar.get(Calendar.MONTH) + 1 // 0 = Gennaio, quindi aggiungi 1
        val nome= String.format("%04d_%02d", anno, mese)
        //creazione transazione
        val nuvoaTransazione=Transazione(
            id=UUID.randomUUID().toString(),
            dataTimestamp= Date().time,
            quantita = valore,
            articoli = prodotti,
            isEntrata = isEntrata,
        )
        //caricamento transazione
        salvaTransazioni(listOf(nuvoaTransazione),nome)
    }

    fun getTransazioni(dataInizio: String, dataFine: String): List<Transazione> {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Parsing date di inizio e fine
        val startDate = sdf.parse(dataInizio) ?: return emptyList()

        val rawEndDate = sdf.parse(dataFine) ?: return emptyList()
        // Imposto endDate alla fine del giorno (23:59:59.999)
        val calFine = Calendar.getInstance().apply {
            time = rawEndDate
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        val endDate = calFine.time

        // Calendari di inizio e fine per confronto mese/anno
        val calInizio = Calendar.getInstance().apply { time = startDate }
        val calFineMese = Calendar.getInstance().apply {
            time = endDate
            // Nota: usiamo endDate come fine giorno
        }

        val risultato = mutableListOf<Transazione>()

        // Ottieni tutti i file presenti nella cartella transazioni
        val files = ottieniTuttiIFiles()

        // Cicla sui file e prendi quelli con nome nel range yyyy_MM
        for (file in files) {
            val nomeFile = file.nameWithoutExtension // es. "2025_08"

            // Prova a estrarre anno e mese dal nome file
            val parts = nomeFile.split("_")
            if (parts.size != 2) continue

            val anno = parts[0].toIntOrNull() ?: continue
            val mese = parts[1].toIntOrNull() ?: continue

            // Costruisci un calendario per il primo giorno del mese del file
            val calFile = Calendar.getInstance()
            calFile.set(Calendar.YEAR, anno)
            calFile.set(Calendar.MONTH, mese - 1)  // mese 0-based
            calFile.set(Calendar.DAY_OF_MONTH, 1)
            calFile.set(Calendar.HOUR_OF_DAY, 0)
            calFile.set(Calendar.MINUTE, 0)
            calFile.set(Calendar.SECOND, 0)
            calFile.set(Calendar.MILLISECOND, 0)

            // Costruisci un calendario per l'ultimo giorno del mese
            val calFileFine = calFile.clone() as Calendar
            calFileFine.set(Calendar.DAY_OF_MONTH, calFile.getActualMaximum(Calendar.DAY_OF_MONTH))
            calFileFine.set(Calendar.HOUR_OF_DAY, 23)
            calFileFine.set(Calendar.MINUTE, 59)
            calFileFine.set(Calendar.SECOND, 59)
            calFileFine.set(Calendar.MILLISECOND, 999)

            // Controlla se il mese del file rientra nell'intervallo richiesto
            if (calFile.after(calFineMese) || calFileFine.before(calInizio)) {
                // Il mese non è compreso nell'intervallo richiesto, skip
                continue
            }

            // Se siamo qui, leggiamo il file e filtriamo le transazioni
            try {
                val json = file.readText()
                val transazioniNelFile = Json.decodeFromString<List<Transazione>>(json)

                val transazioniFiltrate = transazioniNelFile.filter { tx ->
                    val dataTx = Date(tx.dataTimestamp)
                    !dataTx.before(startDate) && !dataTx.after(endDate)
                }

                risultato.addAll(transazioniFiltrate)
            } catch (e: Exception) {
                // In caso di errore di parsing continua
                continue
            }
        }

        // Ordina la lista finale per dataTimestamp (opzionale)
        return risultato.sortedByDescending { it.dataTimestamp }
    }


}
