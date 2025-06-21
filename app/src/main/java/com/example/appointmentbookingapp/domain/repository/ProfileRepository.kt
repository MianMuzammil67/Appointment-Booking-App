package com.example.appointmentbookingapp.domain.repository

interface ProfileRepository {
    fun getCurrentUserName(): String?
    fun getCurrentEmail(): String?
    fun getCurrentUserPhoto(): String?
}
