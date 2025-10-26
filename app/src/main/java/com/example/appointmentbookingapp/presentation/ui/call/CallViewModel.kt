package com.example.appointmentbookingapp.presentation.ui.call

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.repository.CallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CallViewModel @Inject constructor(
    private val callRepository: CallRepository,
) : ViewModel() {

    private val _callState = MutableStateFlow(CallState.NOT_STARTED)
    val callState: StateFlow<CallState> = _callState.asStateFlow()


    fun updatePeerId(appointmentId: String, role: String, peerId: String) = viewModelScope.launch {
        callRepository.updatePeerId(appointmentId, role, peerId)
    }

    fun getPatientPeerId(appointmentId: String, onResult: (String?) -> Unit) {
        Log.d("CallViewModel", "getPatientPeerId called with appointmentId: $appointmentId")
        callRepository.getPatientPeerId(appointmentId, onResult)
    }

    fun updateCallState(appointmentId: String, state: CallState) = viewModelScope.launch {
        callRepository.updateCallState(appointmentId, state)
    }

    fun observeCallState(appointmentId: String) {
        callRepository.observeCallState(appointmentId) {
            _callState.value = it
        }
    }
}

enum class CallState {
    NOT_STARTED,
    WAITING,
    STARTED,
    ENDED
}
