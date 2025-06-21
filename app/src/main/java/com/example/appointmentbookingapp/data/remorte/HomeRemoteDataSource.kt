package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private val logTag = "HomeRemoteDataSource"

    suspend fun getBanners(): List<BannerItem> {
        val snapshot = firestore.collection("banner").get().await()
        return snapshot.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject(
                BannerItem::class.java
            )
        }
    }

    suspend fun getSpecializationCategory(): List<DoctorCategory> {
        Log.d(logTag, "API CALL → getSpecializationCategory()")
        return try {
            val snapshot = firestore.collection("doctorCategories").get().await()
            snapshot.documents.mapNotNull {
                it.toObject(DoctorCategory::class.java)
            }
        } catch (e: Exception) {
            Log.d(logTag, "getSpecializationCategory: catch  ${e.message}")
            emptyList()
        }

    }
    suspend fun getDoctors(): List<DoctorItem> {
        val snapshot = firestore.collection("doctors").get().await()
        return snapshot.documents.mapNotNull { it.toObject(DoctorItem::class.java) }

    }

}