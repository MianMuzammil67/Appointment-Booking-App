package com.example.appointmentbookingapp.data.remorte

import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.CategoryItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val logTag: String = HomeRemoteDataSource::class.java.simpleName

    fun getCurrentUserName(): String? {
        return firebaseAuth.currentUser?.displayName
    }

    fun getCurrentUserPhoto(): String {
        return firebaseAuth.currentUser?.photoUrl.toString()
    }

    suspend fun getBanners(): List<BannerItem> {
        val snapshot = firestore.collection("banner").get().await()
        return snapshot.documents.mapNotNull { documentSnapshot ->
            documentSnapshot.toObject(
                BannerItem::class.java
            )
        }

    }

    suspend fun getDoctorCategories(): List<CategoryItem> {
        val snapshot = firestore.collection("doctorCategories").get().await()
        return snapshot.documents.mapNotNull { it.toObject(CategoryItem::class.java) }

    }

    suspend fun getDoctors(): List<DoctorItem> {
        val snapshot = firestore.collection("doctors").get().await()
        return snapshot.documents.mapNotNull { it.toObject(DoctorItem::class.java) }

    }


}