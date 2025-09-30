package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.ProfileRemoteDataSource
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
import com.example.appointmentbookingapp.util.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {
    private val _userRole = MutableStateFlow(UserRole.NONE)
    override val userRole: StateFlow<String> = _userRole.asStateFlow()

    override suspend fun loadUserRole() {
        val role = getCurrentUserRole() ?: UserRole.NONE
        _userRole.value = role
    }

    override fun setUserRoleManually(role: String) {
        _userRole.value = role
    }


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
    override suspend fun getCurrentUserRole(): String? {
        return profileRemoteDataSource.getCurrentUserRole()
    }

    override fun isUserLoggedIn(): Boolean {
        return profileRemoteDataSource.isUserLoggedIn()
    }

    override fun logOut() = profileRemoteDataSource.logOut()

}