package com.example.mobile.ui.components
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CampoImporto(
    modifier: Modifier,
    valore: String,
    onValoreChange: (String) -> Unit
) {

    ContenitoreOmbreggiato(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 12.dp, vertical = 8.dp)

            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Importo")

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, end = 12.dp),
                contentAlignment = Alignment.Center
            ) {

                BasicTextField(
                    value = valore,
                    onValueChange = { nuovoValore ->
                        Log.d("Debug", "Nuovo valore digitato: $nuovoValore")
                        if (nuovoValore.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            onValoreChange(nuovoValore)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (valore.isEmpty()) {
                                Text(
                                    text = "0",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}
