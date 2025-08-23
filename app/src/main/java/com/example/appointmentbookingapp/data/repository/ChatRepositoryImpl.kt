package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AppointmentRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.ChatRemoteDataSource
import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.util.DateUtils
import com.example.appointmentbookingapp.util.Resource
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ChatRepositoryImpl(
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val appointmentRemoteDataSource: AppointmentRemoteDataSource
) : ChatRepository {

    override fun getCurrentUserId(): String {
        return chatRemoteDataSource.getCurrentUserId()
    }

    override suspend fun sendMessage(message: Message) {
        chatRemoteDataSource.sendMessage(message)
    }

    override fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {
        return chatRemoteDataSource.listenToMessages(chatId, onMessagesChanged)
    }

    override suspend fun getChatList(): Resource<List<ChatListItem>> {
        try {
            val conversations = chatRemoteDataSource.getConversations()

            val chatList = coroutineScope {
                conversations.map { conversation ->
                    async {
                        val doctor =
                            appointmentRemoteDataSource.getDoctorById(conversation.doctorId)
                        doctor?.let {
                            ChatListItem(
                                id = conversation.doctorId,
                                doctor = doctor,
                                lastMessage = conversation.lastMessage,
                                timestamp = DateUtils.formatTimestamp(conversation.timestamp),
                                unreadCount = 0,
                            )
                        }

                    }

                }

            }
            return Resource.Success(chatList.awaitAll().filterNotNull())
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
    }

    override suspend fun deleteConversation(doctorId: String) {
    return chatRemoteDataSource.deleteConversation(doctorId)
    }

}