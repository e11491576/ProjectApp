package com.example.kevin.projectapp;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isOpenGps()){
        }else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }


        button1 =(ImageButton)findViewById(R.id.imageButton1);
        ImageButton nextPageBtn1=(ImageButton)findViewById(R.id.imageButton1);
        nextPageBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        button2 =(ImageButton)findViewById(R.id.imageButton2);
        ImageButton nextPageBtn2=(ImageButton)findViewById(R.id.imageButton2);
        nextPageBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        button3 =(ImageButton)findViewById(R.id.imageButton3);
        ImageButton nextPageBtn3=(ImageButton)findViewById(R.id.imageButton3);
        nextPageBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,GraphActivity.class);
                startActivity(intent);
            }
        });
        button4 =(ImageButton)findViewById(R.id.imageButton4);
        ImageButton nextPageBtn4=(ImageButton)findViewById(R.id.imageButton4);
        nextPageBtn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isOpenGps() {
        LocationManager locationManager
                = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
}
