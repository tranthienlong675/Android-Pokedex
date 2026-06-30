package com.example.pokedex.services

import com.example.pokedex.client.PokeApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.pokedex.PokedexResponse
import com.example.pokedex.model.species.PokemonSpeciesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PokeApiService(
    private val client: HttpClient
) {
    suspend fun getList() =
        client.get(PokeApi.NATIONAL_POKEDEX).body<PokedexResponse>()

    suspend fun getSpecies(name: String) =
        client.get("${PokeApi.POKEMON_SPECIES}/$name").body<PokemonSpeciesResponse>()

    suspend fun getDetail(name: String) =
        client.get("${PokeApi.POKEMON}/$name").body<Pokemon>()
}