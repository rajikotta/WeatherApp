package com.raji.weatherapp.data.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.gms.location.FusedLocationProviderClient
import com.raji.weatherapp.domain.location.LocationTracker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class  DefaultLocationTracker @Inject constructor(
    val locationClient: FusedLocationProviderClient,
    val application: Application
) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasAccessFineLocationEnabled =
            ContextCompat.checkSelfPermission(
                application,
                ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED
        val hasAccessCoarseLocationEnabled =
            ContextCompat.checkSelfPermission(
                application,
                ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED

        val gpsEnabled = locationManager.isProviderEnabled(GPS_PROVIDER) ||
                locationManager.isProviderEnabled(NETWORK_PROVIDER)

        if (!hasAccessCoarseLocationEnabled || !hasAccessFineLocationEnabled || !gpsEnabled) {
            return null
        }
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful)
                        continuation.resume(result)
                    else
                        continuation.resume(null)
                    return@suspendCancellableCoroutine
                } else {
                    addOnSuccessListener {
                        continuation.resume(it)

                    }
                    addOnFailureListener {
                        continuation.resume(null)

                    }
                    addOnCanceledListener {
                        continuation.cancel()
                    }
                }

            }
        }

    }
}