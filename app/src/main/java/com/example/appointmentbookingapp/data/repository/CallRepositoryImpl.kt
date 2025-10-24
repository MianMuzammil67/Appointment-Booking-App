package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.CallRemoteDataSource
import com.example.appointmentbookingapp.domain.repository.CallRepository

class CallRepositoryImpl(
    private val callRemoteDataSource: CallRemoteDataSource,
) : CallRepository {

    override suspend fun notifyServerCallStarted(appointmentId: String) {
        callRemoteDataSource.notifyServerCallStarted(appointmentId)
    }
    override suspend fun notifyServerCallEnded(appointmentId: String) {
        callRemoteDataSource.notifyServerCallEnded(appointmentId)
    }

    override suspend fun updatePeerId(
        appointmentId: String,
        role: String,
        peerId: String
    ) {
        callRemoteDataSource.updatePeerId(appointmentId, role, peerId)
    }

    override fun observeCallStarted(appointmentId: String, onStarted: () -> Unit) {
        callRemoteDataSource.observeCallStarted(appointmentId, onStarted)
    }

    override fun getPatientPeerId(
        appointmentId: String,
        onResult: (String?) -> Unit
    ) {
        callRemoteDataSource.getPatientPeerId(appointmentId, onResult)
    }
}