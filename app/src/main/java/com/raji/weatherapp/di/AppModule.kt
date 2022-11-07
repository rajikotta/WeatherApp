package com.raji.weatherapp.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raji.weatherapp.data.remote.request.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        return Retrofit.Builder().baseUrl("").addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<WeatherApiService>()
    }

    @Provides
    fun provideFusedLocationProviderClient(applcation: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(applcation)
    }
}