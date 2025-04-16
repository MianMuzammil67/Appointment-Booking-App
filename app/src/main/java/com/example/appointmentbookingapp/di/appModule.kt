package com.example.appointmentbookingapp.di

import com.example.appointmentbookingapp.data.remorte.HomeRemoteDataSource
import com.example.appointmentbookingapp.data.repository.HomeRepositoryImpl
import com.example.appointmentbookingapp.domain.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideHomeRepository(remote: HomeRemoteDataSource): HomeRepository {
       return HomeRepositoryImpl(remote)
    }


}