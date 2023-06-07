package com.varalakshmiakella.weatherappjpmc.remote

import com.varalakshmiakella.weatherappjpmc.model.WeatherForecastData
import com.varalakshmiakella.weatherappjpmc.model.WeatherReport
import com.varalakshmiakella.weatherappjpmc.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {
    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query(value = "q", encoded = true) q :String ,
        @Query("appid") apiKey : String= API_KEY,
        @Query("units") units :String = "metric"
    ) : Response<WeatherReport>


    @GET("data/2.5/forecast")
    suspend fun getWeatherForeCastData(
        @Query("lat") lat :Double ,
        @Query("lon") lon :Double ,
        @Query("appid") apiKey : String= API_KEY,
        @Query("units") units :String = "metric"
    ) : Response<WeatherForecastData>

}