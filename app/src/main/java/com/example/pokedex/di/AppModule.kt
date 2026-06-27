package com.example.pokedex.di

import com.example.pokedex.PokedexViewModel
import com.example.pokedex.PokemonDetailViewModel
import com.example.pokedex.services.PokeApiService
import com.example.pokedex.client.fetchPokemonInfo
import com.example.pokedex.repositories.PokemonRepository
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

var appModule = module {
    single { fetchPokemonInfo() }
    single { PokeApiService(get()) }
    single { PokemonRepository(get()) }
    viewModel { PokemonDetailViewModel(get()) }
    viewModel { PokedexViewModel(get()) }
}