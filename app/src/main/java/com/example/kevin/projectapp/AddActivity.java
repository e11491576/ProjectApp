package com.example.kevin.projectapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity{
    static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    LocationListener listener;
    String provider;

    DatabaseHelper myDb;
    EditText editAmount,editTextID;
    Spinner itemSpinner;
    Button btnSubmit,btnUpdate;
    TextView currentTime,editLocation;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
    String str = formatter.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDb = new DatabaseHelper(this);

      //  editTerm = (EditText) findViewById(R.id.editText2);
        editAmount = (EditText) findViewById(R.id.editText3);
        editLocation = (TextView) findViewById(R.id.textLocation);
        editTextID=(EditText)findViewById(R.id.edittext);
        btnSubmit = (Button) findViewById(R.id.button2);
        btnUpdate=(Button)findViewById(R.id.btnupdate);
        currentTime=(TextView)findViewById(R.id.textView);

        itemSpinner=(Spinner)findViewById(R.id.spinner_item);
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.item_array, android.R.layout.simple_spinner_item);
        itemSpinner.setAdapter(nAdapter);

        currentTime.setText(str);//set_curret_time
        AddData();
        UpdateData();


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);
       // locationManager.setTestProviderEnabled("gps", true);
        provider = locationManager.getBestProvider(criteria, true);
        Log.d("provider", provider);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                // TODO Auto-generated method stub
                Log.i("onLocationChanged", "come in");
                if (location != null)
                {
                    Log.w("Location", "Current altitude = "
                            + location.getAltitude());
                    Log.w("Location", "Current latitude = "
                            + location.getLatitude());
                }
                locationManager.removeUpdates(this);
                locationManager.setTestProviderEnabled(provider, false);
            }
            @Override
            public void onProviderDisabled(String provider)
            {
                // TODO Auto-generated method stub
                Log.i("onProviderDisabled", "come in");
            }
            @Override
            public void onProviderEnabled(String provider)
            {
                // TODO Auto-generated method stub
                Log.i("onProviderEnabled", "come in");
            }
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras)
            {
                // TODO Auto-generated method stub
                Log.i("onStatusChanged", "come in");
            }
        };

        getLocation();
    }

    public void AddData() {
        btnSubmit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                boolean isInserted =  myDb.insertData(currentTime.getText().toString(),
                            itemSpinner.getSelectedItem().toString(),
                            editAmount.getText().toString(),
                            editLocation.getText().toString());
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
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextID.getText().toString(),
                                currentTime.getText().toString(),
                                itemSpinner.getSelectedItem().toString(),
                                editAmount.getText().toString(),
                                editLocation.getText().toString()
);
                        if(isUpdate == true) {
                            Toast.makeText(AddActivity.this, "資料修改成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(AddActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AddActivity.this, "資料修改失敗 請重新輸入", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    public void getLocation() {


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location==null){
                locationManager.requestLocationUpdates("gps",60000,1,listener);
            }
            if (location != null){

                double latti = location.getLatitude();
                double longi = location.getLongitude();
                DecimalFormat df = new DecimalFormat("##.0000");
                latti = Double.parseDouble(df.format(latti));
                longi = Double.parseDouble(df.format(longi));
                String consumeLocation = String.valueOf(latti) + "," + String.valueOf(longi);
                editLocation.setText(consumeLocation);
            } else {
                editLocation.setText("Unable to find correct location.");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
    protected void onDestroy()
    {
        locationManager.removeUpdates(listener);
//        locationManager.setTestProviderEnabled(provider, false);
        super.onDestroy();
    }



}
