package com.example.yonseiapp.activities.Stores;

import java.io.IOException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yonseiapp.R;
import com.example.yonseiapp.db.StoreTable;

public class StoreManagerActivity extends AppCompatActivity {
//    StoreAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        ((TextView)findViewById(R.id.topTitle)).setText("StoreManager");
//      findViewById(R.id.topRight).setVisibility(View.GONE);

        TextView topRight = ((TextView)findViewById(R.id.topRight));
        topRight.setText("Add Store");
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreManagerActivity.this, StoreAddActivity.class);
                startActivity(i);
            }
        });


        if (StoreTable.inst().size() == 0)
            putStores();

        ((RecyclerView)findViewById(R.id.recyclerView)).setAdapter(new StoreAdapter());


//        adapter = new StoreAdapter(new StoreAdapter.DelCallBack() {
//            @Override
//            public void del(int idx) {
//                new AlertDialog.Builder(StoreManagerActivity.this)
//                        .setMessage("지우시겠습니까?");
//                StoreTable.inst().del(idx);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        ((RecyclerView)findViewById(R.id.recyclerView)).setAdapter(adapter);
    }

    private void putStores() {
        String[] names = {"Starbucks", "Coffeebean", "Bluebottle", "Ediya", "Orot", "Zagmachi", "Nespresso", "Illy"};
        for (int i = 0; i < names.length; i++)
            StoreTable.inst().put(i, names[i]);
    }

    public void onClickBack(View v) {
        finish();
    }


}