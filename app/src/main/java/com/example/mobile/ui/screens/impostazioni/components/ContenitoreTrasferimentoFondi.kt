package com.example.mobile.ui.screens.impostazioni.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato

@Composable
fun ContenitoreTrasferimentoFondi(
    modifier: Modifier,
    navigator: Navigator,
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable {}
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 10.dp)
                .clickable { navigator.navigateTo(Screen.Trasferimento.route) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    text="Trasferimento fondi",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                )
                Icon(
                    modifier = Modifier
                        .fillMaxHeight(0.7f),
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "Icona carrello",
                    tint = MaterialTheme.colorScheme.onPrimary,
                ) // Icona a destra
            }
        }
    }
}