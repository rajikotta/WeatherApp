package com.raji.weatherapp.data.remote.request

import com.raji.weatherapp.data.remote.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast?hourly=temperature_2m,pressure_msl,relativehumidity_2m,windspeed_10m,weathercode")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): WeatherDto
}