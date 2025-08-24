package com.example.appointmentbookingapp.presentation.ui.profile

import androidx.lifecycle.ViewModel
import com.example.appointmentbookingapp.data.repository.ProfileRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepositoryImp
) : ViewModel() {

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl: StateFlow<String?> = _photoUrl

    init {
        getCurrentUserName()
        getCurrentEmail()
        getCurrentUserPhoto()
    }


    private fun getCurrentUserName() {
        _userName.value = profileRepository.getCurrentUserName()
    }

    private fun getCurrentEmail() {
        _userEmail.value = profileRepository.getCurrentEmail()
    }

    private fun getCurrentUserPhoto() {
        _photoUrl.value = profileRepository.getCurrentUserPhoto()
    }

    fun logOut() = profileRepository.logOut()
}