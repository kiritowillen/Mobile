package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 2.dp, vertical = 2.dp),

        ) {
            Button(
                onClick = onLightningClick,
                modifier = Modifier.fillMaxSize(),
                elevation = null,
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Lightning",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, // grassetto
                        fontSize = 20.sp
                    ),
                )
            }
        }

        ContenitoreOmbreggiato(
            modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 2.dp, vertical = 2.dp),
        ) {
            Button(
                onClick = onBitcoinClick,
                modifier = Modifier.fillMaxSize(),
                elevation = null,
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text="Bitcoin" ,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, // grassetto
                        fontSize = 20.sp
                    ),
                )

            }
        }
    }
}
