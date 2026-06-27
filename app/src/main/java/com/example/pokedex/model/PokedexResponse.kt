package com.example.pokedex.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class PokedexResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokedexEntry>
)

@Serializable
data class PokedexEntry(
    val name: String,
    val url: String,
) {
    val index: Int
        get() = url.trimEnd('/')
            .split("/")
            .last()
            .toInt()
    fun name(): String = name.replaceFirstChar { it.uppercase() }
}
