package com.example.mobile.ui.screens.impostazioni.components

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile.ServiceClasses.ManagerScambioValuta
import com.example.mobile.ViewModel.TransazioniViewModel
import com.example.mobile.funzioni.salvaPdfNeiDownload
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FiltroConDatePicker(
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

    ContenitoreOmbreggiato(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // padding generale
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Colonna con le due date
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
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
            }

            // Pulsante a destra
            Box(
                modifier = Modifier
                    .clickable {
                        salvaPdfNeiDownload(
                            context,
                            listaTransazioni,
                            dataDa,
                            dataA,
                            managerScambioValuta
                        )
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Scarica PDF", color = Color.White)
            }
        }
    }



}

@Composable
fun DatePickerField(data: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = data, color = Color.White)
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Seleziona data",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

fun mostraDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit,
    minDate: Long? = null,
    maxDate: Long? = null
) {
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected("%02d/%02d/%04d".format(dayOfMonth, month + 1, year))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Imposta limiti se presenti
    minDate?.let { datePicker.datePicker.minDate = it }
    maxDate?.let { datePicker.datePicker.maxDate = it }

    datePicker.show()
}

