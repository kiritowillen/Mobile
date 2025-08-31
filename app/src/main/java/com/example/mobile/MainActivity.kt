package com.example.mobile

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ViewModel.ExternalNavigationViewModel
import com.example.mobile.navigation.ExternalNavGraph
import com.example.mobile.ui.theme.MyAppTheme

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {
    private val externalNavViewModel: ExternalNavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            MyAppTheme(darkTheme = isDarkTheme) { // 👈 Qui avvolgi tutta la UI
                val navController = rememberNavController()
                ExternalNavGraph(
                    navController = navController,
                    externalnNavViewModel = externalNavViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}