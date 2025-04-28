package com.example.appointmentbookingapp.presentation.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.appointmentbookingapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _signUp = MutableStateFlow<AuthState>(AuthState.Initial)
    val signUpState = _signUp.asStateFlow()

    private val _signIn = MutableStateFlow<AuthState>(AuthState.Initial)
    val signInState = _signIn.asStateFlow()

    suspend fun signUp(name: String, email: String, password: String, profilePicture: String) {
        _signUp.value = AuthState.Loading
        try {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            val user = authResult.user

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            user?.updateProfile(profileUpdates)?.await()

            user.let {
                val userData = User(name, email, password, profilePicture)
                if (it != null) {
                    saveDataToFirestore(it.uid, userData)
                }
            }

            _signUp.value = AuthState.Success

        } catch (e: Exception) {
            _signUp.value = AuthState.Error
//            _signUp.value = AuthState.Error(e.message ?: "Unknown error")
        }

    }

    suspend fun signIn(email: String, password: String) {
        _signIn.value = AuthState.Loading
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            _signIn.value = AuthState.Success
        } catch (e: Exception) {
            _signUp.value = AuthState.Error

//            _signIn.value = AuthState.Error(e.message ?: "Unknown error")

        }
    }

    private suspend fun saveDataToFirestore(userId: String, user: User) {
        try {
            firestore.collection("users")
                .document(userId)
                .set(user)
                .await()
            Log.d("AuthViewModel", "User data successfully saved.")
        } catch (e: Exception) {
            e.message?.let { Log.d("AuthViewModel", it) }
        }

    }

}

sealed class AuthState {
    data object Initial : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data object Error : AuthState()
//    data class Success<T>(val data: T) : AuthState()
//    data class Error(val message: String) : AuthState()
}