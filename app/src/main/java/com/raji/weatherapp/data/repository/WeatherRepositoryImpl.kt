package com.raji.weatherapp.data.repository

import com.raji.weatherapp.data.remote.model.toWeatherInfo
import com.raji.weatherapp.data.remote.request.WeatherApiService
import com.raji.weatherapp.domain.Resource
import com.raji.weatherapp.domain.model.WeatherInfo
import com.raji.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(val weatherApiService: WeatherApiService) :
    WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Resource<WeatherInfo> {
        return try {
            Resource.Success(weatherApiService.getWeatherData(latitude, longitude).toWeatherInfo())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong")
        }
    }
}