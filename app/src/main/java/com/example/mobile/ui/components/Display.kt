package com.example.mobile.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.mobile.toCustomString
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.mobile.CassaViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import com.example.mobile.DisplayViewModel

import androidx.compose.ui.text.TextStyle
import com.example.mobile.ManagerScambioValuta

@Composable
fun Dispaly(
    cassaViewModel: CassaViewModel,
    displayViewModel: DisplayViewModel,
    managerScambioValuta: ManagerScambioValuta,
) {
    val valutaSelezionata by managerScambioValuta.valutaSelezionata
    val optionsValute = managerScambioValuta.valute
    val leftNumber=displayViewModel.totale.collectAsState()
    val rightNumber=displayViewModel.inputCorrente.collectAsState()


    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .background(Color.Green, shape = RoundedCornerShape(20))
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = leftNumber.value.toCustomString(),
                modifier = Modifier.weight(1f),
            )

            // Qui mettiamo un Box che fa da "anchor" per il DropdownMenu in modo che le mie sclete compaiaono in fondo a destra vicino a dove ho cliccato
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { expanded = true }
                ) {
                    Text(
                        text = rightNumber.value,
                        modifier = Modifier.padding(end = 8.dp),
                        style = TextStyle(textAlign = TextAlign.End)
                    )
                    Text(
                        text = valutaSelezionata,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Seleziona valuta"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.align(Alignment.TopEnd) // menu si apre verso il basso, allineato a destra
                ) {
                    optionsValute.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                Log.d("Debug", "Valore aggiornato: ${cassaViewModel.totale.value}")
                                managerScambioValuta.setValutaSelezionata(option)
                                displayViewModel.aggiornaTotale(managerScambioValuta.converti(cassaViewModel.totale.value,"SATS",managerScambioValuta.valutaSelezionata.value))
                                expanded = false
                            },
                            text = { Text(text = option) }
                        )
                    }
                }
            }
        }
    }
}


