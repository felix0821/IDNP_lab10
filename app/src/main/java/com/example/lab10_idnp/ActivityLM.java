package com.example.lab10_idnp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ActivityLM extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager ubicacion;
    //private ActivityMapsBinding binding;
    private TextView tvAddress;

    final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 13;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lm);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // se coloca un permission check para leer el permiso de ubicacion
        //lee si tenemos o no permiso y le va asignar una variable
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            //si yo quiero mostrar un mensaje antes que le explique al usuario para que le pide la ubicacion
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Call ActivityCompat#requestPermissions
            // here to request the missing permissions, and then override
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        } else {
            //do the actions that require location
            mMap.setMyLocationEnabled(true);
        }
    }

    public void location(View view){
        LocationManager locationManager = (LocationManager) ActivityLM.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        int permissionCheck = ContextCompat.checkSelfPermission(ActivityLM.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        searchMap(mMap, lat, lon, geocoder);
    }

    private void searchMap(GoogleMap mMap, double lat, double lon, Geocoder geocoder) {
        LatLng actually = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(actually).title("Casa ("+lat+", "+lon+")"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actually,18));

        if (lat != 0.0 && lon != 0.0) {
            try {
                List<Address> list = geocoder.getFromLocation(
                        lat, lon, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    showAddress(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void showAddress(String address) {
        tvAddress.setText(address);
    }


}