package com.example.appointmentbookingapp.domain.model

data class Message(
    val messageId: String = "",
    val chatId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
