package com.example.appointmentbookingapp.presentation.ui.aiLogic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.repository.AiLogicRepository
import com.google.firebase.ai.type.GenerateContentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiLogicViewModel @Inject constructor(
    private val repository: AiLogicRepository
) : ViewModel() {

    private val _result = MutableStateFlow<GenerateContentResponse?>(null)
    val result: StateFlow<GenerateContentResponse?> = _result

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getSuggestions(prompt: String) {

        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getSuggestions(prompt)
            _result.value = response

            Log.d("result", "result: ${response.text}")
            _isLoading.value = false

        }

    }

    fun resetLoading() {
        _isLoading.value = false
    }
}
