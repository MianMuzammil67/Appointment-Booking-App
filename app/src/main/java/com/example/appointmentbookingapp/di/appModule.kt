package com.example.appointmentbookingapp.di

import com.example.appointmentbookingapp.data.remorte.AppointmentRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.AuthRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.ChatRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.FavoritesRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.HomeRemoteDataSource
import com.example.appointmentbookingapp.data.remorte.ProfileRemoteDataSource
import com.example.appointmentbookingapp.data.repository.AppointmentRepositoryImpl
import com.example.appointmentbookingapp.data.repository.AuthRepositoryImpl
import com.example.appointmentbookingapp.data.repository.ChatRepositoryImpl
import com.example.appointmentbookingapp.data.repository.FavoriteRepositoryImpl
import com.example.appointmentbookingapp.data.repository.HomeRepositoryImpl
import com.example.appointmentbookingapp.data.repository.ProfileRepositoryImp
import com.example.appointmentbookingapp.domain.repository.AppointmentRepository
import com.example.appointmentbookingapp.domain.repository.AuthRepository
import com.example.appointmentbookingapp.domain.repository.ChatRepository
import com.example.appointmentbookingapp.domain.repository.FavoriteRepository
import com.example.appointmentbookingapp.domain.repository.HomeRepository
import com.example.appointmentbookingapp.domain.repository.ProfileRepository
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

    @Singleton
    @Provides
    fun provideFavoriteRepository(remote: FavoritesRemoteDataSource): FavoriteRepository {
        return FavoriteRepositoryImpl(remote)
    }

    @Singleton
    @Provides
    fun provideAppointmentRepository(remote: AppointmentRemoteDataSource): AppointmentRepository {
        return AppointmentRepositoryImpl(remote)
    }

    @Singleton
    @Provides
    fun provideChatRepository(
        chatRemoteDataSource: ChatRemoteDataSource,
        appointmentRemoteDataSource: AppointmentRemoteDataSource
    ): ChatRepository {
        return ChatRepositoryImpl(chatRemoteDataSource, appointmentRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileRemoteDataSource: ProfileRemoteDataSource): ProfileRepository {
        return ProfileRepositoryImp(profileRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }

}