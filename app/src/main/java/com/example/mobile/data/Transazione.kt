package com.example.mobile.data
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Transazione(
    val id: String,
    val quantita: Double,// in sats
    val dataTimestamp: Long,
    val articoli: List<Articolo>,
    val isEntrata: Boolean,
    val from :String,
    val to:String,
)
