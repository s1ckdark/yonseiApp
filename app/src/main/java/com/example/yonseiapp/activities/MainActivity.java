package com.example.yonseiapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yonseiapp.R;
import com.example.yonseiapp.activities.myProfile.MyProfileViewActivity;
import com.example.yonseiapp.activities.Stores.StoreManagerActivity;
import com.example.yonseiapp.db.SessionTable;
import com.example.yonseiapp.db.StoreTable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {
    GoogleMap gMap = null;

    private static final int PERMISSION_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 내 위치를 받아옵시다.
        }else {
            //퍼미션이 없으면 계속요청
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //지도에 추가 작업을 할 수 있을 때 호출 되도록 세팅
                MarkerOptions marker = marker(37.56, 126.97, "서울", "서울입니다");
                googleMap.addMarker(marker);

                //이동
                LatLng pos = marker.getPosition();
                CameraUpdate cam = CameraUpdateFactory.newLatLng(pos);
                googleMap.moveCamera(cam);

                CameraUpdate cam2 = CameraUpdateFactory.zoomTo(17);
                googleMap.animateCamera(cam2);

                int markernum = StoreTable.inst().size();
                JSONObject store = null;

                for (int i = 0; i<markernum; i++){
                    store = StoreTable.inst().get(i);
                    System.out.println(store);
                    try{
                        marker = marker(store.getDouble("lat"), store.getDouble("lng"), store.getString("desc"), store.getString("name") );
                        googleMap.addMarker(marker);

                    }catch(Exception e) {}

                }
                gMap = googleMap;
                if(checkPermission())
                    googleMap.setMyLocationEnabled(true);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grandResults){
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

    private MarkerOptions marker(double lat, double lng, String name, String desc) {
        LatLng seoul = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(seoul);
        markerOptions.title(name);
        markerOptions.snippet(desc);
        return markerOptions;

    }
    private boolean checkPermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED);
    }

    public void addNewMarker(int i){
        JSONObject store = StoreTable.inst().get(i);
        try {
            MarkerOptions marker = marker(store.getDouble("lat"), store.getDouble("lng"), store.getString("desc"), store.getString("name"));
            gMap.addMarker(marker);
        }catch(Exception e) {}

    }

    public void addAllMarker(){
        int markernum = StoreTable.inst().size();
        JSONObject store = null;

        for (int i = 0; i<markernum; i++){
            store = StoreTable.inst().get(i);
            System.out.println(store);
            try{
                MarkerOptions marker = marker(store.getDouble("lat"), store.getDouble("lng"), store.getString("desc"), store.getString("name") );
                gMap.addMarker(marker);

            }catch(Exception e) {}

        }

        try {
            MarkerOptions marker = marker(store.getDouble("lat"), store.getDouble("lng"), store.getString("desc"), store.getString("name"));
            gMap.addMarker(marker);
        }catch(Exception e) {}

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
        addAllMarker();
    }

    public void onClickStoreManager(View v){
        Intent intent = new Intent(this, StoreManagerActivity.class);
        //값 넣고 전달
        intent.putExtra("test", "test");
        intent.putExtra("testInt", 1);

        startActivity(intent);
    }
}

