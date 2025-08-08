package com.example.mobile.data



class Carrello {
    private val prodotti = mutableListOf<Articolo>()

    fun aggiungiProdotto(p: Articolo) {
        prodotti.add(p)
    }

    fun rimuoviProdotto(p: Articolo) {
        prodotti.remove(p)
    }

    fun totale(): Double = prodotti.sumOf { it.prezzo }

    fun listaProdotti(): List<Articolo> = prodotti.toList()
}