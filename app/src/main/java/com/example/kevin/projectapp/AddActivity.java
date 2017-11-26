package com.example.kevin.projectapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTime, editTerm, editAmount, editLocation;
    Button btnSubmit;
    TextView currentTime,currentLoc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
    String str = formatter.format(curDate);
    String msg_loc;

    //location
    private double currentLatitude = 0;
    private double currentLongitude = 0;
    private LocationManager mLocationManager;
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1000;
    private static final int LOCATION_UPDATE_MIN_TIME = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDb = new DatabaseHelper(this);

        editTerm = (EditText) findViewById(R.id.editText2);
        editAmount = (EditText) findViewById(R.id.editText3);
        btnSubmit = (Button) findViewById(R.id.button2);
        currentTime=(TextView)findViewById(R.id.textView);
        currentTime.setText(str);//set_curret_time
        currentLoc=(TextView)findViewById(R.id.textView4);
        //getCurrentLocation();
        AddData();
    }

    public void AddData() {
        btnSubmit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                boolean isInserted =  myDb.insertData(currentTime.getText().toString(),
                            editTerm.getText().toString(),
                            editAmount.getText().toString(),
                            currentLoc.getText().toString());
                        if(isInserted=true) {
                            Toast.makeText(AddActivity.this, "帳目輸入成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(AddActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(AddActivity.this,"帳目輸入失敗",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)) {
            Toast.makeText(AddActivity.this,"沒抓到GPSNetwork",Toast.LENGTH_LONG).show();
            // location_provider error
        } else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_TIME,
                        LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else if(isGPSEnabled){
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME,
                        LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            msg_loc = String.format("%f, %f", location.getLatitude(), location.getLongitude());
            currentLoc.setText(msg_loc);
        }
    }

    private LocationListener mLocationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                msg_loc = String.format("%f, %f", location.getLatitude(), location.getLongitude());
                currentLoc.setText(msg_loc);
            }
            else{
                // Logger.d("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s){

        }

        @Override
        public void onProviderDisabled(String s){

        }
    };
    */
}

