package com.example.appointmentbookingapp.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.presentation.ui.aiLogic.AiLogicViewModel
import com.example.appointmentbookingapp.presentation.ui.allCategories.AllDoctorCategories
import com.example.appointmentbookingapp.presentation.ui.allDoctors.DoctorScreen
import com.example.appointmentbookingapp.presentation.ui.appointment.AppointmentViewModel
import com.example.appointmentbookingapp.presentation.ui.appointment.BookAppointmentScreen
import com.example.appointmentbookingapp.presentation.ui.appointment.MyAppointments
import com.example.appointmentbookingapp.presentation.ui.auth.AuthViewModel
import com.example.appointmentbookingapp.presentation.ui.auth.SignInScreen
import com.example.appointmentbookingapp.presentation.ui.auth.SignupScreen
import com.example.appointmentbookingapp.presentation.ui.call.CallScreen
import com.example.appointmentbookingapp.presentation.ui.call.CallViewModel
import com.example.appointmentbookingapp.presentation.ui.call.WaitingRoomScreen
import com.example.appointmentbookingapp.presentation.ui.chat.ChatListScreen
import com.example.appointmentbookingapp.presentation.ui.chat.ChatListViewModel
import com.example.appointmentbookingapp.presentation.ui.chat.ChatScreen
import com.example.appointmentbookingapp.presentation.ui.chat.ChatViewModel
import com.example.appointmentbookingapp.presentation.ui.doctorDetail.DocDetailScreen
import com.example.appointmentbookingapp.presentation.ui.doctorHome.DoctorHomeScreen
import com.example.appointmentbookingapp.presentation.ui.doctorHome.DoctorHomeScreenn
import com.example.appointmentbookingapp.presentation.ui.favorite.FavoriteScreen
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.HomeScreen
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import com.example.appointmentbookingapp.presentation.ui.navigation.BottomNavigationBar
import com.example.appointmentbookingapp.presentation.ui.profile.CompleteProfileScreen
import com.example.appointmentbookingapp.presentation.ui.profile.ProfileScreen
import com.example.appointmentbookingapp.presentation.ui.profile.ProfileViewModel
import com.example.appointmentbookingapp.presentation.ui.roleselection.RoleSelectionScreen
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.AppointmentSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.DoctorChatSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.SharedCategoryViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.Routes
import com.example.appointmentbookingapp.util.UserRole

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(startDestination: String) {
    val navController = rememberNavController()

    val sharedDoctorViewModel: SharedDoctorViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val appointmentViewModel: AppointmentViewModel = hiltViewModel()
    val chatViewModel: ChatViewModel = hiltViewModel()
    val doctorChatSharedViewModel: DoctorChatSharedViewModel = hiltViewModel()
    val chatListViewModel: ChatListViewModel = hiltViewModel()
    val sharedCategoryViewModel: SharedCategoryViewModel = hiltViewModel()
    val roleSharedViewModel: UserRoleSharedViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val appointmentSharedViewModel: AppointmentSharedViewModel = hiltViewModel()
    val callViewModel: CallViewModel = hiltViewModel()
    val aiLogicViewModel: AiLogicViewModel = hiltViewModel()



    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val userRolee by roleSharedViewModel.userRole.collectAsState()

    LaunchedEffect(userRolee) {
        Log.d("MainApp", "UserRole: $userRolee")
    }

    val bottomBarScreens = if (userRolee == UserRole.DOCTOR) {
        listOf(Routes.DOCTOR_HOME_SCREEN, Routes.CHAT_LIST, Routes.MY_APPOINTMENTS, Routes.PROFILE)
    } else {
        listOf(Routes.HOME, Routes.CHAT_LIST, Routes.MY_APPOINTMENTS, Routes.PROFILE)
    }

//    val bottomBarScreens =
//        listOf("HomeScreen", "ChatListScreen", "MyAppointmentsScreen", "ProfileScreen")

    val showBottomBar = currentRoute in bottomBarScreens
    val bottomPadding = if (showBottomBar) 80.dp else 0.dp

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController, userRole = userRolee)
//                BottomNavigationBar(navController = navController)
            }
        }) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(Routes.SIGN_UP) { SignupScreen(navController, authViewModel, roleSharedViewModel) }
            composable(Routes.SIGN_IN) { SignInScreen(navController, authViewModel, roleSharedViewModel) }

            composable(Routes.HOME) {
                HomeScreen(
                    navController,
                    sharedDoctorViewModel,
                    homeViewModel,
                    favoriteViewModel,
                    profileViewModel,
                    sharedCategoryViewModel
                )
            }
            composable(Routes.DOCTOR_DETAIL) {
                DocDetailScreen(
                    navController,
                    sharedDoctorViewModel,
                    favoriteViewModel,
                    doctorChatSharedViewModel
                )
            }
            composable(Routes.BOOK_APPOINTMENT) {
                BookAppointmentScreen(navController, sharedDoctorViewModel)
            }
            composable(Routes.ALL_DOCTOR_CATEGORIES) {
                AllDoctorCategories(navController, homeViewModel, sharedCategoryViewModel)
            }
            composable(Routes.DOCTOR_SCREEN) {
                DoctorScreen(
                    navController,
                    homeViewModel,
                    sharedDoctorViewModel,
                    favoriteViewModel,
                    sharedCategoryViewModel
                )
            }
            composable(Routes.FAVORITE) {
                FavoriteScreen(navController, sharedDoctorViewModel, favoriteViewModel)
            }
            composable(Routes.PROFILE) {
                ProfileScreen(navController, profileViewModel)
            }
            composable(Routes.MY_APPOINTMENTS) {
                MyAppointments(
                    navController,
                    appointmentViewModel,
                    appointmentSharedViewModel,
                    roleSharedViewModel
                )
            }
            composable(Routes.CHAT_LIST) {
                ChatListScreen(navController, chatListViewModel, doctorChatSharedViewModel, roleSharedViewModel,aiLogicViewModel)
            }
            composable(Routes.CHAT) {
                ChatScreen(navController, chatViewModel, doctorChatSharedViewModel)
            }
            composable(Routes.DOCTOR_HOME) {
                DoctorHomeScreen(navController)
            }
            composable(Routes.ROLE_SELECTION) {
                RoleSelectionScreen(navController, roleSharedViewModel)
            }
            composable(Routes.COMPLETE_PROFILE) {
                CompleteProfileScreen(navController, authViewModel, roleSharedViewModel)
            }
            composable(Routes.DOCTOR_HOME_SCREEN) {
                DoctorHomeScreenn(navController)
            }
            composable(Routes.CALL) {
                CallScreen(
                    navController,
                    callViewModel,appointmentSharedViewModel,
                    roleSharedViewModel
                )
            }
            composable(Routes.WAITING_ROOM) {
                WaitingRoomScreen(
                    navController,
                    callViewModel,
                    appointmentSharedViewModel
                )
            }

        }
    }
}
