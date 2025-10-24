package com.example.appointmentbookingapp.domain.repository
interface CallRepository {

    suspend fun notifyServerCallStarted(appointmentId: String)
    suspend fun notifyServerCallEnded(appointmentId: String)
    suspend fun updatePeerId (appointmentId: String, role: String, peerId: String)
    fun observeCallStarted(appointmentId: String, onStarted: () -> Unit)
    fun getPatientPeerId(appointmentId: String, onResult: (String?) -> Unit)
}