package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AuthRemoteDataSource
import com.example.appointmentbookingapp.domain.model.DoctorExtras
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.AuthRepository
import com.example.appointmentbookingapp.util.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource) :
    AuthRepository {

    override suspend fun signUp(
        user: User,
        role: String,
        doctorExtras: DoctorExtras?
    ): Resource<Unit> {
        return authRemoteDataSource.signUp(user, role, doctorExtras)
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<Unit> {
        return authRemoteDataSource.signIn(email, password)
    }

}