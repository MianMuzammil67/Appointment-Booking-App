package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AppointmentRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.ChatRemoteDataSource
import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.util.DateUtils
import com.example.appointmentbookingapp.util.Resource
import com.example.appointmentbookingapp.util.UserRole
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

    override suspend fun getChatList(role: String): Resource<List<ChatListItem>> {
        try {
            val conversations = chatRemoteDataSource.getConversations(role)

            val deferredChatItems = coroutineScope {
                conversations.map { conversation ->
                    async {
                        val otherUserId = if (role == UserRole.DOCTOR) {
                            conversation.patientId
                        } else {
                            conversation.doctorId
                        } ?: return@async null

                        val otherUser = when (role) {
                            UserRole.DOCTOR ->
                                appointmentRemoteDataSource.getPatientById(otherUserId)

                            else ->
                                appointmentRemoteDataSource.getDoctorById(otherUserId)
                        } ?: return@async null

                        ChatListItem(
                            id = otherUserId,
                            doctor = if (role != UserRole.DOCTOR) otherUser as? DoctorItem else null,
                            patient = if (role == UserRole.DOCTOR) otherUser as? User else null,
                            lastMessage = conversation.lastMessage,
                            timestamp = DateUtils.formatTimestamp(conversation.timestamp),
                            unreadCount = 0,
                        )

                    }

                }

            }
            val chatList = deferredChatItems.awaitAll().filterNotNull()
            return Resource.Success(chatList)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }

    override suspend fun deleteConversation(otherUserId: String, role: String) {
        return chatRemoteDataSource.deleteConversation(otherUserId, role)
    }

    override suspend fun deleteMessage(message: Message): Resource<Unit> {
        return chatRemoteDataSource.deleteMessage(message)
    }
}