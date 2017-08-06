package net.redcrane.location_notify

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.content_add_location.*

import com.google.android.gms.maps.SupportMapFragment;

class AddLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        setSupportActionBar(toolbar)

        (map as SupportMapFragment).getMapAsync(this);
    }

    override fun onMapReady(p0: GoogleMap) {
    }
}
