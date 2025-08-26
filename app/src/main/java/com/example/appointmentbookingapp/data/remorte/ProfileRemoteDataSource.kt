package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val logTag = "ProfileRemoteDataSource"
    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
    }

    fun getCurrentUserName(): String? {
        Log.d(logTag, "getCurrentUserName called")
        return firebaseAuth.currentUser?.displayName
    }

    fun getCurrentEmail(): String? {
        Log.d(logTag, "getCurrentEmail called")
        return firebaseAuth.currentUser?.email
    }


    suspend fun getCurrentUserPhoto(): String {
        val userId = getCurrentUserId()
        Log.d(logTag, "Current user ID: $userId")

        try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()


            val profileUrl = snapshot.getString("profileUrl") ?: ""
            Log.d(logTag, "profileUrl: \"$profileUrl\"")

            return profileUrl
        } catch (e: Exception) {
            Log.d(logTag, "getCurrentUserPhoto error: ${e.message}")
        }

        return ""


        //        return firebaseAuth.currentUser?.photoUrl.toString()

    }

    fun logOut() {
        firebaseAuth.signOut()
    }

}