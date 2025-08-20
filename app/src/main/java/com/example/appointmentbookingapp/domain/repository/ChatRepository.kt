package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.Message
import com.google.firebase.firestore.ListenerRegistration

interface ChatRepository {

    fun getCurrentUserId(): String

    suspend fun sendMessage(message: Message)

    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration
}