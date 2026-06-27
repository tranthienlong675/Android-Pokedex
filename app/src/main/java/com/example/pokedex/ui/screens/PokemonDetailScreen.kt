package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pokedex.client.PokeApi
import com.example.pokedex.components.StatBar
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.TypeInfo
import com.example.pokedex.model.TypeSlot
import com.example.pokedex.ui.state.PokemonDetailUiState
import com.example.pokedex.utils.getColorByType

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    state: PokemonDetailUiState,
) {
    var totalStats = 0
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.pokemon != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = state.backgroundColor
                ),
                modifier = Modifier.fillMaxWidth().height(240.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = "${PokeApi.OFFICIAL_ARTWORK_URL}/${state.pokemon.id}.png",
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(210.dp)
                    )
                }
            }
            Text(
                text = state.pokemon.name(),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                state.pokemon.getType().forEach {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(getColorByType(it), shape = RoundedCornerShape(24))
                                .padding(vertical = 4.dp)
                                .width(88.dp)
                        ) {
                            Text(
                                text = it.uppercase(),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "%.1f KG".format(state.pokemon.weight * 0.1),
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Weight",
                        color = Color(0xFFADADAD)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "%.1f M".format(state.pokemon.height * 0.1),
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Height",
                        color = Color(0xFFADADAD)
                    )
                }
            }

            Text(
                text = "Base Stats",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )

            state.pokemon.stats.forEach {
                StatBar(
                    it.stat.name,
                    it.base_stat
                )
                totalStats += it.base_stat
            }
            StatBar(
                "total",
                totalStats
            )
        } else {
            Text("Fail to load detail information.")
        }
        if (state.isLoading) {
            Text(text = "Loading...")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TestPreview() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${100}")
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${2000}")
        }
    }
}
