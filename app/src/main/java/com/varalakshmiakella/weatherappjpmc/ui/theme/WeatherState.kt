package com.varalakshmiakella.weatherappjpmc.ui.theme

import com.varalakshmiakella.weatherappjpmc.model.WeatherReport

data class WeatherState(
    val weatherReport: WeatherReport? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)