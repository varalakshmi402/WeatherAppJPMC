package com.varalakshmiakella.weatherappjpmc.ui.theme

import com.varalakshmiakella.weatherappjpmc.model.WeatherForecastData

data class WeatherForeCastState(
    val weatherForeCastReport: WeatherForecastData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
