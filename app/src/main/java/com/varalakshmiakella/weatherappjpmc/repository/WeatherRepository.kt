package com.varalakshmiakella.weatherappjpmc.repository

import com.varalakshmiakella.weatherappjpmc.model.WeatherForecastData
import com.varalakshmiakella.weatherappjpmc.model.WeatherReport
import com.varalakshmiakella.weatherappjpmc.util.Resource


interface WeatherRepository {
    suspend fun getWeatherData(city:String, State: String, country : String): Resource<WeatherReport>
    suspend fun getWeatherForeCastData(lat:Double, lon: Double): Resource<WeatherForecastData>
}