package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.CallRemoteDataSource
import com.example.appointmentbookingapp.domain.repository.CallRepository
import com.example.appointmentbookingapp.presentation.ui.call.CallState
import com.google.firebase.firestore.ListenerRegistration

class CallRepositoryImpl(
    private val callRemoteDataSource: CallRemoteDataSource,
) : CallRepository {

    override suspend fun updatePeerId(
        appointmentId: String,
        role: String,
        peerId: String
    ) {
        callRemoteDataSource.updatePeerId(appointmentId, role, peerId)
    }

    override fun getPatientPeerId(
        appointmentId: String,
        onResult: (String?) -> Unit
    ) {
        callRemoteDataSource.getPatientPeerId(appointmentId, onResult)
    }

    override suspend fun updateCallState(appointmentId: String, state: CallState) {
        callRemoteDataSource.updateCallState(appointmentId, state)
    }

    override fun observeCallState(
        appointmentId: String,
        onStateChanged: (CallState) -> Unit
    ): ListenerRegistration {
        return callRemoteDataSource.observeCallState(appointmentId, onStateChanged)
    }
}