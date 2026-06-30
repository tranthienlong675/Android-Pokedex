package com.example.pokedex.ui.state

import androidx.compose.ui.graphics.Color
import com.example.pokedex.model.Pokemon

data class PokemonDetailUiState(
    val pokemon: Pokemon? = null,
    val pokemonForms: List<Pokemon> = emptyList(),
    val backgroundColor: Color = Color.Gray,
    val isLoading: Boolean = false,
    val error: String? = null
)