package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AiLogicDataSource
import com.example.appointmentbookingapp.domain.repository.AiLogicRepository
import com.google.firebase.ai.type.GenerateContentResponse

class AiLogicRepositoryImpl(
    private val remote: AiLogicDataSource

) : AiLogicRepository {
    override suspend fun getSuggestions(prompt: String): GenerateContentResponse {
        return remote.getSuggestions(prompt)
    }
}