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

    suspend fun getChatList(role: String): Resource<List<ChatListItem>>

    suspend fun deleteMessage(message: Message): Resource<Unit>

    suspend fun deleteConversation(otherUserId: String, role: String)
}