package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.pokedex.PokemonEntry
import com.example.pokedex.ui.state.PokedexUiState
import com.example.pokedex.repositories.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min

class PokedexViewModel(
    private val repository: PokemonRepository
): ViewModel() {

    private val pageSize = 30
    private var currentPage = 0

    private lateinit var allEntries: List<PokemonEntry>
    private val _pokedexState = MutableStateFlow(PokedexUiState())
    val pokedexState: StateFlow<PokedexUiState> = _pokedexState

    init {
        viewModelScope.launch {
            allEntries = repository.loadEntries()

            loadNextPage()
        }
    }

    fun loadNextPage() {
        val currentState = _pokedexState.value

        if (currentState.isLoading || currentState.endReached) return

        val end = min((currentPage + 1) * pageSize, allEntries.size)

        _pokedexState.update {
            it.copy(
                pokemons = allEntries.take(end),
                endReached = end == allEntries.size
            )
        }

        currentPage++
    }
}