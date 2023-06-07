package com.varalakshmiakella.weatherappjpmc.model

data class WeatherForecastData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherForeCastReport>,
    val message: Int
)