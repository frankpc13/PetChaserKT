package com.shibuyaxpress.petchaserkt.components.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.shibuyaxpress.petchaserkt.R

class LocationFragment : Fragment(), OnMapReadyCallback, PermissionsListener {

    private var permissionManager = PermissionsManager(this)
    private lateinit var mapViewChaser: MapView
    private lateinit var mapboxMap: MapboxMap

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapViewChaser.onSaveInstanceState(outState)
        //mapViewChaser.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.cameraPosition = CameraPosition
            .Builder()
            .zoom(15.00)
            .build()
        mapboxMap.setStyle(Style.MAPBOX_STREETS){
            enableLocationComponent(it)
        }
    }
    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(context!!.applicationContext)) {
            val customLocationComponentOptions = LocationComponentOptions.builder(context!!.applicationContext)
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(context!!.applicationContext, R.color.mapbox_blue))
                .build()

            val locationComponentActivationOptions = LocationComponentActivationOptions.builder(context!!.applicationContext,loadedMapStyle)
                .locationComponentOptions(customLocationComponentOptions)
                .build()

            mapboxMap.locationComponent.apply {

                activateLocationComponent(locationComponentActivationOptions)

                isLocationComponentEnabled = true
                cameraMode = CameraMode.TRACKING
                renderMode = RenderMode.COMPASS
            }
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(activity)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(context, "F?", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent(mapboxMap.style!!)
        } else {
            Toast.makeText(context, "no se concedio los permisos", Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*return super.onCreateView(inflater, container, savedInstanceState)*/
        Mapbox.getInstance(context!!.applicationContext,getString(R.string.map_box_key))
        val itemView = inflater.inflate(R.layout.fragment_chaser, container, false)
        mapViewChaser = itemView.findViewById(R.id.mapViewChaser)
        mapViewChaser.onCreate(savedInstanceState)
        mapViewChaser.getMapAsync(this)
        return itemView
    }

    override fun onStart() {
        super.onStart()
        mapViewChaser.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapViewChaser.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapViewChaser.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapViewChaser.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewChaser.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewChaser.onDestroy()
    }

    companion object {
        fun newInstance():LocationFragment = LocationFragment()
    }
}