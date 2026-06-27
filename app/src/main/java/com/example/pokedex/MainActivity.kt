package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokedex.ui.theme.PokedexTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.screens.PokedexScreen
import com.example.pokedex.ui.screens.PokemonDetailScreen
import com.example.pokedex.utils.getColorByType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(modifier = Modifier
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                    )
                }
            }
        }
    }
}

enum class ScreenNavigation {
    Main, Detail
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    pokedexVM: PokedexViewModel = koinViewModel(),
    pokemonDetailVM: PokemonDetailViewModel = koinViewModel()
) {
    val pokedexState by pokedexVM.pokedexState.collectAsState()
    val pokemonState by pokemonDetailVM.uiState.collectAsState()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Main.name,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
    ) {
        composable(route = ScreenNavigation.Main.name) {
            PokedexScreen(modifier = modifier, state = pokedexState,
                onClick = { pokemon, color ->
                    pokemonDetailVM.setBackgroundColor(color)
                    pokemonDetailVM.loadPokemon(pokemon.name)
                    navController.navigate(ScreenNavigation.Detail.name)
                },
                onListStateReachEnd = { pokedexVM.loadNextPage() }
            )
        }
        composable(route = ScreenNavigation.Detail.name) {
            PokemonDetailScreen(modifier = modifier, state = pokemonState)
        }
    }
}

@Preview
@Composable
fun Preview() {
    Text(text = "Poke type", color =getColorByType("fire"))
}