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
    fun name(): String = name
        .replaceFirstChar { it.uppercase() }
        .replace('-', ' ')
        .split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}