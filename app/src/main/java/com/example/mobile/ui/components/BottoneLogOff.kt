package com.example.mobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottoneLogOff(
    modifier: Modifier = Modifier,
    onLogOff: () -> Unit
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                FirebaseAuth.getInstance().signOut()
                onLogOff()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "LOG OFF")
        }
    }
}
