package com.example.pokedex.ui.state

import com.example.pokedex.model.PokedexEntry

data class PokedexUiState(
    val pokemons: List<PokedexEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 0,
    val endReached: Boolean = false
)