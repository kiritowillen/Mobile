package com.example.mobile.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobile.R
import com.example.mobile.data.Screen
import com.example.mobile.navigation.Navigator


@Composable
fun BottoneCarrelloQuadrato(
    modifier: Modifier ,
    navigator: Navigator
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Button(
            onClick = { navigator.navigateTo(Screen.Carrello.route) },
            modifier = Modifier.fillMaxSize(),
            elevation = null,
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(0.dp) // Elimina padding standard
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), // margine interno ai lati
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Carrello") // Testo a sinistra
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Icona carrello"
                ) // Icona a destra
            }
        }
    }
}
