package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.util.UserRole
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
        Log.d(logTag, "getCurrentUserId called : ${firebaseAuth.currentUser?.uid}" )
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

    suspend fun getCurrentUserData(role :String) {
        Log.d(logTag, "getCurrentUserData called")
        val userId = getCurrentUserId()
        Log.d(logTag, "Current user ID: $userId")
        try {
            if (role == UserRole.PATIENT) {
                val snapshot = firestore.collection("users")
                    .document(userId!!)
                    .get()
                    .await()

                val user = snapshot.toObject(User::class.java)
                Log.d(logTag, "userData: \"${user?.name}\"")
            }else{
                val snapshot = firestore.collection("doctors")
                    .document(userId!!)
                    .get()
                    .await()
                val user = snapshot.toObject(DoctorItem::class.java)
                Log.d(logTag, "userData: \"${user?.name}\"")
            }
        } catch (e: Exception) {
            Log.d(logTag, "getCurrentUserData error: ${e.message}")
        }
    }





//    suspend fun getCurrentUserData(): User? {
//        Log.d(logTag, "getCurrentUserData called")
//        val userId = getCurrentUserId() ?: return null
//        Log.d(logTag, "Current user ID: $userId")
//
//        try {
//            val snapshot = firestore.collection("users")
//                .document(userId)
//                .get()
//                .await()
//
//            val user = snapshot.toObject(User::class.java)
//            Log.d(logTag, "userData: \"${user?.name}\"")
//
//            return user
//        } catch (e: Exception) {
//            Log.d(logTag, "getCurrentUserData error: ${e.message}")
//        }
//
//        return null
//
//    }


    suspend fun getCurrentUserRole(): String? {
        Log.d(logTag, "getCurrentUserRole called")
        val userId = getCurrentUserId() ?: return null
        Log.d(logTag, "Current user ID: $userId")

        try {
            // check users collection
            val userSnapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            if (userSnapshot.exists()) {
                val role = userSnapshot.getString("role")
                Log.d(logTag, "Role from 'users': $role")
                return role
            }

            // check doctors collection
            val doctorSnapshot = firestore.collection("doctors")
                .document(userId)
                .get()
                .await()

            if (doctorSnapshot.exists()) {
                val role = doctorSnapshot.getString("role")
                Log.d(logTag, "Role from 'doctors': $role")
                return role
            }
        } catch (e: Exception) {
            Log.e(logTag, "getCurrentUserRole error: ${e.message}")
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