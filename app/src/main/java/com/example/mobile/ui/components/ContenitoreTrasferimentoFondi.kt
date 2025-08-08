package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator

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
                .background(Color.Yellow)
                .clickable { navigator.navigateTo(Screen.Trasferimento.route) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Red),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Trasferimento fondi")
                Image(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "Freccia avanti"
                )
            }
        }
    }
}