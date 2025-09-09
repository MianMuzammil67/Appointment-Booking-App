package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.ProfileRemoteDataSource
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {
    override fun getCurrentUserId(): String? {
        return profileRemoteDataSource.getCurrentUserId()
    }

    override fun getCurrentUserName(): String? {
        return profileRemoteDataSource.getCurrentUserName()
    }

    override fun getCurrentEmail(): String? {
        return profileRemoteDataSource.getCurrentEmail()
    }

    override suspend fun getCurrentUserData(): User? {
        return profileRemoteDataSource.getCurrentUserData()
    }

    override fun isUserLoggedIn(): Boolean {
        return profileRemoteDataSource.isUserLoggedIn()
    }


    override fun logOut() = profileRemoteDataSource.logOut()

}