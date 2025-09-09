package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val logTag = "ProfileRemoteDataSource"
    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun getCurrentUserName(): String? {
        Log.d(logTag, "getCurrentUserName called")
        return firebaseAuth.currentUser?.displayName
    }

    fun getCurrentEmail(): String? {
        Log.d(logTag, "getCurrentEmail called")
        return firebaseAuth.currentUser?.email
    }

    suspend fun getCurrentUserData(): User? {
        Log.d(logTag, "getCurrentUserData called")
        val userId = getCurrentUserId() ?: return null
        Log.d(logTag, "Current user ID: $userId")

        try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)
            Log.d(logTag, "userData: \"${user?.name}\"")

            return user
        } catch (e: Exception) {
            Log.d(logTag, "getCurrentUserData error: ${e.message}")
        }

        return null

    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun logOut() {
        firebaseAuth.signOut()
    }

}