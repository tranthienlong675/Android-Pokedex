package com.example.pokedex.client

object PokeApi {
    const val BASE_URL = "https://pokeapi.co/api/v2"

    const val POKEMON = "$BASE_URL/pokemon"
    const val POKEMON_SPECIES = "$BASE_URL/pokemon-species"
    const val POKEDEX = "$BASE_URL/pokedex"

    const val OFFICIAL_ARTWORK_URL =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork"
}