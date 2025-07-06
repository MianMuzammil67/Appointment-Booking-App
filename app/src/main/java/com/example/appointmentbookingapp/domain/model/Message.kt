package com.example.appointmentbookingapp.domain.model

data class Message(
    val id: String,
    val text: String,
    val isSentByMe: Boolean
)
