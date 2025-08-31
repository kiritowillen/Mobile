package com.example.mobile.funzioni

//funzione per mostrare i numeri in maniera sesnsata

fun Double.toCustomString(maxIntegerDigits: Int = 6, decimals: Int = 8): String {
    val integerDigits = this.toLong().toString().length
    return if (integerDigits > maxIntegerDigits) {
        "%.${decimals}E".format(this)
    } else {
        "%.${decimals}f".format(this).trimEnd('0').trimEnd('.')
    }
}
