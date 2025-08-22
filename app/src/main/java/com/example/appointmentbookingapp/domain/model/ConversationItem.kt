package com.example.appointmentbookingapp.domain.model

data class ConversationItem(
    val doctorId: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L
)