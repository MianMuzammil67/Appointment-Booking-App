package com.example.appointmentbookingapp.data.remorte

import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid.toString()
    }

    suspend fun addToFavorites(doctorItem: DoctorItem) {
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("favorites")
            .document(doctorItem.id)
            .set(doctorItem).await()
    }

    suspend fun removeFromFavorites(doctorItem: DoctorItem) {
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("favorites")
            .document(doctorItem.id)
            .delete().await()

    }

    suspend fun getFavorites(): List<DoctorItem> {
        val snapshot = firestore.collection("users")
            .document(getCurrentUserId())
            .collection("favorites").get().await()
        return snapshot.documents.mapNotNull { it.toObject(DoctorItem::class.java) }
    }


    suspend fun isDoctorFavorite(doctorId: String): Boolean {
        val ref = firestore.collection("users")
            .document(getCurrentUserId())
            .collection("favorites")
            .document(doctorId).get().await()
        return ref.exists()
    }


}