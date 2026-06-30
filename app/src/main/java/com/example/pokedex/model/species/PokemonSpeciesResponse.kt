package com.example.pokedex.model.species

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesResponse(
    val varieties: List<PokemonVariety>
)
