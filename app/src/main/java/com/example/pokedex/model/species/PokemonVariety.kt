package com.example.pokedex.model.species

import com.example.pokedex.model.pokedex.PokemonSpecies
import kotlinx.serialization.Serializable

@Serializable
data class PokemonVariety(
    val is_default: Boolean,
    val pokemon: PokemonSpecies
)

