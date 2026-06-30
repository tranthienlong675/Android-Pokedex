package com.example.pokedex.model.pokedex

import kotlinx.serialization.Serializable

@Serializable
data class PokedexResponse(
    val pokemon_entries: List<PokemonEntry>
)
