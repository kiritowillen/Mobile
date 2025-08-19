package com.example.mobile.ui.screens.pagamento.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.mobile.ui.components.ContenitoreOmbreggiato


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
            shape = RoundedCornerShape(1.dp),
            contentPadding = PaddingValues(0.dp) // Elimina padding standard
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), // margine interno ai lati
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text="Carrello",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    ) // Testo a sinistra
                Icon(
                    modifier = Modifier
                        .fillMaxHeight(0.7f),
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Icona carrello",
                    tint = MaterialTheme.colorScheme.onPrimary,
                ) // Icona a destra
            }
        }
    }
}
