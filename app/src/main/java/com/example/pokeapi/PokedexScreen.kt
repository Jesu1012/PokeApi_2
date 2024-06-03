package com.example.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

data class Pokemon(val name: String)
data class Region(val name: String, val pokemons: List<Pokemon>)

@Composable
fun PokedexScreen(modifier: Modifier) {
    val regions = listOf(
        Region("Kanto", listOf(Pokemon("Bulbasaur"), Pokemon("Charmander"), Pokemon("Squirtle"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        Region("Johto", listOf(Pokemon("Chikorita"), Pokemon("Cyndaquil"), Pokemon("Totodile"))),
        // Add more regions and pokemons as needed
    )

    var selectedRegion by remember { mutableStateOf(regions.first()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Pokédex",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(selectedRegion.pokemons) { pokemon ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            }
        }

        Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)

        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(regions) { region ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedRegion = region
                        }
                ) {
                    Text(
                        text = region.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (region == selectedRegion) Color.Red else Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PokedexScreenPreview() {

    PokedexScreen(Modifier)

}
