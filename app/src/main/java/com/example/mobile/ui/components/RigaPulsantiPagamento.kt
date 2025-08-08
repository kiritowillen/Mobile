package com.example.mobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RigaPulsantiPagamento(
    modifier: Modifier,
    onLightningClick: () -> Unit = {},
    onBitcoinClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp), // spazio tra i pulsanti
        verticalAlignment = Alignment.CenterVertically
    )
    {
        ContenitoreOmbreggiato(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(
                onClick = onLightningClick,
                modifier = Modifier.fillMaxSize(),
                elevation = null,
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Lightning")
            }
        }

        ContenitoreOmbreggiato(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(
                onClick = onBitcoinClick,
                modifier = Modifier.fillMaxSize(),
                elevation = null,
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Bitcoin")
            }
        }
    }
}
