package com.hazrat.prayertimes.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.hazrat.prayertimes.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LocationHandler(
    private val context: Context,
    private val locationRepository: LocationRepository
) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    private fun initializeLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getCurrentLocation(onLocationReceived: (Location) -> Unit, onLocationError: () -> Unit) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(OnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        onLocationError()
                        Log.e("LocationHandler", "Error getting location: Last location is null")
                    } else {
                        onLocationReceived(location)
                        Log.d(
                            "LocationHandler",
                            "Location received: ${location.latitude}, ${location.longitude}"
                        )
                    }
                })
            } else {
                onLocationError()
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun checkPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    init {
        initializeLocationClient()
        CoroutineScope(Dispatchers.Main).launch {
            getCurrentLocation(
                onLocationReceived = { location ->
                    CoroutineScope(Dispatchers.IO).launch {
                        locationRepository.saveLocation(location.latitude, location.longitude)
                    }
                },
                onLocationError = {
                    Log.e("MainActivity", "Error getting location")
                }
            )
        }
    }

}
