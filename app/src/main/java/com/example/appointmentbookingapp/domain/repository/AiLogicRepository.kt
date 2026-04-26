package com.example.appointmentbookingapp.domain.repository

import com.google.firebase.ai.type.GenerateContentResponse

interface AiLogicRepository {
    suspend fun getSuggestions(prompt: String): GenerateContentResponse
}