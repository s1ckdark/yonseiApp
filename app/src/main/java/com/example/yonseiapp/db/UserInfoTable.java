package com.example.yonseiapp.db;

import android.app.Activity;
import android.content.Context;

import com.example.yonseiapp.utils.Utils;

import org.json.JSONObject;

public class UserInfoTable extends JSONObject {

    private static com.example.yonseiapp.db.UserInfoTable instance = new com.example.yonseiapp.db.UserInfoTable();
    public static com.example.yonseiapp.db.UserInfoTable inst() {
        return instance;
    }

    private JSONObject data = new JSONObject();


public void put(JSONObject params, Activity act) {
    try {
        JSONObject userinfo = params.getJSONObject("userinfo");
        if(userinfo !=null) {
            Utils.JsonMerge(this, userinfo);
            Utils.JsonSave("UserInfoTable", this, act);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public JSONObject get() {
    try {
        return (JSONObject)this.get("userinfo");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public void del(Activity act) {
    Utils.JsonClear(this);
    Utils.JsonSave("UserInfoTable", this, act);
}
public void load(Context ctx){
    Utils.JsonLoad(UserInfoTable.class.getName(),this, ctx);
}
}
