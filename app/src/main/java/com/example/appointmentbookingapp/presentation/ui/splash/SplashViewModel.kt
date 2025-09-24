package com.example.appointmentbookingapp.presentation.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import com.example.appointmentbookingapp.util.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var startDestination by mutableStateOf("RoleSelectionScreen")
    var currentUserData: User? by mutableStateOf(null)

    var isLoading by mutableStateOf(true)

    init {
        loadUser()
    }
    private fun loadUser() = viewModelScope.launch {
        if (!profileRepository.isUserLoggedIn()) {
            currentUserData = null
            startDestination = "RoleSelectionScreen"
        } else {
            currentUserData = profileRepository.getCurrentUserData()

            Log.d("SplashViewModel", "Role: ${currentUserData?.role}")

            startDestination = when (currentUserData?.role) {
                UserRole.PATIENT -> "HomeScreen"
                UserRole.DOCTOR -> "DoctorHomeScreen"
                else -> "RoleSelectionScreen"
            }
        }

        isLoading = false
    }

}