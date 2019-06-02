package com.example.yonseiapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

public class Utils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    private static Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static void post(JSONObject params, final PostCallBack cb){
        post("http://192.168.1.50:8084", params.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (cb != null)
                    cb.onResponse(null, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response)  {
                if (cb == null)
                    return ;
                try {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        cb.onResponse(new JSONObject(responseStr), null);
                        // Do what you want to do with the response.
                    } else {
                        cb.onResponse(null, response.message());
                    }
                } catch (Exception e) {
                    cb.onResponse(null, e.getMessage());
                }

            }
        });
    }

    public interface PostCallBack {
        void onResponse(JSONObject ret, String errMsg);
    }

    public static void toast(final Activity act, final String msg) {
        act.runOnUiThread(new Runnable() {public void run() {
            Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
        }});
    }

    public static void JsonMerge(JSONObject src, JSONObject desc) {
        try {
            Iterator<String> it = desc.keys();
            while (it.hasNext()) {
                String key = it.next();
                src.put(key, desc.get(key));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void JsonSave(String className, JSONObject table, Context act){
        SharedPreferences sharedPreferences=act.getSharedPreferences("ysApp", MODE_PRIVATE);
        SharedPreferences.Editor = sharedPreferences.edit();
        editor.putString(className, table.toString());
        editor.commit();
    }

    public static void JsonLoad(String className, JSONObject table, Context act) {
        SharedPreferences sharedPreferences = act.getSharedPreferences("ysAPP", MODE_PRIVATE);
        String json = sharedPreferences.getString(className, null);
        if(json==null)
            return;
        try {
            JSONObject loadedJson = new JSONObject(json);
            Utils.JsonMerge(table, loadedJson);
        } catch (Exception e) {
        }
    }
}
