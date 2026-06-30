package com.example.pokedex.model.pokedex

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpecies(
    val name: String,
    val url: String
) {
    val index: Int
        get() = url.trimEnd('/')
            .split("/")
            .last()
            .toInt()
    fun name(): String = name.replaceFirstChar { it.uppercase() }
}