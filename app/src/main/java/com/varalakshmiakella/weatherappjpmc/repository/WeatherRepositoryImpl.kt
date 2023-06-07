package com.varalakshmiakella.weatherappjpmc.repository


import com.varalakshmiakella.weatherappjpmc.model.WeatherForecastData
import com.varalakshmiakella.weatherappjpmc.model.WeatherReport
import com.varalakshmiakella.weatherappjpmc.remote.WeatherAPI
import com.varalakshmiakella.weatherappjpmc.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
): WeatherRepository {

    override suspend fun getWeatherData(city: String, state: String, country:String): Resource<WeatherReport> {
        return try {
            Resource.Success(

                data = api.getWeatherData(
                    q=locationString(listOf(city,state,country))
                ).body()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getWeatherForeCastData(
        lat: Double,
        lon: Double
    ): Resource<WeatherForecastData> {
        return try {
            Resource.Success(
                data = api.getWeatherForeCastData(lat,lon).body()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    fun locationString(location: List<String>) = location.joinToString(separator = ",")

}