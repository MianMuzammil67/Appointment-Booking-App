package com.example.appointmentbookingapp.presentation.ui.call

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.repository.CallRepository
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CallViewModel @Inject constructor(
    private val callRepository: CallRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _callStarted = MutableStateFlow(false)
    val callStarted: StateFlow<Boolean> = _callStarted.asStateFlow()

    fun observeCall(appointmentId: String) {
        callRepository.observeCallStarted(appointmentId) {
            _callStarted.value = true
        }
    }

    fun updatePeerId(appointmentId: String, role: String, peerId: String) = viewModelScope.launch {
        callRepository.updatePeerId(appointmentId, role, peerId)
    }

    fun getPatientPeerId(appointmentId: String, onResult: (String?) -> Unit) {
        Log.d("CallViewModel", "getPatientPeerId called with appointmentId: $appointmentId")
        callRepository.getPatientPeerId(appointmentId, onResult)
    }


    fun notifyServerCallStarted(appointmentId: String) {
        Log.d("CallViewModel", "startCall called with appointmentId: $appointmentId")
        viewModelScope.launch {
            callRepository.notifyServerCallStarted(appointmentId)
        }
    }

    fun notifyServerCallEnded(appointmentId: String) {
        Log.d("CallViewModel", "endCallCall called with appointmentId: $appointmentId")
        viewModelScope.launch {
            callRepository.notifyServerCallEnded(appointmentId)
        }
    }
}