package com.example.mobile.ui.screens.tastiera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobile.CassaViewModel
import com.example.mobile.DisplayViewModel
import com.example.mobile.ManagerScambioValuta

@Composable
fun TastierinoNumerico(
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    managerScambioValuta: ManagerScambioValuta,
    )
{

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // colore random per la colonna
        //verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //riga 1 con 123
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ogni Row prende 1 parte uguale del totale // ciascuna riga occupa il 25% dell'altezza
                //.background(Color.White)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NumeroButton(
                label = "1",
                onClick = { displayViewModel.aggiungiNumero("1") }
            )
            NumeroButton(
                label = "2",
                onClick = { displayViewModel.aggiungiNumero("2") }
            )
            NumeroButton(
                label = "3",
                onClick = { displayViewModel.aggiungiNumero("3") }
            )
        }
        //riga 2 ci 456
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ogni Row prende 1 parte uguale del totale) // ciascuna riga occupa il 25% dell'altezza
                //.background(Color.Black)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumeroButton(
                label = "4",
                onClick = { displayViewModel.aggiungiNumero("4") }
            )
            NumeroButton(
                label = "5",
                onClick = { displayViewModel.aggiungiNumero("5") }
            )
            NumeroButton(
                label = "6",
                onClick = { displayViewModel.aggiungiNumero("6") }
            )
        }
        //riga 3 con 789
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ogni Row prende 1 parte uguale del totale // ciascuna riga occupa il 25% dell'altezza
                //.background(Color.Cyan)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumeroButton(
                label = "7",
                onClick = { displayViewModel.aggiungiNumero("7") }
            )
            NumeroButton(
                label = "8",
                onClick = { displayViewModel.aggiungiNumero("8") }
            )
            NumeroButton(
                label = "9",
                onClick = { displayViewModel.aggiungiNumero("9") }
            )
        }
        //riga 4 con ,000
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ogni Row prende 1 parte uguale del totale // ciascuna riga occupa il 25% dell'altezza
                //.background(Color.Gray)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumeroButton(
                label = ".",
                onClick = { displayViewModel.aggiungiNumero(".") }
            )
            NumeroButton(
                label = "0",
                onClick = { displayViewModel.aggiungiNumero("0") }
            )
            NumeroButton(
                label = "00",
                onClick = { displayViewModel.aggiungiNumero("00") }
            )
        }
        //riga 5 con X<-+
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ogni Row prende 1 parte uguale del totale // ciascuna riga occupa il 25% dell'altezza
            //.background(Color.Gray)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumeroButton(
                label = "C",
                onClick = { displayViewModel.azzeraInput() }
            )
            NumeroButton(
                label = "←",
                onClick = { displayViewModel.cancellaUltimo()}
            )
            NumeroButton(
                label = "+",
                onClick = {
                    val numero = displayViewModel.inputCorrente.value.replace(",", ".").toDoubleOrNull()
                    if (numero != null && numero > 0) {
                        val numeroinSats=managerScambioValuta.converti(numero,managerScambioValuta.valutaSelezionata.value,"SATS")
                        cassaViewModel.aggiungiProdotto(numero,managerScambioValuta.valutaSelezionata.value,numeroinSats) //creo nuovo prodotto se ho inseirto numero valido
                        displayViewModel.confermaInput(numero)//mostro a display numeri aggiornati se andava tutto bene
                    }

                }
            )
        }
    }
}
