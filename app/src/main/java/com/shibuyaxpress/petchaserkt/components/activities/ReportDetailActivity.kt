package com.shibuyaxpress.petchaserkt.components.activities

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.shibuyaxpress.petchaserkt.models.Report
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.utils.ColorUtils
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.modules.GlideApp


class ReportDetailActivity : AppCompatActivity(){

    private lateinit var mapView: MapView
    private val MARKER_SOURCE = "markers-source"
    private val MARKER_STYLE_LAYER = "markers-style-layer"
    private val MARKER_IMAGE = "custom-marker"
    private lateinit var report: Report
    private lateinit var imagePet: ImageView
    private lateinit var petName: TextView
    private lateinit var descriptionReport: TextView

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.map_box_key))
        setContentView(R.layout.activity_report_detail)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        report = intent.getParcelableExtra("reportSelected")!!
        Log.d("reported get","$report")
        setMapInit()
        initUIComponents(report)
    }

    private fun initUIComponents(report:Report?) {
        petName = findViewById(R.id.petNameReportDetail)
        imagePet = findViewById(R.id.imagePetReportDetail)
        descriptionReport = findViewById(R.id.descriptionReportDetail)
        petName.text = report!!.pet!!.name
        GlideApp.with(this).load(report.pet!!.image)
            .apply(RequestOptions.circleCropTransform()).into(imagePet)
        descriptionReport.text = report.description
        imagePet.transitionName = report.pet!!.image
        petName.transitionName = report.pet!!.name
    }

    private fun addMarkers(report: Report?, loadedMapStyle:Style) {
        val featureList:ArrayList<Feature> = ArrayList()
        featureList
            .add(Feature.fromGeometry(Point.fromLngLat(report?.longitude!!,report.latitude!!)))
        loadedMapStyle
            .addSource(GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(featureList)))

        loadedMapStyle.addLayer(SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
            .withProperties(
                PropertyFactory.circleRadius(25f),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true),
                PropertyFactory.iconImage(MARKER_IMAGE),
                PropertyFactory.iconOffset(arrayOf(0f, -52f))
            ))
    }

    private fun setMapInit() {
        mapView.getMapAsync {
            it.cameraPosition = CameraPosition
                .Builder()
                .target(LatLng(report.latitude!!,report.longitude!!))
                .zoom(15.00)
                .build()
            it.setStyle(Style.MAPBOX_STREETS) { style ->
                val circleManager = CircleManager(mapView, it, style)
                // create a fixed circle
                val circleOptions = CircleOptions()
                    .withLatLng( LatLng(report.latitude!!, report.longitude!!))
                    .withCircleColor(ColorUtils.colorToRgbaString(Color.CYAN))
                    .withCircleRadius(250f)
                    .withCircleOpacity(0.25f)
                    .withDraggable(false)
                    .withCircleStrokeColor(ColorUtils.colorToRgbaString(Color.WHITE))
                circleManager.create(circleOptions)
                //here we add the marker to the coordinates of the report
                it.addMarker(MarkerOptions().position(LatLng(report.latitude!!,report.longitude!!)).title("ME PERDI"))
                style
                    .addImage(MARKER_IMAGE, BitmapFactory
                        .decodeResource(this@ReportDetailActivity.resources,
                            R.drawable.custom_marker
                        ))
                addMarkers(report, style)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
