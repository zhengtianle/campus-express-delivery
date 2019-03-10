package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Adapter.HomeItemAdapter;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by mrzheng on 18-5-28.
 */

public class MyAcceptTaskActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private List<TaskInfo> list;
    private HomeItemAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accept_task);

        Intent intent = this.getIntent();
        String s = intent.getStringExtra("info");
        Gson gson = new Gson();
        list = gson.fromJson(s, new TypeToken<List<TaskInfo>>() {}.getType());

        recyclerView = (RecyclerView)findViewById(R.id.accept_recyclerview_task);

        adapter = new HomeItemAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置方向
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new HomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (null != recyclerView.getChildViewHolder(view)){

                    Intent intent = new Intent(MyAcceptTaskActivity.this, TaskInformationActivity.class);

                    TaskInfo currentTask = new TaskInfo();
                    currentTask = list.get(position);
                    Gson gson = new Gson();
                    String s = gson.toJson(currentTask);
                    intent.putExtra("currentTask",s);
                    intent.putExtra("visiable","false");
                    startActivity(intent);

                    //do something
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MyAcceptTaskActivity.this,position+1+" long click",Toast.LENGTH_SHORT).show();
            }
        });

    }




}
