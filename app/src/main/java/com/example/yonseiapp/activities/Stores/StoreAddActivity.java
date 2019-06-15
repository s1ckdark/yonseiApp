package com.example.yonseiapp.activities.Stores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yonseiapp.R;

import com.example.yonseiapp.api.RetCallBack;
import com.example.yonseiapp.api.UserInfo;
import com.example.yonseiapp.db.UserInfoTable;
import com.example.yonseiapp.utils.PostCallBack;
import com.example.yonseiapp.utils.Utils;
//import com.example.app.utils.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StoreAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_add);

        updateToView();

        UserInfo.get(this, new RetCallBack() {
            @Override
            public void onResponse(Boolean ret, String errMsg) {
                if(ret)
                    updateToView();
            }
        });

        //intent 전달된 값을 꺼내옴
        String test = getIntent().getStringExtra("test");
        int testInt = getIntent().getIntExtra("testInt",0);

        Utils.toast(this, test+"/"+testInt);
    }

    Callback cb;

    private void updateToView(){
        try{
            JSONObject val = UserInfoTable.inst().get();
            System.out.println("updateToView Val : " + val.toString());

            if(val != null){
                String storename = val.getString("storename");
                String coupon = val.getString("coupon");
                String addr = val.getString("addr");
                String lat = val.getString("lat");
                String lng = val.getString("lng");


                TextView stName = findViewById(R.id.storename);
                TextView stCoupon = findViewById(R.id.coupon);
                TextView Address = findViewById(R.id.addr);
                TextView stLat = findViewById(R.id.lat);
                TextView stLng = findViewById(R.id.lng);
            }

        }catch (Exception e){
            System.out.println("updateToView : " + e);
        }
    }

    public void onClickGPS(View view) {
        JSONObject params = new JSONObject();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?";
        String address = ((EditText)findViewById(R.id.addr)).getText().toString();

    }
}
