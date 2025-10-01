package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class AppointmentRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    private val logTag: String = "AppointmentDataSource"

    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid?:""
    }

    suspend fun getFirebaseServerTime(): LocalDate {
        val dummyRef = firestore.collection("time").document("server_time_temp")
        dummyRef.set(mapOf("timestamp" to FieldValue.serverTimestamp())).await()
        val snapshot = dummyRef.get().await()
        val timestamp = snapshot.getTimestamp("timestamp")

        return timestamp?.toDate()?.toInstant()?.atZone(ZoneId.of("UTC"))?.toLocalDate()
            ?: LocalDate.now(ZoneId.of("UTC"))
    }

    suspend fun bookAppointment(appointment: Appointment) {
        try {
            val batch = firestore.batch()

            /**
             * - Full data: appointments/{appointmentId}
             * - Only IDs: users/{userId}/appointments/{appointmentId}
             *             doctors/{doctorId}/appointments/{appointmentId}
             *
             * - No data duplication
             * - Cleaner and simpler Firestore
             */

            val appointmentRef = firestore.collection("appointments")
                .document(appointment.appointmentId)

            val userRef = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("appointments")
                .document(appointment.appointmentId)

            val doctorRef = firestore.collection("doctors")
                .document(appointment.doctorId)
                .collection("appointments")
                .document(appointment.appointmentId)

            batch.set(appointmentRef, appointment)
            batch.set(userRef, mapOf("appointmentId" to appointment.appointmentId))
            batch.set(doctorRef, mapOf("appointmentId" to appointment.appointmentId))

            batch.commit().await()

        } catch (e: Exception) {
            Log.d(logTag, "bookAppointment: ${e.message}")
        }
    }

    suspend fun cancelAppointment(appointment: Appointment) {
        try {
            val batch = firestore.batch()

            val appointmentRef = firestore.collection("appointments")
                .document(appointment.appointmentId)

            val userRef = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("appointments")
                .document(appointment.appointmentId)

            val doctorRef = firestore.collection("doctors")
                .document(appointment.doctorId)
                .collection("appointments")
                .document(appointment.appointmentId)

            batch.delete(appointmentRef)
            batch.delete(userRef)
            batch.delete(doctorRef)

            batch.commit().await()

        } catch (e: Exception) {
            Log.d(logTag, "cancelAppointment: ${e.message}")
        }
    }


    suspend fun isTimeSlotAvailable(doctorId: String, date: LocalDate, time: String): Boolean {
        val startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        return try {
            val snapshot = firestore.collection("appointments")
                .whereEqualTo("doctorId", doctorId)
                .whereGreaterThanOrEqualTo("appointmentDate", startOfDay)
                .whereLessThan("appointmentDate", endOfDay)
                .whereEqualTo("time", time)
                .get()
                .await()
            Log.d(logTag, "isTimeSlotAvailable: ${snapshot.toString()}")
            snapshot.isEmpty // true if slot is free, false if taken

        } catch (e: Exception) {
            Log.e(logTag, "Error checking availability: ${e.message}")
            false // assume not available if error occurs
        }
    }

    suspend fun getNotAvailableSlots(doctorId: String, date: Date): List<String?> {
        val zoneId = ZoneId.of("UTC")
        val localDate = date.toInstant().atZone(zoneId).toLocalDate()

        val startOfDay = Date.from(localDate.atStartOfDay(zoneId).toInstant())
        val endOfDay = Date.from(localDate.plusDays(1).atStartOfDay(zoneId).toInstant())

        val snapshot = firestore.collection("appointments")
            .whereEqualTo("doctorId", doctorId)
            .whereGreaterThanOrEqualTo("appointmentDate", startOfDay)
            .whereLessThan("appointmentDate", endOfDay)
            .get()
            .await()
        return snapshot.documents.map { it.getString("timeSlot") }

    }

    suspend fun getMyAppointments(): List<Appointment?> {
        return try {

            val snapshot = firestore.collection("appointments")
                .whereEqualTo("patientId", getCurrentUserId())
                .orderBy("appointmentDate", Query.Direction.DESCENDING)
                .get().await()

            Log.d(logTag, snapshot.documents.toString())
            snapshot.documents.map { it.toObject(Appointment::class.java) }
        } catch (e: Exception) {
            Log.d(logTag, "getMyAppointments: ${e.message}")
            emptyList()
        }
    }

    suspend fun getMyAppointmentsAsDoctor(): List<Appointment?> {
        return try {
            val snapshot = firestore.collection("appointments")
                .whereEqualTo("doctorId", getCurrentUserId())
                .orderBy("appointmentDate", Query.Direction.DESCENDING)
                .get().await()
            Log.d(logTag, "getMyAppointmentsAsDoctor: ${snapshot.documents}")

            snapshot.documents.map { it.toObject(Appointment::class.java) }
        } catch (e: Exception) {
            Log.d(logTag, "getMyAppointmentsAsDoctor: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPatientById(patientId: String): User? {
        return try {
            val snapshot = firestore.collection("users")
                .document(patientId)
                .get().await()

            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }

    }
    suspend fun getDoctorById(doctorId: String): DoctorItem? {
        return try {
            val snapshot = firestore.collection("doctors")
                .document(doctorId)
                .get().await()

            snapshot.toObject(DoctorItem::class.java)
        } catch (e: Exception) {
            null
        }

    }
}