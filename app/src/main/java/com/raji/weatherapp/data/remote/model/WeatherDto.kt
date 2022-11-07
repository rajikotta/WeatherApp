package com.raji.weatherapp.data.remote.model

import com.raji.weatherapp.domain.model.WeatherInfo
import com.squareup.moshi.Json
import java.time.LocalDateTime

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        waetherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData!!
    )
}