package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex.components.PokedexEntryCard
import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.ui.state.PokedexUiState

@Composable
fun PokedexScreen(
    modifier: Modifier = Modifier,
    state: PokedexUiState,
    onClick: (PokedexEntry, Color) -> Unit,
    onListStateReachEnd: () -> Unit
) {
    val listState = rememberLazyGridState()
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.pokemons.size) { index ->
            val pokemon = state.pokemons[index]
            PokedexEntryCard(pokemon, onClick)
        }
        if (state.isLoading) {
            item {
                Text("Loading...")
            }
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= state.pokemons.size - 3
                ) {
                    onListStateReachEnd()
                }
            }
    }
}