package com.example.yonseiapp.db;

import org.json.JSONArray;
import org.json.JSONObject;

public class StoreTable {
    private static StoreTable inst = new StoreTable();
    public static StoreTable inst() {return inst;}

    private JSONArray data = new JSONArray();

    public void put(int sidx,String name){
        try {
            JSONObject store = new JSONObject();
            store.put("sidx",sidx);
            store.put("name",name);
            data.put(store);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject get(int pos){
        try {
            return data.getJSONObject(pos);
        } catch (Exception e){}
        return null;
    }

    public int size(){return data.length();}
}
