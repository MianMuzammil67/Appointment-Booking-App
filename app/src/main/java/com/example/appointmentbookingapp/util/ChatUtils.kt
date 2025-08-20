package com.example.appointmentbookingapp.util

object ChatUtils {
    fun generateChatId(userId1: String, userId2: String): String {
        return listOf(userId1, userId2).sorted().joinToString("_")
    }
}