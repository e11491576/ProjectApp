package com.example.kevin.projectapp;

import android.content.Context;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        takeDataBase();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(25.1504, 121.773);
        //mMap.setMyLocationEnabled(true);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        DatabaseHelper myDb = new DatabaseHelper(this);
        Cursor cursor =myDb.getAllData();
        int rows_num = cursor.getCount();
        if(rows_num!=0) {
            cursor.moveToFirst();
            for (int i = 0; i < rows_num; i++) {
                //_id=0 time=1 term=2 amount=3 comsumeLocation=4
                String item = cursor.getString(4);
                String money=cursor.getString(3);
                String[] loc=item.split(",");
                //loc[0]=Latitude loc[1]=Longtitude
                //Toast.makeText(MapsActivity.this, ""+loc[0]+loc[1], Toast.LENGTH_LONG).show();
                LatLng sydney1 = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                mMap.addMarker(new MarkerOptions().position(sydney1).title("我在這花了"+money));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));
                cursor.moveToNext();

            }

        }
    }
    public void takeDataBase() {
    }
}
