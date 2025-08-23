package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.util.Resource
import com.google.firebase.firestore.ListenerRegistration

interface ChatRepository {

    fun getCurrentUserId(): String

    suspend fun sendMessage(message: Message)

    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration

    suspend fun getChatList():  Resource<List<ChatListItem>>
    suspend fun deleteConversation(doctorId: String)
}