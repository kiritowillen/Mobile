package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator


@Composable
fun CartButton(navigator: Navigator, modifier: Modifier = Modifier) {
    ContenitoreTondoOmbreggiato(
        modifier = modifier
    ) {
        IconButton(
            onClick = { navigator.navigateTo(Screen.Carrello.route) },
            modifier = Modifier.fillMaxSize() // bottone su tutto il contenitore
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxHeight(0.7f),
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Carrello",
                tint = MaterialTheme.colorScheme.onPrimary,
            ) // Icona a destra
        }
    }
}