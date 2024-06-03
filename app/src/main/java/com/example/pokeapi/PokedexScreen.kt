package com.example.pokeapi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

import kotlinx.coroutines.launch

@Composable
fun PokedexScreen(modifier: Modifier) {
    var locationAreas by remember { mutableStateOf<List<LocationArea>>(emptyList()) }
    var selectedLocationArea by remember { mutableStateOf<LocationArea?>(null) }
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val fetchedLocationAreas = fetchLocationAreas()
            locationAreas = fetchedLocationAreas
            if (fetchedLocationAreas.isNotEmpty()) {
                selectedLocationArea = fetchedLocationAreas.first()
                val fetchedPokemons = fetchPokemons(fetchedLocationAreas.first().url)
                pokemons = fetchedPokemons
            }
            isLoading = false
        }
    }

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

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(pokemons) { pokemon ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.surface),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (pokemon.imageUrl != null) {
                                AsyncImage(
                                    model = pokemon.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = pokemon.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
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
                items(locationAreas) { locationArea ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedLocationArea = locationArea
                                coroutineScope.launch {
                                    isLoading = true
                                    println("Selected location area: ${locationArea.name}")
                                    val fetchedPokemons = fetchPokemons(locationArea.url)
                                    pokemons = fetchedPokemons
                                    isLoading = false
                                }
                            }
                    ) {
                        Text(
                            text = locationArea.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (locationArea == selectedLocationArea) Color.Red else Color.White,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

suspend fun fetchLocationAreas(): List<LocationArea> {
    println("Fetching location areas...")
    return try {
        val apiService = RetrofitClient.instance.create(PokeApiService::class.java)
        val response = apiService.getLocationAreas()
        println("Location areas fetched: ${response.results}")
        response.results
    } catch (e: Exception) {
        println("Error fetching location areas: ${e.message}")
        emptyList()
    }
}

suspend fun fetchPokemons(locationAreaUrl: String): List<Pokemon> {
    println("Fetching pokemons for location area: $locationAreaUrl")
    return try {
        val apiService = RetrofitClient.instance.create(PokeApiService::class.java)
        val response = apiService.getLocationArea(locationAreaUrl)
        val pokemons = response.pokemon_encounters.map { encounter ->
            val pokemonResponse = apiService.getPokemon(encounter.pokemon.url)
            Pokemon(encounter.pokemon.name, pokemonResponse.sprites.front_default)
        }
        println("Pokemons fetched: $pokemons")
        pokemons
    } catch (e: Exception) {
        println("Error fetching pokemons: ${e.message}")
        emptyList()
    }
}

@Preview
@Composable
fun PokedexScreenPreview() {
    PokedexScreen(Modifier)
}
