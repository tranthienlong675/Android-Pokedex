package com.example.pokedex.components

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.asDrawable
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import com.example.pokedex.client.PokeApi
import com.example.pokedex.model.pokedex.PokemonSpecies
import com.example.pokedex.ui.theme.PokedexTheme
import org.koin.core.definition.indexKey

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    index: Int,
    name: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader

    var dominantColor by remember { mutableStateOf(Color.Gray) }

    val imageUrl =
        "${PokeApi.OFFICIAL_ARTWORK_URL}/${index}.png"

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

    Card(
        colors = CardDefaults.cardColors(
            containerColor = dominantColor
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            if (!name.isNullOrEmpty()) {
                val isDark = dominantColor.luminance() < 0.5f

                val textColor = if (isDark) {
                    Color(0xFFFAFAFA)
                } else {
                    Color(0xFF121212)
                }
                Text(
                    text = name,
                    color = textColor
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CardPreview() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val index = 254
        PokemonCard(
            modifier = Modifier.fillMaxWidth().height(240.dp),
            index = index,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = "${PokeApi.OFFICIAL_ARTWORK_URL}/$index.png",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(210.dp)
                )
            }
        }
    }
}