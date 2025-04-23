package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.Appointment
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

    private val logTag: String = "AppointmentDataSource"

    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid.toString()
    }

    suspend fun getFirebaseServerTime(): LocalDate {
        val dummyRef = firestore.collection("time").document("server_time_temp")
        dummyRef.set(mapOf("timestamp" to FieldValue.serverTimestamp())).await()
        val snapshot = dummyRef.get().await()
        val timestamp = snapshot.getTimestamp("timestamp")

        return timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            ?: LocalDate.now()
    }

    suspend fun bookAppointment(appointment: Appointment) {
        try {
            val batch = firestore.batch()
            val userRef = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("Appointments")
                .document(appointment.appointmentId)
//            .set(appointment).await()

            val doctorRef = firestore.collection("doctors")
                .document(appointment.doctorId).collection("Appointments")
                .document(appointment.appointmentId)
//                .set(appointment).await()
            batch.set(userRef,appointment)
            batch.set(doctorRef,appointment)
            batch.commit().await()

        } catch (e: Exception) {
            Log.d(logTag, "bookAppointment: ${e.message}")
        }
    }

}