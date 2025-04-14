package com.example.paises.model

data class Country(
    val name: Name,
    val capital: List<String>?,
    val population: Long,
    val region: String,
    val subregion: String?,
    val languages: Map<String, String>?,
    val flags: Flags,
    val latlng: List<Double>
)

data class Name(
    val official: String
)

data class Flags(
    val png: String
)