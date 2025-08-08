package com.example.mobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.mobile.data.Transazione
import com.example.mobile.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RigaTransazione(transazione: Transazione, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sezione immagine (10%)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bitcoin_logo_circle),
                contentDescription = "Bitcoin Logo"
            )
        }

        // Sezione ID e data (45%)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.45f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = transazione.id, fontSize = 14.sp)
            Text(
                text = formattaData(transazione.dataTimestamp),
                fontSize = 10.sp,
                color = Color.Gray
            )
        }

        // Sezione quantità (45%)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.45f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            val simbolo = if (transazione.isEntrata) "+" else "-"
            Text(text = "$simbolo${transazione.quantita}", fontSize = 14.sp)
            Text(text = "sats", fontSize = 10.sp, color = Color.Gray)
        }
    }
}

fun formattaData(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}
