package com.example.appointmentbookingapp.presentation.ui.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.repository.AppointmentRepository
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _firebaseTimeFlow = MutableStateFlow<UiState<LocalDate>>(UiState.Loading)
    val firebaseTimeFlow = _firebaseTimeFlow.asStateFlow()

    init {
        getFirebaseServerTime()
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        _currentUserId.value = repository.getCurrentUserId()
    }


    private fun getFirebaseServerTime() = viewModelScope.launch {
        Log.d("AppointmentViewModel", "getFirebaseServerTime is called")
        _firebaseTimeFlow.value = UiState.Loading

        when (val time = repository.getFirebaseServerTime()) {
            is Resource.Success -> {
                _firebaseTimeFlow.value = UiState.Success(time.data)
            }

            is Resource.Error -> {
                _firebaseTimeFlow.value = UiState.Error(time.message)
            }

            else -> {}
        }
    }

    fun bookAppointment(appointment: Appointment) = viewModelScope.launch {
        Log.d("AppointmentViewModel", "bookAppointment is called")
        repository.bookAppointment(appointment)
    }


}