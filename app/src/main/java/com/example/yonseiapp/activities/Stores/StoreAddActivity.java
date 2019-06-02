package com.example.yonseiapp.activities.Stores;

import android.widget.EditText;

import com.example.yonseiapp.R;
import com.example.yonseiapp.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class StoreAddActivity {

    JSONObject params = new JSONObject();
    String url = "https://maps.googleapis.com/maps/api/geocode/json?";
    String address = ((EditText) findViewById(R.id.address)).getText().toString();
    Utils.post(url+"address="+address+"&key="+apiKey", params.toString(), new Callback(){
    @Override
    public void onFailure(Call call, IOException e){
        if(cb!=null)
            cb.onResponse(null, e.getMessage());
    }
    @Override
    public void onResponse(Call call, Response response){
        if(cb ==null)
            return;
        try {
            if(response.isSuccessful()) {
                String responseStr = response.body().string();
                cb, onResponse(new JSONObject(responseStr), null);
            }
            else {
                cb.onResponse(null, response.message());
            }
        } catch(Exception e) {
            cb.onResponse(null, e.getMessage());
        }
    }
});
    final Utils.PostCallBack cb = new Utils.PostCallBack() {
        @Override
        public void onResponse(JSONObject ret, String errMsg) {
            try {
                JSONObject result = ret.getJSONArray("results").getJSONObject(0);
                JSONObject gps = result.getJSONObject("geometry").getJSONObject("location");
                final Double lng = gps.getDouble("lng");
                final Double lat = gps.getDouble("lat");
                StoreAddActivity.this.runOnUiTrread(new Runnable(){
                    @Override
                    public void run(){
                        ((EditText) findViewById(R.id.lng)).setText(lng.toString());
                        ((EditText) findViewById(R.id.lat)).setText(lat.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
