package com.example.appointmentbookingapp.presentation.ui.sharedviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.appointmentbookingapp.util.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// ⚠️ IMPORTANT for Hilt:
// Always add @Inject constructor() even if there are no parameters,
// otherwise Hilt can't generate the ViewModel and the build will fail!

@HiltViewModel
class UserRoleSharedViewModel @Inject constructor(): ViewModel() {

    val logTag = "RoleViewModel"

    private var _userRole = MutableStateFlow<String> (UserRole.NONE)
    val userRole:StateFlow<String> = _userRole

    fun setUserRole (role :String){
        if (role in UserRole.VALID_ROLES) {
            Log.d(logTag , "current User role = $role")
            _userRole.value = role
        } else {
            throw IllegalArgumentException("Invalid user role: $role")
        }
    }


}