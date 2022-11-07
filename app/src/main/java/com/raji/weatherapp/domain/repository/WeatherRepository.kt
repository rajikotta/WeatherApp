package com.raji.weatherapp.domain.repository

import com.raji.weatherapp.domain.Resource
import com.raji.weatherapp.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(latitude: Double, longitude: Double): Resource<WeatherInfo>
}