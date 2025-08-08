package com.example.mobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContenitoreModalita(
    modifier: Modifier,
) {
    var isGiorno by remember { mutableStateOf(true) }

    ContenitoreOmbreggiato(
        modifier = modifier
            .clickable { isGiorno = !isGiorno } // Tutto il contenitore è cliccabile
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Modalità")

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (isGiorno) "Giorno" else "Notte")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isGiorno,
                    onCheckedChange = {
                        isGiorno = it
                    }
                )
            }
        }
    }
}
