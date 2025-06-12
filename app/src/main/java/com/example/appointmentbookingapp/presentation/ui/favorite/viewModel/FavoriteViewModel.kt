package com.example.appointmentbookingapp.presentation.ui.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.repository.FavoriteRepository
import com.example.appointmentbookingapp.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val favoritesState: StateFlow<UiState<List<DoctorItem>>> = _favoritesState

    fun addToFavorites(doctorItem: DoctorItem) {
        viewModelScope.launch {
            favoriteRepository.addToFavorites(doctorItem)
        }
    }

    fun removeFromFavorites(doctorItem: DoctorItem) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(doctorItem)
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            _favoritesState.value = UiState.Loading
            try {
                val favorites = favoriteRepository.getFavorites()
                _favoritesState.value = UiState.Success(favorites)

            } catch (e: Exception) {
                _favoritesState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun isDoctorFavorite(doctorId: String) {
        viewModelScope.launch {
            favoriteRepository.isDoctorFavorite(doctorId)
        }
    }
}