package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.ChatRemoteDataSource
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository

class ChatRepositoryImp(
    private val remoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    override suspend fun sendMessage(
        doctorId: String,
        message: Message
    ) {
        remoteDataSource.sendMessage(doctorId, message)
    }

    override fun listenToMessages(
        doctorId: String,
        patientId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ) {
        remoteDataSource.listenToMessages(doctorId, patientId, onMessagesChanged)
    }


}