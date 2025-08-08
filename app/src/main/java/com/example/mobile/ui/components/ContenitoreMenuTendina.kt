package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobile.CassaViewModel
import androidx.compose.runtime.collectAsState // ✅ import corretto
import com.example.mobile.DisplayViewModel
import com.example.mobile.ManagerScambioValuta

@Composable
fun ContenitoreMenuTenda(
    modifier: Modifier,
    cassaViewModel: CassaViewModel,
    managerScambioValuta: ManagerScambioValuta,
    displayViewModel: DisplayViewModel,
) {
    val valutaSelezionata by managerScambioValuta.valutaSelezionata // ✅ CORRETTO
    val options = managerScambioValuta.valute

    var expanded by remember { mutableStateOf(false) }

    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Red)
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Unità di misura")

            Box(
                modifier = Modifier.clickable { expanded = !expanded },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = valutaSelezionata)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Menu"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                managerScambioValuta.setValutaSelezionata(option)
                                displayViewModel.aggiornaTotale(managerScambioValuta.converti(cassaViewModel.totale.value,"SATS",managerScambioValuta.valutaSelezionata.value))
                                expanded = false



                            },
                            text = { Text(option) }
                        )
                    }
                }
            }
        }
    }
}
