package com.example.appointmentbookingapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.presentation.ui.allCategories.AllDoctorCategories
import com.example.appointmentbookingapp.presentation.ui.allDoctors.DoctorScreen
import com.example.appointmentbookingapp.presentation.ui.appointment.AppointmentViewModel
import com.example.appointmentbookingapp.presentation.ui.appointment.BookAppointmentScreen
import com.example.appointmentbookingapp.presentation.ui.appointment.MyAppointments
import com.example.appointmentbookingapp.presentation.ui.auth.SignInScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignupScreen
import com.example.appointmentbookingapp.presentation.ui.chat.ChatListScreen
import com.example.appointmentbookingapp.presentation.ui.chat.ChatScreen
import com.example.appointmentbookingapp.presentation.ui.doctorDetail.DocDetailScreen
import com.example.appointmentbookingapp.presentation.ui.favorite.FavoriteScreen
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.HomeScreen
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import com.example.appointmentbookingapp.presentation.ui.navigation.BottomNavigationBar
import com.example.appointmentbookingapp.presentation.ui.profile.ProfileScreen
import com.example.appointmentbookingapp.presentation.ui.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp() {
    val navController = rememberNavController()

    val sharedDoctorViewModel: SharedDoctorViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val appointmentViewModel: AppointmentViewModel = hiltViewModel()


    val currentUser = FirebaseAuth.getInstance().currentUser
    val start = if (currentUser != null) "HomeScreen" else "SignIn"

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomBarScreens =
        listOf("HomeScreen", "ChatListScreen", "MyAppointmentsScreen",  "ProfileScreen")
    val showBottomBar = currentRoute in bottomBarScreens
    val bottomPadding = if (showBottomBar) 80.dp else 0.dp

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = start,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable("SignUp") { SignupScreen(navController) }
            composable("SignIn") { SignInScreen(navController) }
            composable("HomeScreen") {
                HomeScreen(
                    navController,
                    sharedDoctorViewModel,
                    homeViewModel,
                    favoriteViewModel,
                    profileViewModel
                )
            }
            composable("DoctorDetail") {
                DocDetailScreen(navController, sharedDoctorViewModel, favoriteViewModel)
            }
            composable("BookAppointment") {
                BookAppointmentScreen(navController, sharedDoctorViewModel)
            }
            composable("AllDoctorCategories") {
                AllDoctorCategories(navController, homeViewModel)
            }
            composable("DoctorScreen") {
                DoctorScreen(navController, homeViewModel, sharedDoctorViewModel, favoriteViewModel)
            }
            composable("FavoriteScreen") {
                FavoriteScreen(navController, sharedDoctorViewModel, favoriteViewModel)
            }
            composable("ProfileScreen") {
                ProfileScreen(profileViewModel)
            }
            composable("MyAppointmentsScreen") {
                MyAppointments(navController, appointmentViewModel)
            }
            composable("ChatListScreen") {
                ChatListScreen(navController)
            }
            composable("ChatScreen") {
                ChatScreen(navController)
            }
        }
    }
}
