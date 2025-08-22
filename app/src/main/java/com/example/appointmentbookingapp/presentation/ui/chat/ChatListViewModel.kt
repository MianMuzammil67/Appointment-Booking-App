package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatList = MutableStateFlow<Resource<List<ChatListItem>>>(Resource.Loading)
    val chatList: StateFlow<Resource<List<ChatListItem>>> = _chatList.asStateFlow()

    fun getChatList() = viewModelScope.launch {
        _chatList.value = Resource.Loading
        when(val result = chatRepository.getChatList()){
            is Resource.Success -> {
                _chatList.value = Resource.Success(result.data)
            }
            is Resource.Error -> {
                _chatList.value = Resource.Error(result.message)
            }
            else -> {}
        }

    }

}