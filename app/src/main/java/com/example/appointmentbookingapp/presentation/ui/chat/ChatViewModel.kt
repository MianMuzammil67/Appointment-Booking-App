package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.util.ChatUtils.generateChatId
import com.example.appointmentbookingapp.util.UserRole
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    val currentUserId: String by lazy { chatRepository.getCurrentUserId() }

    fun sendMessage(receiverId: String, messageContent: String, currentUserRole: String) =
        viewModelScope.launch {
            val chatId = generateChatId(currentUserId, receiverId)

            val message = Message(
                messageId = UUID.randomUUID().toString(),
                chatId = chatId,
                senderId = currentUserId,
                receiverId = receiverId,
                patientId = if (currentUserRole == UserRole.PATIENT) currentUserId else receiverId,
                doctorId = if (currentUserRole == UserRole.DOCTOR) currentUserId else receiverId,
                content = messageContent,
                timestamp = System.currentTimeMillis()
            )
            chatRepository.sendMessage(message)

        }

    var messageListener: ListenerRegistration? = null

    fun listenToMessages(
        otherUserId: String,
    ) {
        messageListener?.remove()
        val chatId = generateChatId(otherUserId, currentUserId)
//        val chatId = generateChatId(currentUserId, otherUserId)
        messageListener = chatRepository.listenToMessages(chatId) { newMessages ->
            _messages.value = newMessages
        }
    }

    fun deleteMessage(message: Message) = viewModelScope.launch {
        chatRepository.deleteMessage(message)
    }

    override fun onCleared() {
        super.onCleared()
        messageListener?.remove()
    }

}