package com.example.mobile.ui.screens.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.FirebaseService

@Composable
fun LoginScreen(
    firebaseService: FirebaseService,
    onLoginSuccess: () -> Unit
) {
    val googleSignInClient = remember {
        firebaseService.getGoogleSignInClient()
    }

    val signInLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("Firebase", "Entrato in handleGoogleSignInResult")
        firebaseService.handleGoogleSignInResult(
            data = result.data,
            onSuccess = onLoginSuccess,
            onFailure = { e -> e.printStackTrace() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                Log.d("Firebase", "Cliccato puslante")
                signInLauncher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Sign in with Google")
        }
    }
}