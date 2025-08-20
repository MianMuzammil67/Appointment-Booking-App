package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.util.ChatUtils.generateChatId
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

//    val patientId = chatRepository.getCurrentUserId()
    val patientId: String by lazy { chatRepository.getCurrentUserId() }


    fun sendMessage(doctorId: String, messageContent: String) = viewModelScope.launch {

        val message = Message(
            messageId = UUID.randomUUID().toString(),
            chatId = generateChatId(patientId, doctorId),
            patientId = patientId,
            doctorId = doctorId,
            content = messageContent,
            timestamp = System.currentTimeMillis()
        )
        chatRepository.sendMessage(message)

    }

    var messageListener: ListenerRegistration? = null

    fun listenToMessages(
        doctorId: String,
    ) {
        val patientId = chatRepository.getCurrentUserId()
        messageListener?.remove()
        val chatId = generateChatId(doctorId, patientId)

        messageListener = chatRepository.listenToMessages(chatId) { newMessages ->
            _messages.value = newMessages
        }
    }

    override fun onCleared() {
        super.onCleared()
        messageListener?.remove()
    }

}