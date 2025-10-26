package com.example.appointmentbookingapp.data.remorte


import android.util.Log
import com.example.appointmentbookingapp.presentation.ui.call.CallState
import com.example.appointmentbookingapp.util.UserRole
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class CallRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    val logTag = "CallRemoteDataSource"

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

    suspend fun updateCallState(appointmentId: String, state: CallState) {
        Log.d(logTag, "updateCallState: $appointmentId -> $state")

        firestore.collection("appointments")
            .document(appointmentId)
            .update(
                mapOf(
                    "callState" to state.name,
                    "lastUpdated" to FieldValue.serverTimestamp()
                )
            )
            .await()
    }

    fun observeCallState(
        appointmentId: String,
        onStateChanged: (CallState) -> Unit
    ): ListenerRegistration {
        return firestore.collection("appointments")
            .document(appointmentId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(logTag, "Error listening to call state", error)
                    return@addSnapshotListener
                }

                val stateValue = snapshot?.getString("callState")
                val state = try {
                    CallState.valueOf(stateValue ?: "NOT_STARTED")
                } catch (e: IllegalArgumentException) {
                    Log.e(logTag, "Invalid call state value: $e")
                    CallState.NOT_STARTED
                }

                Log.d(logTag, "Call state updated: $state")
                onStateChanged(state)
            }
    }

}
