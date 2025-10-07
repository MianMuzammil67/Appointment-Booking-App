package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.DoctorExtras
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.util.Resource
import com.example.appointmentbookingapp.util.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val logTag = "AuthRemoteDataSource"

    suspend fun signUp(
        currUser: User,
        role: String,
        doctorExtras: DoctorExtras? = null
    ): Resource<Unit> {
        return try {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(currUser.email, currUser.password)
                .await()
            val user = authResult.user
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(currUser.name)
                .build()

            user?.updateProfile(profileUpdates)?.await()

            user?.let {
                when (role.lowercase()) {
                    UserRole.PATIENT -> {
                        savePatientToFirestore(it.uid, currUser.copy(id = it.uid, role = UserRole.PATIENT))
                    }
                    UserRole.DOCTOR -> {
                        if (doctorExtras == null) {
                            throw IllegalArgumentException("Doctor data is missing for role=doctor")
                        }
                        val doctorItem = DoctorItem(
                            id = it.uid,
                            name = currUser.name,
                            email = currUser.email,
                            password = currUser.password,
                            imageUrl = currUser.profileUrl, // yet to resolve problem of not saving photo on firebase
                            docCategory = doctorExtras.docCategory,
                            experienceYears = doctorExtras.experienceYears,
                            consultationFee = doctorExtras.consultationFee,
                            languagesSpoken = doctorExtras.languagesSpoken,
                            gender = doctorExtras.gender,
                            aboutDoctor = doctorExtras.aboutDoctor,
                            role = UserRole.DOCTOR
                        )

                        saveDoctorToFirestore(it.uid, doctorItem)
                    }

                    else -> {
                        throw IllegalArgumentException("Invalid role: $role")
                    }
                }
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.d(logTag, "Sign up failed: ${e.message}")
            Resource.Error(e.localizedMessage ?: "Sign up failed")
        }

    }
    suspend fun signIn(email: String, password: String): Resource<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            Log.d("AuthViewModel", "Sign in successfully.")
            Resource.Success(Unit)

        } catch (e: Exception) {
            Log.d(logTag, "Sign in : ${e.message}")
            Resource.Error(e.localizedMessage ?: "Sign in failed")
        }
    }

    private suspend fun savePatientToFirestore(userId: String, user: User) {
        try {
            firestore.collection("users")
                .document(userId)
                .set(user)
                .await()
            Log.d(logTag, "Patient data saved.")
        } catch (e: Exception) {
            Log.d(logTag, "Save patient failed: ${e.message}")
        }
    }

    private suspend fun saveDoctorToFirestore(userId: String, doctor: DoctorItem) {
        try {
            firestore.collection("doctors")
                .document(userId)
                .set(doctor)
                .await()
            Log.d(logTag, "Doctor data saved.")
        } catch (e: Exception) {
            Log.d(logTag, "Save doctor failed: ${e.message}")
        }
    }


}