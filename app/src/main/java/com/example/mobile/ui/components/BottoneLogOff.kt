package com.example.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.mobile.FirebaseService
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottoneLogOff(
    modifier: Modifier = Modifier,
    onLogOff: () -> Unit,
    firebaseService: FirebaseService,
) {
    ContenitoreOmbreggiato(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            //
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                firebaseService.signOut(onLogOff)
            },
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Log off",
                style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
                )
        }
    }
}
