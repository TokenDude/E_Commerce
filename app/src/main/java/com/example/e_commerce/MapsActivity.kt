package com.example.e_commerce

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var locationManager : LocationManager
    private var url= "https://61d39047b4c10c001712b9ca.mockapi.io/Location"

    var latitude:Double=0.0
    var longitude:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            && checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            requestPermissions(permission, 1)
        }
        else
        {
            val gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        }
        MockApi.Queue(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        // Add a marker in currentLocation and move the camera
        val currentLocation = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(currentLocation))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

        MockApi.getData(mMap)
        mMap.setOnMapClickListener { googleMap->
            val marker=mMap.addMarker(MarkerOptions().position(googleMap).title("new"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(googleMap))
            if (marker != null) {
                MockApi.Queue(this)
                MockApi.postData(marker.position.latitude.toString(),marker.position.longitude.toString())
            }
        }
        mMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Pin ${marker.id}")

            builder.setMessage("Do you want to delete pin with \n latitude: $latitude and longitude: $longitude")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                Toast.makeText(applicationContext,marker.id.substring(1,2),Toast.LENGTH_LONG).show()
                val m=marker.id
                MockApi.delete(marker.id.substring(1,2).toInt())
                marker.remove()
            }

            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()
            true
        }

    }
}