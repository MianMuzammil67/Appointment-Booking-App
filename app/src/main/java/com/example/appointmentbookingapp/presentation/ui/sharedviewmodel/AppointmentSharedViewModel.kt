package com.example.appointmentbookingapp.presentation.ui.sharedviewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AppointmentSharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedAppointmentId = MutableStateFlow<String?>(null)
    val selectedAppointmentId: StateFlow<String?> = _selectedAppointmentId.asStateFlow()

    fun setAppointmentId(id: String) {
        _selectedAppointmentId.value = id
    }
}
