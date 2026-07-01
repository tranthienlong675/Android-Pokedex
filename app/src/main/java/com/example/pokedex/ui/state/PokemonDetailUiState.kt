package com.example.pokedex.ui.state

import com.example.pokedex.model.Pokemon

data class PokemonDetailUiState(
    val pokemon: Pokemon? = null,
    val pokemonForms: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)