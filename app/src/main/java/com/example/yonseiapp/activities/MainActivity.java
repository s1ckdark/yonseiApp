package com.example.yonseiapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yonseiapp.R;
import com.example.yonseiapp.activities.myProfile.MyProfileViewActivity;
import com.example.yonseiapp.db.SessionTable;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
