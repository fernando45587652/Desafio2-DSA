package com.example.paises.model

import retrofit2.http.GET
import retrofit2.http.Path

interface PaisApiService {
    @GET("all")
    suspend fun getAllCountries(): List<Country>

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): List<Country>
}