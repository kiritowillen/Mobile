package com.example.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import com.example.mobile.navigation.Navigator


@Composable
fun TrashButton(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    aggiornaTotale: (Double) -> Unit,
    eliminaTutto: () -> Unit,
) {
    IconButton(
        onClick = {
            aggiornaTotale(0.0)
            eliminaTutto()
                  },
        modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(1f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bin),
            contentDescription = "Elimina tutto"
        )
    }
}