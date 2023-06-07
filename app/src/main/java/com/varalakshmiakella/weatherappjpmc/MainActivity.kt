package com.varalakshmiakella.weatherappjpmc

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.varalakshmiakella.weatherappjpmc.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
            viewModel.loadWeatherForeCastInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            WeatherAppJPMCTheme {
                val cityText by viewModel.cityText.collectAsState()
                val stateText by viewModel.stateText.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        TextField(value = cityText, onValueChange = viewModel::onCityTextChange,
                            placeholder = {
                                Text(
                                    text = "Enter City Name",
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            })
                        TextField(value = stateText, onValueChange = viewModel::onStateTextChange,

                            placeholder = {
                                Text(
                                    text = "State Code",
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            })

                    }

                    viewModel.loadWeatherForeCastInfo()
                    viewModel.loadWeatherInfo()

                    Column(
                        modifier = Modifier
                            .padding(60.dp)
                    ) {
                        viewModel.weatherState.weatherReport?.let {

                            Text(
                                text = it.name,
                                fontSize = 35.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = it.main.temp.toString(),
                                fontSize = 60.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = it.weather[0].description,
                                fontSize = 30.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                            viewModel.loadIcon(icon = it.weather[0].icon)
                            Column {
                                Row {
                                    Text(
                                        text = "H:" + it.main.temp_max.toString(),
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        text = "L:" + it.main.temp_min.toString(),
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(start = 15.dp)

                                    )
                                }
                                Row {
                                    Text(
                                        text = "Lat:" + it.coord.lat.toString(),
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        text = "Long:" + it.coord.lon.toString(),
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(start = 15.dp)
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = "Next 30 Hr Forecast",
                        fontSize = 40.sp,
                        color = Color.Magenta,
                        fontStyle = FontStyle.Italic
                    )
                    LazyColumn() {
                        val itemList = viewModel.weatherForecastState.weatherForeCastReport?.list
                        if (itemList != null) {
                            items(itemList.subList(0, 10)) { item ->
                                Text(
                                    text = item.dt_txt,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(start = 15.dp)
                                )
                                Row {
                                    Text(
                                        text = item.main.temp.toString(),
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(start = 15.dp)
                                    )
                                    Text(
                                        text = item.weather[0].description,
                                        fontSize = 30.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(start = 15.dp)
                                    )
                                }
                            }
                        }

                    }
                }
                viewModel.weatherState.error?.let { error ->
                    Text(
                        text = "Please Enter valid weatherState and City",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

