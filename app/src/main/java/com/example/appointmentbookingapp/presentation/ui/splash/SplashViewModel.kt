package com.example.appointmentbookingapp.presentation.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import com.example.appointmentbookingapp.util.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val userRole: StateFlow<String> = profileRepository.userRole

    var startDestination by mutableStateOf("RoleSelectionScreen")
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadUser()
    }

    private fun loadUser() = viewModelScope.launch {
        if (!profileRepository.isUserLoggedIn()) {
            startDestination = "RoleSelectionScreen"
        } else {
            profileRepository.loadUserRole()
            val role = profileRepository.userRole.value

            Log.d("SplashViewModel", "Role: $role")

            startDestination = when (role) {
                UserRole.PATIENT -> "HomeScreen"
                UserRole.DOCTOR -> "DoctorHomeScreenn"
                else -> "RoleSelectionScreen"
            }
        }

        isLoading = false

    }
}