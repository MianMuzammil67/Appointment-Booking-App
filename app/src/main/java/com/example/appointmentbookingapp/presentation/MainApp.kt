package com.example.appointmentbookingapp.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.presentation.ui.allCategories.AllDoctorCategories
import com.example.appointmentbookingapp.presentation.ui.allDoctors.DoctorScreen
import com.example.appointmentbookingapp.presentation.ui.appointment.BookAppointmentScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignInScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignupScreen
import com.example.appointmentbookingapp.presentation.ui.doctorDetail.DocDetailScreen
import com.example.appointmentbookingapp.presentation.ui.favorite.FavoriteScreen
import com.example.appointmentbookingapp.presentation.ui.home.HomeScreen
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import com.example.appointmentbookingapp.presentation.ui.navigation.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {
//    Surface (modifier = Modifier.fillMaxSize()){
    val navController = rememberNavController()

    val sharedDoctorViewModel: SharedDoctorViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    val currentUser = FirebaseAuth.getInstance().currentUser
    val start = if (currentUser != null) "HomeScreen" else "SignIn"

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = start,
//            Modifier.padding(innerPadding)


        ) {

//        NavHost(navController = navController, startDestination = start){

            composable("SignUp") {
                SignupScreen(navController)
            }
            composable("SignIn") {
                SignInScreen(navController)
            }
            composable("HomeScreen") {
                HomeScreen(navController, sharedDoctorViewModel, homeViewModel)
            }
            composable("DoctorDetail") {
                DocDetailScreen(navController, sharedDoctorViewModel)
            }
            composable("BookAppointment") {
                BookAppointmentScreen(navController, sharedDoctorViewModel)
            }
            composable("AllDoctorCategories") {
                AllDoctorCategories(navController, homeViewModel)
            }
            composable("DoctorScreen") {
                DoctorScreen(navController, homeViewModel, sharedDoctorViewModel)
            }
            composable("FavoriteScreen") {
                FavoriteScreen(navController)
            }
        }
    }
}