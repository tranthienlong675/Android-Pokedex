package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.ui.state.PokedexUiState
import com.example.pokedex.repositories.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val repository: PokemonRepository
): ViewModel() {
    private val _pokedexState = MutableStateFlow(PokedexUiState())
    val pokedexState: StateFlow<PokedexUiState> = _pokedexState

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val currentState = _pokedexState.value
        if (currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _pokedexState.update { it.copy(isLoading = true) }

            try {
                val data = repository.loadPage(currentState.page)

                _pokedexState.update {
                    it.copy(
                        pokemons = it.pokemons + data,
                        isLoading = false,
                        page = it.page + 1,
                        endReached = data.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _pokedexState.update {
                    it.copy(isLoading = true, error = e.message)
                }
                e.printStackTrace()
            }
        }
    }
}