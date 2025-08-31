package com.example.mobile.ui.screens.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ServiceClasses.FirebaseService
import com.example.mobile.ui.sharedComponents.ContenitoreOmbreggiato

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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

            ) {

                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = "CONVERT",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    Modifier
                        .fillMaxHeight(0.05f)
                ) // <-- spazio tra titolo e bottone

                // Titolo in grossetto
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = "Bitcoiner app for Bitcoiners",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    Modifier
                        .fillMaxHeight(0.5f)
                ) // <-- spazio tra titolo e bottone

                // Pulsante Google Sign-In
                ContenitoreOmbreggiato(
                    modifier = Modifier
                        .fillMaxHeight(0.15f)
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 2.dp, vertical = 2.dp),
                ) {
                    Button(
                        onClick = {
                            Log.d("Firebase", "Cliccato pulsante")
                            // Forza la scelta dell'account
                            googleSignInClient.signOut().addOnCompleteListener {
                                // Dopo il logout, lancia l'intent di login
                                signInLauncher.launch(googleSignInClient.signInIntent)
                            }
                        },

                        modifier = Modifier.fillMaxSize(),
                        elevation = null,
                        shape = RoundedCornerShape(15.dp),
                        contentPadding = PaddingValues(0.dp) // Elimina padding predefinito
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = "Sign in with Google",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }


        }
    }
}
