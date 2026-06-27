package com.example.pokedex.services

import com.example.pokedex.client.PokeApi
import com.example.pokedex.model.PokedexResponse
import com.example.pokedex.model.Pokemon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PokeApiService(
    private val client: HttpClient
) {
    suspend fun getList(limit: Int, offset: Int) =
        client.get(PokeApi.POKEMON) {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<PokedexResponse>()

    suspend fun getDetail(name: String) =
        client.get("${PokeApi.POKEMON}/$name").body<Pokemon>()
}