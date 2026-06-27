package com.example.pokedex.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<TypeSlot>,
    val weight: Int,
    val height: Int,
    val stats: List<StatSlot>
) {
    fun name(): String = name.replaceFirstChar { it.uppercase() }
    fun getType(): List<String> {
        return types.map { it.type.name }
    }
}


@Serializable
data class TypeSlot(
    val slot: Int,
    val type: TypeInfo
)

@Serializable
data class TypeInfo(
    val name: String,
    val url: String
)

@Serializable
data class StatSlot(
    val base_stat: Int,
    val effort: Int,
    val stat: StatInfo
)

@Serializable
data class StatInfo(
    val name: String,
    val url: String
)
