package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.presentation.ui.call.CallState
import com.google.firebase.firestore.ListenerRegistration

interface CallRepository {

    suspend fun updatePeerId(appointmentId: String, role: String, peerId: String)
    fun getPatientPeerId(appointmentId: String, onResult: (String?) -> Unit)
    suspend fun updateCallState(appointmentId: String, state: CallState)
    suspend fun updateCallStatus(appointmentId: String, status: String)
    fun observeCallState(
        appointmentId: String,
        onStateChanged: (CallState) -> Unit
    ): ListenerRegistration
}