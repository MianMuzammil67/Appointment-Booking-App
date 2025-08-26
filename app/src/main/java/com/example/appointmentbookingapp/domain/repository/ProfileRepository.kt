package com.example.appointmentbookingapp.domain.repository

interface ProfileRepository {
    fun getCurrentUserId(): String
    fun getCurrentUserName(): String?
    fun getCurrentEmail(): String?
    suspend fun getCurrentUserPhoto(): String?
    fun logOut()
}
