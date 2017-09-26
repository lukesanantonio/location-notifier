package net.redcrane.location_notify

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.content_add_location.*

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*

const val METERS_PER_MILE = 1609.344;

const val OUTER_ZOOM_LEVEL = 15.0f;
const val INNER_ZOOM_LEVEL = 5.0f;

class AddLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var marker: Marker? = null
    private var circle: Circle? = null
    private var googleMap: GoogleMap? = null

    fun updateMilesFromSeekBar() {
        updateMilesFromSeekBar(radiusSeekBar.progress.toDouble())
    }
    fun updateMilesFromSeekBar(meters: Double) {
        // Update the circle around the pin if necessary.
        circle?.let { it.radius = meters }

        // Update the text view with the miles
        val miles = meters / METERS_PER_MILE
        miles_view.text = resources.getString(R.string.miles_indicator, miles);
    }

    fun zoomToMarker() {
        // Find out the radius of the circle.
        googleMap?.let {
            // In meters
            val radius = radiusSeekBar.progress.toDouble()

            val lat = radius / 111111.0
            val long = (radius / 111111.0) * Math.cos(Math.toRadians(lat))

            val map = it
            marker?.let {
                val place = it.position;
                val northeast = LatLng(place.latitude - lat, place.longitude - long * 1.5)
                val southwest = LatLng(place.latitude + lat, place.longitude + long * 1.5)
                val cam = CameraUpdateFactory.newLatLngBounds(LatLngBounds(northeast, southwest), 0)
                map.animateCamera(cam)
            }
        }
        // Use the radius to build a LatLngBounds object.
        // Zoom to that bounds object.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        setSupportActionBar(toolbar)

        (map as SupportMapFragment).getMapAsync(this)

        updateMilesFromSeekBar()

        radiusSeekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(self: SeekBar?, newRadius: Int, fromUser: Boolean) {
                        updateMilesFromSeekBar(newRadius.toDouble())
                        zoomToMarker()
                    }

                    override fun onStartTrackingTouch(self: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(self: SeekBar?) {
                    }
                }
        )
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map;
        map.setOnMapClickListener {
            if(marker == null || circle == null) {
                marker = map.addMarker(MarkerOptions()
                        .position(it))

                circle = map.addCircle(CircleOptions()
                        .center(it)
                        .radius(radiusSeekBar.progress.toDouble())
                        .strokeWidth(5.0f)
                        .fillColor(Color.argb(0x55, 0x6A, 0xD9, 0xB3)))
            } else {
                marker!!.position = it
                circle!!.center = it
            }
        }
    }
}
