package com.example.paises.controller

import com.example.paises.model.PaisApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PaisApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: PaisApiService = retrofit.create(PaisApiService::class.java)
}