package com.example.yonseiapp.activities.Stores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yonseiapp.R;
import com.example.yonseiapp.db.StoreTable;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.yonseiapp.db.StoreTable.inst;

public class StoreAdapter extends RecyclerView.Adapter {


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button del;

        //itemView는 item_store layout을 view로 만들어 준 것을 넘겨준다.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            del = itemView.findViewById(R.id.del);
        }
    }

    //지우기 방법 2 - 메인 Activity에서 함수처리
//    interface DelCallBack{
//        void del(int idx);}
//        DelCallBack delcallBack = null;
//
//    //지우기 버튼을 누르면 이 콜백을 실행시킴킴
//   public StoreAdapter(DelCallBack cb){
//        delcallBack = cb;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Layout에서 View를 만들어주는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_store, viewGroup, false);

        //ViewHolder을 만들고 위에서 만든 view와 연결한다.
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);

        //ViewHolder return
        return viewHolder;
    }

    // final 붙이는 이유? -> Handler는 자신을 등록하는 외부 메서드의 지역변수를 참조해야 하는데,
    // 이때 지역 변수에 final 지정자를 반드시 붙여야 한다.
    // (final을 붙이게 되면 그 값은 더 이상 변경할 수 없는 상수가 됨)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        JSONObject item = StoreTable.inst().get(i);
        try{
            ViewHolder v = (ViewHolder)viewHolder;
            v.name.setText(item.getString("name"));
            v.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoreTable.inst().del(i);
                    notifyDataSetChanged();
//                    delcallBack.del(i);
//                    notifyDataSetChanged();
                }
            });
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return StoreTable.inst().size();
    }

}