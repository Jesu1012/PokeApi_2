package com.example.pokeapi

import retrofit2.http.GET
import retrofit2.http.Url

// Clases de datos
data class Pokemon(val name: String, val imageUrl: String?)
data class LocationArea(val name: String, val url: String)

// Interfaz de API
interface PokeApiService {
    @GET("location-area/")
    suspend fun getLocationAreas(): LocationAreaResponse

    @GET
    suspend fun getLocationArea(@Url url: String): LocationAreaDetailResponse

    @GET
    suspend fun getPokemon(@Url url: String): PokemonDetailResponse
}

// Respuestas de API
data class LocationAreaResponse(
    val results: List<LocationArea>
)

data class LocationAreaDetailResponse(
    val pokemon_encounters: List<PokemonEncounter>
)

data class PokemonEncounter(
    val pokemon: PokemonReference
)

data class PokemonReference(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val sprites: Sprites
)

data class Sprites(
    val back_default: String?,
    val front_default: String?
)
