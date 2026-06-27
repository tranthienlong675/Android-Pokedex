package com.example.pokedex

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.repositories.PokemonRepository
import com.example.pokedex.ui.state.PokemonDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState

    fun loadPokemon(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val pokemon = repository.getDetail(name)
                _uiState.update {
                    it.copy(
                        pokemon = pokemon,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
    fun setBackgroundColor(color: Color) {
        _uiState.update {
            it.copy(backgroundColor = color)
        }
    }
}