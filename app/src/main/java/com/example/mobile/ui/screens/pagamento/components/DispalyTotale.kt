package com.example.mobile.ui.screens.pagamento.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.CassaViewModel
import com.example.mobile.funzioni.toCustomString
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.ui.components.ContenitoreOmbreggiato

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
        Surface(
            shape = RoundedCornerShape(8.dp), // angoli arrotondati
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 4.dp, // ombra leggera
            modifier = Modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text=testo,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, // grassetto
                        fontSize = 20.sp
                    ),
                )
                //val valore = managerScambioValuta.converti(valore,"SATS",managerScambioValuta.valutaSelezionata.value).toString();
                Text(
                    text = valore.toCustomString(), // <-- Mostra il valore reale
                    modifier = Modifier.padding(end = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, // grassetto
                        fontSize = 20.sp
                    ),
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { expanded = true }
                ) {
                    Text(
                        text = valutaSelezionata,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                    )
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
                                text = {
                                    Text(
                                        text=option,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        ),
                                    )
                                       },
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
}
