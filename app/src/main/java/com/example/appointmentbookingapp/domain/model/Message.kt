package com.example.appointmentbookingapp.domain.model

data class Message(
    val messageId: String = "",
    val chatId: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
