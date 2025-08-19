package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    fun sendMessage(doctorId: String, message: Message) = viewModelScope.launch {
        chatRepository.sendMessage(doctorId,message)
    }

    fun listenToMessages(
        doctorId: String,
        patientId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ) {
        chatRepository.listenToMessages(doctorId, patientId, onMessagesChanged)
    }

}