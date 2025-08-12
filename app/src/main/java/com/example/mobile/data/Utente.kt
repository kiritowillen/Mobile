package com.example.mobile.data
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Utente(
    val id: String,
    val bitcoinAddress: String,
    val lightningAddress: String,
    val saldo: Double
)