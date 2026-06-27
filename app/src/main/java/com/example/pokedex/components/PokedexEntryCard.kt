package com.example.pokedex.components

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.asDrawable
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import com.example.pokedex.PokemonDetailViewModel
import com.example.pokedex.client.PokeApi
import com.example.pokedex.model.PokedexEntry
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokedexEntryCard(
    pokedexEntry: PokedexEntry,
    onClick: (PokedexEntry, Color) -> Unit,
    pokemonDetailVM: PokemonDetailViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader

    var dominantColor by remember { mutableStateOf(Color.Gray) }

    val imageUrl =
        "${PokeApi.OFFICIAL_ARTWORK_URL}/${pokedexEntry.index}.png"
    val pokemonState by pokemonDetailVM.uiState.collectAsState()
    LaunchedEffect(imageUrl) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(request)

        if (result is SuccessResult) {
            val bitmap = (result.image.asDrawable(context.resources)
                    as BitmapDrawable)
                .bitmap

            val palette = Palette.from(bitmap).generate()

            dominantColor = Color(
                palette.getDominantColor(
                    Color.Gray.toArgb()
                )
            )
        }
    }
    val isDark = dominantColor.luminance() < 0.5f

    val textColor = if (isDark) {
        Color(0xFFFAFAFA)
    } else {
        Color(0xFF121212)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = dominantColor
        ),
        modifier = Modifier.clickable(
            enabled = true,
            onClick = {
                onClick(pokedexEntry, dominantColor)
            }
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
            )
            Text(
                text = pokedexEntry.name(),
                color = textColor
            )
        }
    }
}