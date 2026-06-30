package com.example.pokedex.model.pokedex

import kotlinx.serialization.Serializable

@Serializable
data class PokemonEntry(
    val pokemon_species: PokemonSpecies
)

