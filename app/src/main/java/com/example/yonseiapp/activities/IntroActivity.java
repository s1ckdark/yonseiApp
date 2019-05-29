package com.example.yonseiapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.yonseiapp.R;
import com.example.yonseiapp.db.SessionTable;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (hasSession()) {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean hasSession() {
        String utk = SessionTable.inst().getSession();
        return (utk !=null);
    }
}
