package com.raji.weatherapp.data.remote.model

import com.raji.weatherapp.domain.model.WeatherData
import com.raji.weatherapp.domain.model.WeatherType
import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>
)


private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

/**
 * this method to convert [WeatherDataDto] to  [com.raji.weatherapp.domain.model.WeatherInfo.waetherDataPerDay]
 */
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    return time.mapIndexed { index, time ->

        IndexedWeatherData(
            index, WeatherData(
                temperatureCelsius = temperatures[index],
                pressure = pressures[index],
                windSpeed = windSpeeds[index],
                humidity = humidities[index],
                weatherType = WeatherType.fromWMO(weatherCodes[index]),
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)

            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { indexedWeatherData ->
        indexedWeatherData.value.map { it.data }
    }

}