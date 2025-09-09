package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.User

interface ProfileRepository {
    fun getCurrentUserId(): String?
    fun getCurrentUserName(): String?
    fun getCurrentEmail(): String?
    suspend fun getCurrentUserData(): User?
    fun isUserLoggedIn(): Boolean
    fun logOut()
}
