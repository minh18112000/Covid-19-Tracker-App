package com.example.covid_19trackerapp

data class Country(
    val countryName: String,
    val countryCases: Int,
    val countryRecovered: Int,
    val countryDeaths: Int
)