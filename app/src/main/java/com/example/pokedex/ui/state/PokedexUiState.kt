package com.example.pokedex.ui.state

import com.example.pokedex.model.pokedex.PokemonEntry

data class PokedexUiState(
    val pokemons: List<PokemonEntry> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)