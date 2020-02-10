package com.arhamtechnolabs.homesalonapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arhamtechnolabs.homesalonapp.DataModel.DataTransferHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    EditText adressED;
    Button submitAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        adressED = findViewById(R.id.adressED);
        submitAddress = findViewById(R.id.submitAddress);

        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataTransferHelper.address = adressED.getText().toString().trim();
                Log.d("Data helper on Intent", DataTransferHelper.address);

                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
    LatLng sydney = new LatLng(-34, 151);
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        System.out.println("LAST COCATIOn");
                        if (location != null) {
                            Log.i("last know location", location.toString());
                            sydney = new LatLng(location.getLatitude(), location.getLongitude());
//                            setAddressAndCity(location);
                        }
                    }
                });*/

        Location loc = DataTransferHelper.location;
        sydney = new LatLng(loc.getLatitude(), loc.getLongitude());


        adressED.setText(DataTransferHelper.address);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
    }
}
