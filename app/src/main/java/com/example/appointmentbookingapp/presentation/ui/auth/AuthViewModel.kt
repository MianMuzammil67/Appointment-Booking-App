package com.example.appointmentbookingapp.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.DoctorExtras
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.domain.repository.AuthRepository
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUp = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val signUpState = _signUp.asStateFlow()

    private val _signIn = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val signInState = _signIn.asStateFlow()


    fun signUp(
        name: String,
        email: String,
        password: String,
        profilePicture: String,
        role: String,
        doctorExtras: DoctorExtras?
    ) = viewModelScope.launch {
        val userData = User(
            name = name,
            email = email,
            password = password,
            profileUrl = profilePicture,
            role = role
        )

        _signUp.value = UiState.Loading
        when (val result = authRepository.signUp(userData, role, doctorExtras)) {
            is Resource.Success -> {
                _signUp.value = UiState.Success(Unit)
            }

            is Resource.Error -> {
                _signUp.value = UiState.Error(result.message)
            }

            else -> {}
        }

    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _signIn.value = UiState.Loading

        when (val result = authRepository.signIn(email, password)) {
            is Resource.Success -> {
                _signIn.value = UiState.Success(result.data)
            }

            is Resource.Error -> {
                _signIn.value = UiState.Error(result.message)
            }

            else -> {}
        }

    }

}
