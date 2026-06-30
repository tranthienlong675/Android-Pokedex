package com.example.pokedex.repositories

import com.example.pokedex.model.pokedex.PokedexResponse
import com.example.pokedex.model.pokedex.PokemonEntry
import com.example.pokedex.model.pokedex.PokemonSpecies
import com.example.pokedex.model.species.PokemonVariety
import com.example.pokedex.services.PokeApiService

class PokemonRepository(private val service: PokeApiService) {
    private var entries: List<PokemonEntry>? = null

    suspend fun loadEntries(): List<PokemonEntry> {
        if (entries == null) {
            entries = service.getList().pokemon_entries
        }
        return entries!!
    }

    suspend fun getSpecies(name: String): List<PokemonVariety> {
        val res = service.getSpecies(name)
        return res.varieties
    }
    suspend fun getDetail(name: String) =
        service.getDetail(name)
}