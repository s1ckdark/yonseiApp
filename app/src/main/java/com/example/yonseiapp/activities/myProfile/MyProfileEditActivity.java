package com.example.yonseiapp.activities.myProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yonseiapp.R;

public class MyProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_edit);

        TextView topTitle = findViewById(R.id.topTitle);
        TextView topRight = findViewById(R.id.topRight);

        topTitle.setText("내 정보 수정");
        topRight.setText("저장");

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

    public void onClickMyProfile(View v) {
        Intent result = new Intent();
        EditText etName = findViewById(R.id.name);
        EditText etAge = findViewById(R.id.age);
        result.putExtra("name", etName.getText().toString());
        result.putExtra("age", etAge.getText().toString());
        setResult(100, result);
        finish();


    }
}
