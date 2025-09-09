package com.example.appointmentbookingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appointmentbookingapp.presentation.ui.splash.SplashViewModel
import com.example.appointmentbookingapp.ui.theme.appointmentbookingappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isLoading
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination = splashViewModel.startDestination
            if (!splashViewModel.isLoading) {
                appointmentbookingappTheme {
                    Log.d(
                        "MainActivity",
                        "Loading: ${splashViewModel.isLoading}, Destination: ${splashViewModel.startDestination}"
                    )
                    MainApp(startDestination = startDestination)
                }
            }
        }
    }
}
