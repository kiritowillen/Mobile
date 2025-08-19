package com.example.mobile.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile.ManagerScambioValuta
import com.example.mobile.TransazioniRepository
import com.example.mobile.TransazioniViewModel
import com.example.mobile.funzioni.salvaPdfNeiDownload
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FiltroConDatePickerold(
    modifier: Modifier = Modifier,
    transazioniViewModel: TransazioniViewModel,
    managerScambioValuta: ManagerScambioValuta,
) {
    val context = LocalContext.current

    // Osserva la lista dal ViewModel
    val listaTransazioni by transazioniViewModel.listaTransazioni
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val oggi = remember { Calendar.getInstance() }

    var dataDa by remember { mutableStateOf(sdf.format(oggi.time)) }
    var dataA by remember { mutableStateOf(sdf.format(oggi.time)) }

    // Chiamata iniziale per caricare le transazioni appena apre la pagina
    LaunchedEffect(Unit) {
        transazioniViewModel.getTransazioni(dataDa, dataA)
    }

    ContenitoreOmbreggiato(modifier=modifier) {
        Column(modifier = Modifier) {

            // FILTRO DATE
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Da:", color = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    DatePickerField(
                        data = dataDa,
                        onClick = {
                            val maxDateMillis = sdf.parse(dataA)?.time
                            mostraDatePicker(
                                context,
                                onDateSelected = { nuovaData ->
                                    dataDa = nuovaData
                                    transazioniViewModel.getTransazioni(dataDa, dataA)
                                },
                                maxDate = maxDateMillis
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "A:", color = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    DatePickerField(
                        data = dataA,
                        onClick = {
                            val minDateMillis = sdf.parse(dataDa)?.time
                            mostraDatePicker(
                                context,
                                onDateSelected = { nuovaData ->
                                    dataA = nuovaData
                                    transazioniViewModel.getTransazioni(dataDa, dataA)
                                },
                                minDate = minDateMillis
                            )
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .clickable {
                            salvaPdfNeiDownload(context,listaTransazioni,dataDa,dataA,managerScambioValuta)
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Scarica PDF", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }


}



