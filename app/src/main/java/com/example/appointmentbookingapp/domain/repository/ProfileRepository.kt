package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val userRole: StateFlow<String>

    fun getCurrentUserId(): String?
    fun getCurrentUserName(): String?
    fun getCurrentEmail(): String?
    suspend fun getCurrentUserRole(): String?
    suspend fun getCurrentUserData(): User?
    fun isUserLoggedIn(): Boolean
    fun logOut()

    suspend fun loadUserRole()
    fun setUserRoleManually(role: String)

}
