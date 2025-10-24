package com.example.appointmentbookingapp.data.remorte


import android.util.Log
import com.example.appointmentbookingapp.util.UserRole
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class CallRemoteDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun notifyServerCallStarted(appointmentId: String) {
        Log.d("CallRemoteDataSource", "startCall: $appointmentId")
        firestore.collection("appointments")
            .document(appointmentId)
            .update("callStarted", true)
            .await()
    }
    suspend fun notifyServerCallEnded(appointmentId: String) {
        Log.d("CallRemoteDataSource", "startCall: $appointmentId")
        firestore.collection("appointments")
            .document(appointmentId)
            .update("callStarted", false)
            .await()
    }

    suspend fun updatePeerId(appointmentId: String, role: String, peerId: String) {
        val field = when (role.lowercase()) {
            UserRole.DOCTOR -> "doctorPeerId"
            UserRole.PATIENT -> "patientPeerId"
            else -> throw IllegalArgumentException("Unknown role: $role")
        }

        firestore.collection("appointments")
            .document(appointmentId)
            .update(field, peerId)
            .await()
    }

    fun getPatientPeerId(appointmentId: String, onResult: (String?) -> Unit) {
        firestore.collection("appointments")
            .document(appointmentId)
            .get()
            .addOnSuccessListener { doc ->
                val peerId = doc.getString("patientPeerId")
                onResult(peerId)
            }
    }


    fun observeCallStarted(appointmentId: String, onStarted: () -> Unit) {
        firestore.collection("appointments")
            .document(appointmentId)
            .addSnapshotListener { snapshot, _ ->
                val started = snapshot?.getBoolean("callStarted") ?: false
                if (started) onStarted()
            }
    }

}
