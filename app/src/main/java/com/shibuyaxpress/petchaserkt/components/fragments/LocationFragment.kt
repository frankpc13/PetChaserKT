package com.shibuyaxpress.petchaserkt.components.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Report
import com.shibuyaxpress.petchaserkt.modules.GlideApp
import com.shibuyaxpress.petchaserkt.network.APIServiceGenerator
import kotlinx.android.synthetic.main.fragment_chaser.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationFragment : Fragment(), OnMapReadyCallback, PermissionsListener, OnCameraTrackingChangedListener {

    private var permissionManager = PermissionsManager(this)
    private lateinit var mapViewChaser: MapView
    private lateinit var mapboxMap: MapboxMap
    private var isInTrackingMode: Boolean = false
    private val MARKER_SOURCE = "markers-source"
    private val MARKER_STYLE_LAYER = "markers-style-layer"
    private val MARKER_IMAGE = "custom-marker"
    private lateinit var list: List<Report>

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

    private fun imageFromView(list: List<Report>) {
        var options = ArrayList<MarkerOptions>()
        for (item in list) {
            var inflatedView = layoutInflater.inflate(R.layout.marker_pet_report, null)
            var imagePet = inflatedView.findViewById<ImageView>(R.id.imageMarkerPet)
            inflatedView.isDrawingCacheEnabled = true
            val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            inflatedView.measure(measureSpec, measureSpec)
            inflatedView.layout(0, 0, inflatedView.measuredWidth, inflatedView.measuredHeight)
            inflatedView.buildDrawingCache(true)
            val requestOptions =
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(100, 100)
            GlideApp.with(this).load(item.pet!!.image)
                .apply(RequestOptions.circleCropTransform())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(context!!,"no carga el recurso", Toast.LENGTH_SHORT).show()
                        Log.e("Location Fragment","${e!!.message}")
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imagePet.setImageDrawable(resource)
                        var bitmap = Bitmap.createBitmap(
                            inflatedView.measuredWidth,
                            inflatedView.measuredHeight,
                            Bitmap.Config.ARGB_8888
                        )
                        var canvas = Canvas(bitmap)

                        inflatedView.draw(canvas)

                        var iconFactory = IconFactory.getInstance(context!!)
                        var icon = iconFactory.fromBitmap(bitmap)
                       //inflatedView.draw(canvas)
                        options.add(MarkerOptions().position(
                            LatLng(
                                item.latitude!!,
                                item.longitude!!
                            )
                        ).icon(icon))
                        return true
                    }

                })
                .into(imagePet)
        }
        mapboxMap.addMarkers(options)
    }

    private fun getReportFromApi() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = APIServiceGenerator.petchaserClient.getReportsAsync().await()
                if (webResponse.isSuccessful) {
                    val reportList: List<Report>? = webResponse.body()!!.data
                    Log.d(tag, reportList!!.toString())
                    list = reportList
                    /*var options = ArrayList<MarkerOptions>()
                    for (report in list) {
                        //ading custom view to marker layout
                        var iconFactory = IconFactory.getInstance(context!!)
                        var icon = iconFactory.fromBitmap(imageFromView(report.pet!!.image!!))
                        options.add(
                            MarkerOptions().position(LatLng(report.latitude!!,
                            report.longitude!!))
                            .icon(icon))
                    }*/
                    //mapboxMap.addMarkers(options)
                    imageFromView(list)
                } else {
                    Log.e(tag, "Error ${webResponse.code()}")
                    Toast.makeText(activity!!.applicationContext, "Error ${webResponse.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(tag, "Exception"+e.printStackTrace())
                Toast.makeText(activity!!.applicationContext, "Error ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMarkers(report: Report?, loadedMapStyle:Style) {
        val featureList:ArrayList<Feature> = ArrayList()
        featureList
            .add(Feature.fromGeometry(Point.fromLngLat(report?.longitude!!,report.latitude!!)))
        loadedMapStyle
            .addSource(GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(featureList)))

        loadedMapStyle.addLayer(
            SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                    PropertyFactory.circleRadius(25f),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.iconIgnorePlacement(true),
                    PropertyFactory.iconImage(MARKER_IMAGE),
                    PropertyFactory.iconOffset(arrayOf(0f, -52f))
                ))
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
                addOnCameraTrackingChangedListener(this@LocationFragment)
            }
            back_to_camera_tracking_mode.setOnClickListener {
                if (!isInTrackingMode) {
                    isInTrackingMode = true
                    mapboxMap.locationComponent.cameraMode = CameraMode.TRACKING
                    mapboxMap.locationComponent.zoomWhileTracking(16.0)
                    Toast.makeText(activity,"Se activo el tracking", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "El tracking ya esta activado", Toast.LENGTH_SHORT).show()
                }
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
        getReportFromApi()
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

    override fun onCameraTrackingChanged(currentMode: Int) {

    }

    override fun onCameraTrackingDismissed() {
        isInTrackingMode = false
    }
}