package com.example.yonseiapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.yonseiapp.R;
import com.example.yonseiapp.db.SessionTable;
import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {

    public void onClickSignup(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    public void onClickSignIn(View v) {
        EditText dtID = findViewById(R.id.suetID);
        EditText dtPW = findViewById(R.id.suetPwd);

        String id = dtID.getText().toString();
        String pwd = dtPW.getText().toString();

        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("command", "signin");
            json.put("pwd", pwd);

            Utils.post(json, new Utils.PostCallBack() {
                @Override
                public void onResponse(JSONObject ret, String errMsg) {
                    try{
                        if (errMsg !=null) {
                            Utils.toast(SigninActivity.this, errMsg);
                            return;
                        }
                        if (ret.getBoolean("ret") == false) {
                            Utils.toast(SigninActivity.this, ret.getString("message"));
                            return;
                        }

                        String utk = ret.getString("sessionID");
                        SessionTable.inst().putSession(SigninActivity.this, utk);
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch(Exception e) {

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //String id = savedInstanceState.getString("id");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //EditText dtID = findViewById(R.id.suetID);
        //EditText dtPW = findViewById(R.id.suetPwd);
        //String id = dtID.getText().toString();
        //String pwd = dtPW.getText().toString();
        //outState.putString("id", id);
    }
}
