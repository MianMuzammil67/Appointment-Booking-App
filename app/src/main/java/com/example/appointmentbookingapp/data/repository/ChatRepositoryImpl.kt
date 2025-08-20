package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.ChatRemoteDataSource
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.google.firebase.firestore.ListenerRegistration

class ChatRepositoryImpl(
    private val remoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    override fun getCurrentUserId(): String {
        return remoteDataSource.getCurrentUserId()
    }

    override suspend fun sendMessage(message: Message) {
        remoteDataSource.sendMessage(message)
    }

    override fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {
        return remoteDataSource.listenToMessages(chatId, onMessagesChanged)
    }

}