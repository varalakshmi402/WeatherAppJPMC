package com.varalakshmiakella.weatherappjpmc.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.varalakshmiakella.weatherappjpmc.locationSettings.DefaultLocationTracker
import com.varalakshmiakella.weatherappjpmc.repository.WeatherRepository
import com.varalakshmiakella.weatherappjpmc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: DefaultLocationTracker


):ViewModel() {

    private val _cityText = MutableStateFlow("")
    val cityText = _cityText.asStateFlow()
    private val _stateText = MutableStateFlow("")
    val stateText = _stateText.asStateFlow()
    var weatherState by mutableStateOf(WeatherState())
        private set
    var weatherForecastState by mutableStateOf(WeatherForeCastState())
        private set
    fun getLatitude() = weatherState.weatherReport?.coord?.lat
    fun getLongitude() = weatherState.weatherReport?.coord?.lon
    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadWeatherInfo() {
        viewModelScope.launch {
            weatherState = weatherState.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
            }
            when (val result = repository.getWeatherData(
                city = cityText.value,
                State = stateText.value,
                country = "US"
            )) {
                is Resource.Success<*> -> {
                    weatherState = weatherState.copy(
                        weatherReport = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error<*> -> {
                    weatherState = weatherState.copy(
                        weatherReport = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    fun loadWeatherForeCastInfo() {
        viewModelScope.launch {
            weatherForecastState = weatherForecastState.copy(
                isLoading = true,
                error = null
            )
                when (val result =
                    getLatitude()?.let {
                        getLongitude()?.let { it1 ->
                            repository.getWeatherForeCastData(
                                lat = it,
                                lon = it1
                            )
                        }
                    }) {
                    is Resource.Success<*> -> {
                        weatherForecastState = weatherForecastState.copy(
                            weatherForeCastReport = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error<*> -> {
                        weatherForecastState = weatherForecastState.copy(
                            weatherForeCastReport = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
        }
    }
        fun onCityTextChange(city: String) {
            _cityText.value = city
        }
        fun onStateTextChange(state: String) {
            _stateText.value = state
        }
        @Composable
        fun loadIcon(icon: String) {
            val uri = "https://openweathermap.org/img/wn/$icon@2x.png"
            Image(
                painter = rememberAsyncImagePainter(
                    uri
                ), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
            )
        }
}