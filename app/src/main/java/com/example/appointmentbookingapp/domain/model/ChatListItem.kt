package com.example.appointmentbookingapp.domain.model

data class ChatListItem(
    val id: String,
    val doctor: DoctorItem?,
    val patient: User?,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
)