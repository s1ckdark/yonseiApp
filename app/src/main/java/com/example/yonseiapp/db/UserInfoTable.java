package com.example.yonseiapp.db;

import android.app.Activity;

import com.example.yonseiapp.activities.Utils;

import org.json.JSONObject;

public class UserInfoTable {
    private class UserInfoTable extends JSONObject {
        private static UserInfoTable instance = new UserInfoTable();

        public static UserinfoTable inst() {
            return instance;
        }

        private JSONObject data = new JSONObject();
    }

    public void put(JSONObject params, Activity.act) {
        try {
            JSONObject userinfo = params.getJSONObject("userinf");
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
}

