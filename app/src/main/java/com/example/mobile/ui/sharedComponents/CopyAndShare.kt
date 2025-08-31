package com.example.mobile.ui.sharedComponents

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R

@Composable
fun CopyAndShareTexts(walletSelezionato: MutableState<String>) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ---- COPIA ----
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ContenitoreOmbreggiato(
                modifier = Modifier
                    .clickable {
                        val testoDaCopiare = walletSelezionato.value
                        clipboardManager.setText(AnnotatedString(testoDaCopiare))
                        Toast.makeText(context, "Copiato: $testoDaCopiare", Toast.LENGTH_SHORT).show()
                    }
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.8f)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp) // 🔹 bordi tondi
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.copy),
                        contentDescription = "Icona Copia",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Copia",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                    )
                }
            }
        }

        // ---- CONDIVIDI ----
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End
        ) {
            ContenitoreOmbreggiato(
                modifier = Modifier
                    .clickable {
                        val testoDaCondividere = walletSelezionato.value
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, testoDaCondividere)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.8f)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp) // 🔹 bordi tondi
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Condividi",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "Icona Condividi",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }

}
