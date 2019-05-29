package com.example.yonseiapp.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class SessionTable extends JSONObject {
    //Singleton
    private static SessionTable instance = new SessionTable();
    public static SessionTable inst() {return instance;}

    public void putSession(Activity act, String utk){
        try {
            put("utk", utk);

            SharedPreferences sharedPreferences = act.getSharedPreferences("ysAPP", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SessionTable", this.toString());
            editor.commit();
        } catch (Exception e) {}

    }

    public void pullSession(Activity act){
        try {
            remove("utk");

            SharedPreferences sharedPreferences = act.getSharedPreferences("ysAPP", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

        } catch(Exception e) {}
    }
    public String getSession() {
        try{
            String utk = getString("utk");
            return utk;
        }catch (Exception e) {

        }
        return null;
    }
}
