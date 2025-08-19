package com.example.mobile.ui.screens.impostazioni.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
                .weight(0.15f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bitcoin_logo_circle),
                contentDescription = "Bitcoin Logo",
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        // Sezione ID e data (45%)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                //.background(Color.Red)
                .weight(0.35f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = formattaData(transazione.dataTimestamp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                )
        }

        // Sezione quantità (45%)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                //.background(Color.Yellow)
                .weight(0.50f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            val simbolo = if (transazione.isEntrata) "+" else "-"
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "$simbolo${transazione.quantita} sats",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )
        }
    }
}

fun formattaData(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}
