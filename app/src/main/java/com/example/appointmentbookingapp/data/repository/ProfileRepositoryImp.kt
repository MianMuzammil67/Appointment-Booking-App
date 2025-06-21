package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.ProfileRemoteDataSource
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override fun getCurrentUserName(): String? {
        return profileRemoteDataSource.getCurrentUserName()
    }

    override fun getCurrentEmail(): String? {
        return profileRemoteDataSource.getCurrentEmail()
    }

    override fun getCurrentUserPhoto(): String {
        return profileRemoteDataSource.getCurrentUserPhoto()
    }

}