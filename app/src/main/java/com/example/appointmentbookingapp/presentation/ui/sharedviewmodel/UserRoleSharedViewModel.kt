package com.example.appointmentbookingapp.presentation.ui.sharedviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import com.example.appointmentbookingapp.util.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ⚠️ IMPORTANT for Hilt:
// Always add @Inject constructor() even if there are no parameters,
// otherwise Hilt can't generate the ViewModel and the build will fail!

@HiltViewModel
class UserRoleSharedViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val userRole: StateFlow<String> = profileRepository.userRole

    init {
        viewModelScope.launch {
            profileRepository.loadUserRole()
        }
    }

    fun setUserRole(role: String) {
        if (role in UserRole.VALID_ROLES) {
            Log.d("UserRoleSharedViewModel", "current User role = $role")
            profileRepository.setUserRoleManually(role)
        } else {
            throw IllegalArgumentException("Invalid user role: $role")
        }
    }


}