package com.example.mobile.ui.screens.impostazioni.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato

@Composable
fun ContenitoreModalita(
    modifier: Modifier,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    ContenitoreOmbreggiato(
        modifier = modifier
            .clickable { onThemeToggle() } // cliccando il contenitore cambia tema
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Modalità",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    text = if (!isDarkTheme) "Giorno" else "Notte") // mostra "Giorno" se isDarkTheme = false
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkTheme, // lo switch riflette il tema
                    onCheckedChange = { onThemeToggle() }, // chiama il toggle globale
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onBackground,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onBackground,
                        checkedTrackColor = MaterialTheme.colorScheme.background,
                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}
