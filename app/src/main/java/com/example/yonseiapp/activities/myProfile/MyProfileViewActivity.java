package com.example.yonseiapp.activities.myProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yonseiapp.R;
import com.example.yonseiapp.activities.Utils;

public class MyProfileViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_view);

        TextView topRight = findViewById(R.id.topRight);
        topRight.setText("플필수정");


    }

    public void onClickBack(View v) {
        onBackPressed();
    }

    public void onClickMyProfile(View v) {
        TextView tvName = findViewById(R.id.name);
        TextView tvAge = findViewById(R.id.age);
        String name = tvName.getText().toString();
        String age = tvAge.getText().toString();


        Intent intent = new Intent(this, MyProfileEditActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        startActivityForResult(intent, 1000);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView tvName = findViewById(R.id.name);
        TextView tvAge = findViewById(R.id.age);

        if (requestCode == 1000 && resultCode==100) {
            String name = data.getStringExtra("name");
            String age = data.getStringExtra("age");
            tvName.setText(name);
            tvAge.setText(age);
        }
    }
}
