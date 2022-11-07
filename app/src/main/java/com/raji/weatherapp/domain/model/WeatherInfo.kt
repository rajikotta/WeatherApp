package com.raji.weatherapp.domain.model

data class WeatherInfo(
    val waetherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData
)
