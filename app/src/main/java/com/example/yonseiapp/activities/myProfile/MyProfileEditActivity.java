package com.example.yonseiapp.activities.myProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yonseiapp.R;
import com.example.yonseiapp.utils.Utils;
import com.example.yonseiapp.api.RetCallBack;
import com.example.yonseiapp.api.UserInfo;

public class MyProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_edit);

        TextView topTitle = findViewById(R.id.topTitle);
        TextView topRight = findViewById(R.id.topRight);

        topTitle.setText("my profile edit");
        topRight.setText("save");

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");

        EditText etName = findViewById(R.id.name);
        EditText etAge = findViewById(R.id.age);
        etName.setText(name);
        etAge.setText(age);


    }

    public void onClickBack(View v) {
        onBackPressed();
    }

    public void onClickMyProfileEdit(View v){
        Intent result = new Intent();
        EditText etName = findViewById(R.id.name);
        EditText etAge = findViewById(R.id.age);
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        result.putExtra("name",name);
        result.putExtra("age",age);
        setResult(100, result);

        UserInfo.put(this, name, age, new RetCallBack() {
            @Override
            public void onResponse(Boolean ret, String errMsg) {
                if(ret==true)
                    finish();
                else
                    Utils.toast(MyProfileEditActivity.this errMsg);
            }
        });
        finish();
    }
}
