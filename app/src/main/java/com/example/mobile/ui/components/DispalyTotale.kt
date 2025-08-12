package com.example.mobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.CassaViewModel
import com.example.mobile.toCustomString
import androidx.compose.runtime.collectAsState
import com.example.mobile.ManagerScambioValuta

@Composable
fun DisplayTotale(
    modifier: Modifier,
    cassaViewModel: CassaViewModel,
    managerScambioValuta: ManagerScambioValuta,
    testo:String,
    valore:Double
) {

    val valutaSelezionata by managerScambioValuta.valutaSelezionata
    val valute = managerScambioValuta.valute

    var expanded by remember { mutableStateOf(false) }

    ContenitoreOmbreggiato(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(testo)
            //val valore = managerScambioValuta.converti(valore,"SATS",managerScambioValuta.valutaSelezionata.value).toString();
            Text(
                text = valore.toCustomString(), // <-- Mostra il valore reale
                modifier = Modifier.padding(end = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { expanded = true }
            ) {
                Text(text = valutaSelezionata)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Cambia valuta"
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    valute.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                managerScambioValuta.setValutaSelezionata(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
