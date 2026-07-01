package com.example.pokedex.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val MAX_STAT = 255

@Composable
fun StatBar(
    statName: String,
    baseStat: Int
) {
    val shortenName = when (statName) {
        "special-attack" -> "sp.Atk"
        "special-defense" -> "sp.Def"
        else -> statName
    }
    val percent =
        if (statName == "total") 0f
        else if (baseStat >= 255) 1f
        else baseStat.toFloat() / MAX_STAT

    val exclusive = 1f - percent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = shortenName.replaceFirstChar { it.uppercase() },
            textAlign = TextAlign.Right,
            modifier = Modifier
                .weight(2f)
        )
        Text(
            text = "$baseStat",
            textAlign = TextAlign.Right,
            fontWeight =
            if (statName == "total") FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .weight(1.5f)
                .padding(end = 8.dp)
        )

        Row(
            modifier = Modifier.weight(7f)
        ) {
            Box(
                modifier =
                    if (percent == 0f) Modifier
                    else Modifier
                        .height(12.dp)
                        .weight(percent)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = getColorByStat(baseStat))
            )

            Box(
                modifier = if (exclusive > 0f) {
                    Modifier.weight(exclusive)
                } else {
                    Modifier
                }
            )
        }
    }
}

fun getColorByStat(baseStat: Int): Color {
    return when {
        baseStat < 30 -> Color(0xFFf34444)
        baseStat < 60 -> Color(0xFFff7f0f)
        baseStat < 90 -> Color(0xFFffdd57)
        baseStat < 120 -> Color(0xFFa0e515)
        baseStat < 150 -> Color(0xFF23cd5e)
        else -> Color(0xFF00c2b8)
    }
}

@Preview(showSystemUi = true)
@Composable
fun TestBar() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        StatBar(
            "hp", 255
        )
        StatBar(
            "attack", 160
        )
        StatBar(
            "defense", 130
        )
        StatBar(
            "special-attack", 60
        )
        StatBar(
            "special-defense", 90
        )
        StatBar(
            "speed", 90
        )
        StatBar(
            "total", 696
        )
    }
}