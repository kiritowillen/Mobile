package com.example.mobile.funzioni

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

fun generaQrCode(testo: String, size: Int = 512): Bitmap {
    val writer = QRCodeWriter()
    val hints = mapOf(
        EncodeHintType.MARGIN to 0 // elimina margini bianchi
    )

    // Genera la matrice senza margini
    val bitMatrix = writer.encode(testo, BarcodeFormat.QR_CODE, size, size, hints)

    // Trova i limiti effettivi del QR (per sicurezza)
    val rect = bitMatrix.enclosingRectangle
    val qrWidth = rect[2]
    val qrHeight = rect[3]

    // Crea un bitmap che contiene solo il QR code senza bordo
    val croppedBitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888)
    for (x in 0 until qrWidth) {
        for (y in 0 until qrHeight) {
            croppedBitmap.setPixel(
                x,
                y,
                if (bitMatrix[x + rect[0], y + rect[1]]) Color.BLACK else Color.TRANSPARENT
            )
        }
    }

    // Scala il bitmap croppato per occupare tutto lo spazio richiesto
    return Bitmap.createScaledBitmap(croppedBitmap, size, size, false)
}
