package com.example.yonseiapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yonseiapp.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    public void onClickSignup(View v) {
        EditText dtID = findViewById(R.id.suetID);
        EditText dtPW = findViewById(R.id.suetPwd);
        String id = dtID.getText().toString();
        String pwd = dtPW.getText().toString();

        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("command", "signup");
            json.put("pwd", pwd);

            Utils.post(json, new Utils.PostCallBack() {
                @Override
                public void onResponse(JSONObject ret, String errMsg) {
                    try {
                        if (errMsg !=null) {
                            Utils.toast(SignupActivity.this, errMsg);
                            return ;
                        }
                        if (ret.getBoolean("ret") == false) {
                            Utils.toast(SignupActivity.this, ret.getString("message"));
                            return;
                        }
                        Utils.toast(SignupActivity.this, "가입성공! 로그인해주세요");
                        finish();
                    } catch (Exception e) {}
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}
