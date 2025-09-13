package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.DoctorExtras
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.util.Resource

interface AuthRepository {
    suspend fun signUp(user: User,role: String,doctorExtras: DoctorExtras?): Resource<Unit>
    suspend fun signIn(email: String, password: String): Resource<Unit>

}