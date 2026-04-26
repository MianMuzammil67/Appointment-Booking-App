package com.example.appointmentbookingapp.data.remorte

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerateContentResponse
import com.google.firebase.ai.type.GenerativeBackend
import javax.inject.Inject

class AiLogicDataSource @Inject constructor(
) {

    suspend fun getSuggestions(prompt: String): GenerateContentResponse {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-3-flash-preview")

        val response = model.generateContent(prompt)
        return response
    }

}