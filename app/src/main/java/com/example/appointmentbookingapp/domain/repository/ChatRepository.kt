package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.Message

interface ChatRepository {

    suspend fun sendMessage(doctorId: String, message: Message)
    fun listenToMessages(
        doctorId: String,
        patientId: String,
        onMessagesChanged: (List<Message>) -> Unit
    )

}