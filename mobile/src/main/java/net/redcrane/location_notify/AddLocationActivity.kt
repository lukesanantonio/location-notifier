package net.redcrane.location_notify

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.content_add_location.*

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class AddLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var marker: Marker? = null
    private var circle: Circle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        setSupportActionBar(toolbar)

        (map as SupportMapFragment).getMapAsync(this);
    }

    override fun onMapReady(map: GoogleMap) {
        map.setOnMapClickListener {
            if(marker == null || circle == null) {
                marker = map.addMarker(MarkerOptions()
                        .position(it));

                circle = map.addCircle(CircleOptions()
                        .center(it)
                        .radius(1000.0)
                        .strokeWidth(5.0f)
                        .fillColor(Color.argb(0x55, 0x6A, 0xD9, 0xB3)));
            } else {
                marker!!.position = it;
                circle!!.center = it;
            }
        }
    }
}
