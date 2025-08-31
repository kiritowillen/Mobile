package com.example.mobile.ui.screens.impostazioni.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.ViewModel.CassaViewModel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobile.ViewModel.DisplayViewModel
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato

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
                .background(MaterialTheme.colorScheme.primary)
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp,),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Unità di misura",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )

            Box(
                modifier = Modifier.clickable { expanded = !expanded },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = valutaSelezionata,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                    )
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
                            text = {
                                Text(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text=option,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
