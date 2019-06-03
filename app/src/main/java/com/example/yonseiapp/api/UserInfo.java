package com.example.yonseiapp.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.yonseiapp.db.SessionTable;
import com.example.yonseiapp.utils.
import com.example.yonseiapp.utils.Utils;
import com.example.yonseiapp.db.UserInfoTable;

import org.json.JSONObject;

public class UserInfo {
    public static void put(final Activity act, String name, String age, @NonNull final RetCallBack cb){
        try{
            JSONObject params = new JSONObject(());
            params.put("command","userinfo_put");

            params.put("name",name);
            params.put("age",age);

            params.put("utk", SessionTable.inst().getSession());

            Utils.post(params, new Utils.PostCallBack(){
                @Override
                public void onResponse(JSONObject ret, String errMsg){
                    try {
                        if(ret==null || ret.getBoolean("ret") != true)
                            cb.onResponse(false, "error or Save");
                        else
                            cb.onResponse(true, null);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void get(final Activity act, final @NonNull RetCallBack cb){
        try {
            JSONObject params = new JSONObject();
            params.put("command", "userinfo_get");
            params.put("utk", SessionTable.inst().getSession());

            Utils.post(params, new Utils.PostCallBack() {
                @Override
                public void onResponse(JSONObject ret, String errMsg) {
                    try {
                        if (ret == null || ret.getBoolean("ret") == true) {
                            cb.onResponse(false, errMsg);
                        }else {
                            UserInfoTable.inst().put(ret, act);
                            cb.onResponse(true, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
