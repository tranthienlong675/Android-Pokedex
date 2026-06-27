package com.example.pokedex.repositories

import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.services.PokeApiService

class PokemonRepository(private val service: PokeApiService) {
    suspend fun loadPage(page: Int): List<PokedexEntry> {
        val res = service.getList(limit = 20, offset = page * 20)
        return res.results
    }
    suspend fun getDetail(name: String) =
        service.getDetail(name)
}