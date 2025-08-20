package com.example.appointmentbookingapp.presentation.ui.sharedviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

// ⚠️ IMPORTANT for Hilt:
// Always add @Inject constructor() even if there are no parameters,
// otherwise Hilt can't generate the ViewModel and the build will fail!

@HiltViewModel
class DoctorChatSharedViewModel @Inject constructor(): ViewModel() {

    var doctorId by mutableStateOf("")

    fun updateDoctorId(id: String) {
        doctorId = id
    }
}