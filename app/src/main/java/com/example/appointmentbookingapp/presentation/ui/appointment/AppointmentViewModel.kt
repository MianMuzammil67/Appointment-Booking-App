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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val logTag: String = "AppointmentViewModel"

    private val _bookingState = MutableStateFlow<UiState<Unit>?>(null)
    val bookingState: StateFlow<UiState<Unit>?> = _bookingState

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _firebaseTimeFlow = MutableStateFlow<UiState<LocalDate>>(UiState.Loading)
    val firebaseTimeFlow = _firebaseTimeFlow.asStateFlow()

    private val _isSlotAvailable = MutableStateFlow<Boolean?>(null)
    val isSlotAvailable: StateFlow<Boolean?> = _isSlotAvailable

    private val _notAvailableSlots = MutableStateFlow<List<String?>>(emptyList())
    val notAvailableSlots: MutableStateFlow<List<String?>> = _notAvailableSlots

    private val _myAppointments = MutableStateFlow<UiState<List<Appointment?>>>(UiState.Loading)
    val myAppointments : StateFlow<UiState<List<Appointment?>>> = _myAppointments

    init {
        getFirebaseServerTime()
        getCurrentUserId()
        getMyAppointments()
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

    fun bookAppointment(appointment: Appointment) {
        viewModelScope.launch {
            _bookingState.value = UiState.Loading
            try {
                repository.bookAppointment(appointment)
                _bookingState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _bookingState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun isTimeSlotAvailable(doctorId: String, date: LocalDate, time: String) {
        viewModelScope.launch {
            try {
                _isSlotAvailable.value = repository.isTimeSlotAvailable(doctorId, date, time)
            } catch (e: Exception) {
                _isSlotAvailable.value = false
            }
        }
    }

    fun getNotAvailableSlots(doctorId: String, date: Date) = viewModelScope.launch {
        val result = repository.getNotAvailableSlots(doctorId, date)
        _notAvailableSlots.value = result

        Log.d("AppointmentViewModel", "getNotAvailableSlots: $result")

    }

    fun getMyAppointments() = viewModelScope.launch {
        _myAppointments.value = UiState.Loading
        try {
            val myAppointments = repository.getMyAppointments()
            _myAppointments.value = UiState.Success(myAppointments)
            Log.d(logTag, "getMyAppointments: $myAppointments")
        }catch (e: Exception){
            _myAppointments.value = UiState.Error(e.message ?: "Something went wrong")
        }
    }

}