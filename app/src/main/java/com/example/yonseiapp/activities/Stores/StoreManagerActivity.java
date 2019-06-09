package com.example.yonseiapp.activities.Stores;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yonseiapp.R;
import com.example.yonseiapp.db.StoreTable;
import com.example.yonseiapp.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class StoreManagerActivity extends AppCompatActivity {
    StoreAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        ((TextView) findViewById(R.id.topTitle)).setText("상점관리");
//        findViewById(R.id.topRight).setVisibility(View.GONE);

        TextView topRight = ((TextView) findViewById(R.id.topRight));
        topRight.setText("상점추가");

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StoreManagerActivity.this, StoreAddActivity.class);
                startActivity(i);
            }
        });

        if (StoreTable.inst().size() == 0)
            putStores();

        adapter = new StoreAdapter(new StoreAdapter.DelCallBack() {
            @Override
            public void del(final int idx) {
                new AlertDialog.Builder(StoreManagerActivity.this)
                        .setMessage("지우시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StoreTable.inst().del(idx);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        ((RecyclerView)findViewById(R.id.recyclerView)).setAdapter(new StoreAdapter());
    }

    private void putStores() {
        String[] names = {"수타벅스", "카피빈", "파스부처", "삼보텐", "또래주루", "런던바케트", "사천리자전거", "알통스포츠"};
        for (int i = 0; i < names.length; i++)
            StoreTable.inst().put(i, names[i]);
    }

    public void onClickBack(View v) {
        finish();
    }


}