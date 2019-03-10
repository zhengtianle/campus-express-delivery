package com.example.mrzheng.lanlanapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Adapter.HomeItemAdapter;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Widget.HomeItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.mrzheng.lanlanapp.DataBaseService.HttpService.IP;

/**
 * Created by mrzheng on 18-5-28.
 */

public class MyReleaseTaskActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private List<TaskInfo> list;
    private HomeItemAdapter adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Toast.makeText(MyReleaseTaskActivity.this,"显示失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release_task);

        Intent intent = this.getIntent();
        String s = intent.getStringExtra("info");
        Gson gson = new Gson();
        list = gson.fromJson(s, new TypeToken<List<TaskInfo>>() {}.getType());

        recyclerView = (RecyclerView)findViewById(R.id.release_recyclerview_task);

        adapter = new HomeItemAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置方向
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new HomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (null != recyclerView.getChildViewHolder(view)){

                    Intent intent = new Intent(MyReleaseTaskActivity.this, TaskInformationActivity.class);

                    TaskInfo currentTask = new TaskInfo();
                    currentTask = list.get(position);
                    Gson gson = new Gson();
                    String s = gson.toJson(currentTask);
                    intent.putExtra("currentTask",s);
                    intent.putExtra("visiable","false");
                    intent.putExtra("position",position);
                    startActivity(intent);

                    //do something
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MyReleaseTaskActivity.this,position+1+" long click",Toast.LENGTH_SHORT).show();
            }
        });

    }



    /**
     * 返回键直接返回到主界面
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            finish();
            startActivity(new Intent(MyReleaseTaskActivity.this,HomeActivity.class));
        }

        return true;
    }
}
