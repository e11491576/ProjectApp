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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ClusterManager<MyItem> mClusterManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); // 自動移到標記點
        //mMap.setMyLocationEnabled(true); // 右上角的定位功能；這行會出現紅色底線，不過仍可正常編譯執行
        mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
        mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能
        setUpClusterMarker();
        generateNewPointOnMap();

    }

    public void generateNewPointOnMap() {
        DatabaseHelper myDb = new DatabaseHelper(this);
        Cursor cursor = myDb.getAllData();
        int rows_num = cursor.getCount();
        if (rows_num != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < rows_num; i++) {
                //_id=0 time=1 term=2 amount=3 comsumeLocation=4
                String term = cursor.getString(2);
                String item = cursor.getString(4);
                String money = cursor.getString(3);
                String[] loc = item.split(",");
                //loc[0]=Latitude loc[1]=Longtitude
                try {
                    LatLng sydney1 = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                    //String icon;
                    MarkerOptions option = new MarkerOptions().position(sydney1).title("我在這花了" + money)
                            .snippet("項目:" + term);
                    switch (term) {
                        case "食":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.food_x50));
                            break;
                        case "衣":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.shirt_x50));
                            break;
                        case "住":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.house_x50));
                            break;
                        case "行":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_x50));
                            break;
                        case "育":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.edu_x50));
                            break;
                        case "樂":
                            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.play_x50));
                            break;
                        case "其它":
                            break;
                    }
                    Marker mark = mMap.addMarker(option);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney1, 12);
                    mark.showInfoWindow();
                    mMap.animateCamera(cameraUpdate);
                } catch (Exception e) {

                }
                cursor.moveToNext();
            }
        }
    }
    public void setUpClusterMarker(){
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        //addClusterItems();
        mClusterManager.cluster();
    }

    public void addClusterItems() {
        // Set some lat/lng coordinates to start with.
        double lat = 121.80;
        double lng = 25.10;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 5; i++) {
            double offset = i / 30d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }

    }
}