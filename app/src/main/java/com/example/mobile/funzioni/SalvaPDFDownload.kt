package com.example.mobile.funzioni

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.data.Transazione
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.Q)
fun salvaPdfNeiDownload(
    context: Context,
    transazioni: List<Transazione>,
    dataDa: String,
    dataA: String,
    managerScambioValuta: ManagerScambioValuta,
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val calDa = Calendar.getInstance()
    val calA = Calendar.getInstance()

    calDa.time = sdf.parse(dataDa)!!
    calA.time = sdf.parse(dataA)!!

    val giornoDa = calDa.get(Calendar.DAY_OF_MONTH)
    val meseDa = calDa.get(Calendar.MONTH) + 1
    val annoDa = calDa.get(Calendar.YEAR)
    val giornoA = calA.get(Calendar.DAY_OF_MONTH)
    val meseA = calA.get(Calendar.MONTH) + 1
    val annoA = calA.get(Calendar.YEAR)

    val nomeFile = "Report_Da_${giornoDa}_${meseDa}_${annoDa}_A_${giornoA}_${meseA}_${annoA}.pdf"

    // 🔹 1. Controllo se esiste già
    val projection = arrayOf(MediaStore.Downloads._ID, MediaStore.Downloads.DISPLAY_NAME)
    val selection = "${MediaStore.Downloads.DISPLAY_NAME} = ?"
    val selectionArgs = arrayOf(nomeFile)

    context.contentResolver.query(
        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID))
            val existingUri = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id)
            // 🔹 Elimino il file esistente
            context.contentResolver.delete(existingUri, null, null)
            Log.d("PDF", "File esistente eliminato: $nomeFile")
        }
    }

    // 🔹 2. Inserisco il nuovo file
    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, nomeFile)
        put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        ?: run {
            Log.e("PDF", "Errore: impossibile creare l'URI nei Download")
            return
        }

    try {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            val pdfBytes = generaPdf(transazioni, dataDa, dataA,managerScambioValuta)
            outputStream.write(pdfBytes)
            Log.d("PDF", "PDF salvato con successo nei Download $nomeFile")
        }
    } catch (e: Exception) {
        Log.e("PDF", "Errore durante il salvataggio PDF: ${e.message}", e)
    }
}
