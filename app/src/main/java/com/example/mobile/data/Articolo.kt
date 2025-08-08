package com.example.mobile.data
import kotlinx.serialization.Serializable

@Serializable
data class Articolo(
    val id: Int,
    val nome: String,
    val prezzo: Double,
    val valuta:String,
)
