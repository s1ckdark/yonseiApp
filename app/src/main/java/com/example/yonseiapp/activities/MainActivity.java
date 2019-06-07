package com.example.yonseiapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yonseiapp.R;
import com.example.yonseiapp.activities.myProfile.MyProfileViewActivity;
import com.example.yonseiapp.db.SessionTable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    Stringp[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    GoogleMap gMap = null;
    @Override
    pubic void onRequestPermissionResult(int permsRequestCode, Stringp[] permissions, int[] grandResults) {
        if( permsRequestCode == PERMISSION_REQUEST_CODE) {
            for(int result : grandResults) {
                if(result != PackageManager.PERMISSION_GRANTED){
                    finish();
                    return;
                }
            }
            if(gMap != null){
                if(checkPermission()){
                    gMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();

        MapFragement maoFragement = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(final GoogleMap googleMap){
                MarkerOptions marker = marker(37.56, 126.97,"서울","서울입니다");
                googleMap.addMarker(marker);

                LatLng pos = marker.getPosition();
                CameraUpdate cam = CameraUpdateFactory.newLatLng(pos);
                googleMap.moveCamera(cam);

                if(checkPermission())
                    googleMap.setMyLocationEnabled(true);
            }
        });
    }

    private boolean checkPermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED);
    }

    private MarkerOptions marker(double lat, double lng, String name, String desc){
        //마커​
        LatLng seoul = new LatLng(37.56, 126.97);​
        MarkerOptions markerOptions = new MarkerOptions();​
        markerOptions.position(seoul);​
        markerOptions.title("서울");​
        markerOptions.snippet("서울입니다!");​

//지도에 마커 추가​
//        GoogleMap googleMap;
        googleMap.addMarker(markerOptions);
    }

    public void onClickLogout(View v) {
        SessionTable.inst().delSession(this);

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);

        finish();
    }

    public void onClickBack(View v){
        finish();
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(this, MyProfileViewActivity.class);
        //값 넣고 전달
        intent.putExtra("test", "test");
        intent.putExtra("testInt", 1);

        startActivity(intent);
    }
}
