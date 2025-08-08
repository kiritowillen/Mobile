package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator


@Composable
fun CartButton(navigator: Navigator, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { navigator.navigateTo(Screen.Carrello.route) },
        modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(1f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cart),
            contentDescription = "Carrello"
        )
    }
}