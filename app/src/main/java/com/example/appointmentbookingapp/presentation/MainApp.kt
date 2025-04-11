package com.example.appointmentbookingapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.presentation.ui.appointment.BookAppointmentScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignInScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignupScreen
import com.example.appointmentbookingapp.presentation.ui.doctorDetail.DocDetailScreen
import com.example.appointmentbookingapp.presentation.ui.homescreen.HomeScreen

@Composable
fun MainApp() {
    Surface (modifier = Modifier.fillMaxSize()){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "SignUp"){

            composable("SignUp"){
                SignupScreen(navController)
            }
            composable("SignIn"){
                SignInScreen(navController)
            }
            composable("HomeScreen"){
                HomeScreen(navController)
            }
            composable("DoctorDetail"){
                DocDetailScreen(navController)
            }
            composable("BookAppointment"){
                BookAppointmentScreen(navController)
            }


        }

    }
}