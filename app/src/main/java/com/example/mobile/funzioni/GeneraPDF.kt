package com.example.mobile.funzioni

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
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.itextpdf.layout.element.Text
import java.text.SimpleDateFormat
import java.util.*
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.property.VerticalAlignment

// Funzione che genera un PDF e lo restituisce come ByteArray
fun generaPdf(
    transazioni: List<Transazione>,
    dataDa: String,
    dataA: String,
    managerScambioValuta: ManagerScambioValuta,
): ByteArray {

    val outputStream = ByteArrayOutputStream()
    val writer = PdfWriter(outputStream)
    val pdf = PdfDocument(writer)
    val document = Document(pdf)

    // Imposto margini: alto, destro, basso, sinistro (in punti)
    document.setMargins(40f, 40f, 40f, 40f)

    // Font base
    val boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD)
    val normalFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA)

    //INIZIO HEADER
    val headerTable = Table(floatArrayOf(1f, 1f))
    headerTable.setWidth(UnitValue.createPercentValue(100f))
    headerTable.setBorder(Border.NO_BORDER)

    headerTable.addCell(
        Cell().add(
            Paragraph("Convert")
                .setFont(boldFont)
                .setFontSize(28f)
                .setTextAlignment(TextAlignment.LEFT)
        ).setBorder(Border.NO_BORDER)
    )

    headerTable.addCell(
        Cell().add(
            Paragraph("Report Transazioni")
                .setFont(boldFont)
                .setFontSize(28f)
                .setTextAlignment(TextAlignment.RIGHT)
        ).setBorder(Border.NO_BORDER)
    )

    document.add(headerTable)

    // 2️⃣ Data generazione sotto, a destra
    val oggi = Calendar.getInstance().time
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    document.add(
        Paragraph("Generato il ${sdf.format(oggi)}")
            .setFont(normalFont)
            .setFontSize(14f)
            .setTextAlignment(TextAlignment.RIGHT)
    )
    //FINE HEADER
    document.add(
        Paragraph("Report entrate/uscite Bitcoin $dataDa - $dataA")
            .setFont(boldFont) // sempre in grassetto
            .setFontSize(20f)  // più piccolo della prima riga
            .setTextAlignment(TextAlignment.LEFT)
            .setMarginTop(10f)   // spazio sopra
            .setMarginBottom(10f) // spazio sotto
    )
    //INIZIO TRANSAZIONI

    // Riga orizzontale spessa (puoi regolare altezza e colore)
    val separator = Table(floatArrayOf(1f))
    separator.setWidth(UnitValue.createPercentValue(100f))
    separator.addCell(
        Cell().setBorder(Border.NO_BORDER)
            .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.BLACK)
            .setHeight(3f)
    )
    document.add(separator)

// ---- UNICA TABELLA: header + dati + riepilogo ----
    val cols = floatArrayOf(1f, 4f, 1f, 1f)
    val grid = Table(UnitValue.createPercentArray(cols)).useAllAvailableWidth()

    fun sepRow(height: Float = 2f): Cell =
        Cell(1, 4)
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.BLACK)
            .setHeight(height)

// Header
    listOf("Data", "ID", "Sats", "Euro").forEach { title ->
        grid.addCell(
            Cell().setBorder(Border.NO_BORDER).add(
                Paragraph(title)
                    .setFont(boldFont)
                    .setFontSize(14f)
                    .setTextAlignment(TextAlignment.CENTER)
            )
        )
    }

// Riga nera
    grid.addCell(sepRow(2f))

// Dati
    transazioni.forEach { t ->
        val valoreSats = if (t.isEntrata) t.quantita else -t.quantita
        val testoSats = (if (t.isEntrata) "+" else "-") + valoreSats.toString().removePrefix("-")

        // Conversione in EUR
        val valoreEuro = managerScambioValuta.converti(valoreSats, "SATS", "EUR")
        val testoEuro = String.format(Locale.getDefault(), "%.2f", valoreEuro)

        grid.addCell(
            Cell().setBorder(Border.NO_BORDER)
                .add(Paragraph(formattaData(t.dataTimestamp)))
                .setTextAlignment(TextAlignment.CENTER)
        )
        grid.addCell(
            Cell().setBorder(Border.NO_BORDER)
                .add(Paragraph(t.id))
                .setTextAlignment(TextAlignment.CENTER)
        )
        grid.addCell(
            Cell().setBorder(Border.NO_BORDER)
                .add(Paragraph(testoSats))
                .setTextAlignment(TextAlignment.CENTER)
        )
        grid.addCell(
            Cell().setBorder(Border.NO_BORDER)
                .add(Paragraph(testoEuro))
                .setTextAlignment(TextAlignment.CENTER)
        )
    }

// Riga nera
    grid.addCell(sepRow(2f))

// Riepilogo (stesse colonne!)
// --- Calcolo somme ---
    val sommaSats = transazioni.sumOf { if (it.isEntrata) it.quantita else -it.quantita }
    val sommaEuro = managerScambioValuta.converti(sommaSats, "SATS", "EUR")

    grid.addCell(
        Cell().setBorder(Border.NO_BORDER)
            .add(Paragraph("Riepilogo").setFont(boldFont).setFontSize(14f))
            .setTextAlignment(TextAlignment.LEFT)
    )
// Cella “vuota” ma mantiene la larghezza della 2ª colonna
    grid.addCell(
        Cell().setBorder(Border.NO_BORDER)
            .add(Paragraph("\u00A0")) // NBSP per non essere trimmato
    )

    grid.addCell(
        Cell().setBorder(Border.NO_BORDER)
            .add(Paragraph(sommaSats.toString()))
            .setTextAlignment(TextAlignment.CENTER)
    )
    grid.addCell(
        Cell().setBorder(Border.NO_BORDER)
            .add(Paragraph(sommaEuro.toString()))
            .setTextAlignment(TextAlignment.CENTER)
    )

// Ultima riga nera
    grid.addCell(sepRow(2f))

    document.add(grid)


    // Qui potrai poi aggiungere la lista delle transazioni

    document.close()
    return outputStream.toByteArray()
}

fun formattaData(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}