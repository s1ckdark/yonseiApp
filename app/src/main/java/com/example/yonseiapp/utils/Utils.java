package com.example.yonseiapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.content.Context.MODE_PRIVATE;

public class Utils extends AppCompatActivity {


    public static void toast(final Context context,
                             final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    static private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static private OkHttpClient client = new OkHttpClient();

    static private Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    static public void post(JSONObject params, final PostCallBack cb) {
        post("http://192.168.1.50:8084", params.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (cb != null)
                    cb.onResponse(null, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (cb == null)
                    return;
                try {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        cb.onResponse(new JSONObject(responseStr), null);
                    } else {
                        cb.onResponse(null, response.message());
                    }
                } catch (Exception e) {
                    cb.onResponse(null, e.getMessage());
                }
            }
        });
    }

    public static void JsonMerge(JSONObject src, JSONObject desc) {
        try {
            Iterator<String> it = desc.keys();
            while (it.hasNext()) {
                String key = it.next();
                src.put(key, desc.get(key));
            }
        } catch (Exception e) {
            System.out.println("JsonMerge : " + e);
        }
    }

    public static void JsonSave(String className, JSONObject table, Context act) {
        SharedPreferences sharedPreferences = act.getSharedPreferences("myapplication", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(className, table.toString());
        editor.commit();
    }

    public static void JsonLoad(String className, JSONObject table, Context act) {
        SharedPreferences sharedPreferences = act.getSharedPreferences("myapplication", MODE_PRIVATE);
        String json = sharedPreferences.getString(className, null);
        if (json == null)
            return;
        try {
            JSONObject loadedJson = new JSONObject(json);
            Utils.JsonMerge(table, loadedJson);
        } catch (Exception e) {
            System.out.println("JsonLoad : " + e);
        }
    }
}
