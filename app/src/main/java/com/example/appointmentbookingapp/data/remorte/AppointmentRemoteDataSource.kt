package com.example.appointmentbookingapp.data.remorte

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class AppointmentRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {



    suspend fun getFirebaseServerTime(): LocalDate {
        val dummyRef = firestore.collection("time").document("server_time_temp")

        dummyRef.set(mapOf("timestamp" to FieldValue.serverTimestamp())).await()

        val snapshot = dummyRef.get().await()
        val timestamp = snapshot.getTimestamp("timestamp")

        return timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            ?: LocalDate.now()
    }

}