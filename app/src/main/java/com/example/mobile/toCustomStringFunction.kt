package com.example.mobile

//funzione per mostrare i numeri in maniera sesnsata

fun Double.toCustomString(maxIntegerDigits: Int = 10, decimals: Int = 12): String {
    val integerDigits = this.toLong().toString().length
    return if (integerDigits > maxIntegerDigits) {
        "%.${decimals}E".format(this)
    } else {
        "%.${decimals}f".format(this).trimEnd('0').trimEnd('.')
    }
}
