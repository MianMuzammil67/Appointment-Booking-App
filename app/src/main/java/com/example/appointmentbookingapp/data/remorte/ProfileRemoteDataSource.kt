package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
){
    private val logTag = "ProfileRemoteDataSource"

     fun getCurrentUserName(): String? {
        Log.d(logTag, "getCurrentUserName called")
        return firebaseAuth.currentUser?.displayName
    }
    fun getCurrentEmail(): String? {
        Log.d(logTag, "getCurrentEmail called")
        return firebaseAuth.currentUser?.email
    }
    fun getCurrentUserPhoto(): String {
        Log.d(logTag, "getCurrentUserPhoto called")
        return firebaseAuth.currentUser?.photoUrl.toString()
    }


}