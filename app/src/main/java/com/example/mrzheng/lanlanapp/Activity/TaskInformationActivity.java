package com.example.mrzheng.lanlanapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Model.TaskEntity;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.In;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-6-3.
 */

public class TaskInformationActivity extends AppCompatActivity
        implements View.OnClickListener,HttpService {
    private ImageView phone;
    private EditText releaseUserTel;
    private EditText releaseUserNickname;
    private EditText releaseUserSex;
    private EditText releaseUserSchool;
    private EditText releaseUserGrade;
    private EditText expressName;
    private EditText expressType;
    private EditText expressWeight;
    private EditText expressValue;
    private EditText meetingLocation;
    private EditText meetingTime;
    private EditText senderName;
    private EditText senderTel;
    private EditText senderLocation;
    private EditText receiverName;
    private EditText receiverTel;
    private EditText receiverLocation;
    private EditText expressCompany;
    private EditText money;
    private EditText note;
    private Button receiveThisTask;
    private EditText type;

    /*private TaskEntity currentTask;*/
    private TaskInfo currentTask;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 1:
                    UserInfo.receive_tasks = (Integer.parseInt(UserInfo.receive_tasks)+1)+"";
                    TextView receiveTaskNumber = (TextView)findViewById(R.id.mine_receive);
                    receiveTaskNumber.setText(UserInfo.receive_tasks);
                    Toast.makeText(TaskInformationActivity.this,"接受任务成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TaskInformationActivity.this,HomeActivity.class));
                    finish();
                    break;
                case 2:
                    Toast.makeText(TaskInformationActivity.this,"该任务已被接受",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TaskInformationActivity.this,HomeActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);
        Intent intent = getIntent();
        /*currentTask= new TaskEntity();
        currentTask = (TaskEntity) intent.getParcelableExtra("currentTask");*/

        currentTask = new TaskInfo();
        String s = intent.getStringExtra("currentTask");
        Gson gson = new Gson();
        currentTask = gson.fromJson(s,TaskInfo.class);



        initView();
    }

    public void initView() {
        phone = (ImageView) findViewById(R.id.release_user_phone);
        releaseUserTel = (EditText) findViewById(R.id.release_user_tel);
        releaseUserNickname = (EditText) findViewById(R.id.release_user_nickname);
        releaseUserSex = (EditText) findViewById(R.id.release_user_sex);
        releaseUserSchool = (EditText) findViewById(R.id.release_user_school);
        releaseUserGrade = (EditText) findViewById(R.id.release_user_grade);
        expressName = (EditText) findViewById(R.id.express_name);
        expressType = (EditText) findViewById(R.id.express_type);
        expressWeight = (EditText) findViewById(R.id.express_weight);
        expressValue = (EditText) findViewById(R.id.express_value);
        meetingTime = (EditText) findViewById(R.id.meeting_time);
        meetingLocation = (EditText) findViewById(R.id.meeting_location);
        senderName = (EditText) findViewById(R.id.sender_name);
        senderTel = (EditText) findViewById(R.id.sender_tel);
        senderLocation = (EditText) findViewById(R.id.sender_location);
        receiverName = (EditText) findViewById(R.id.receiver_name);
        receiverTel = (EditText) findViewById(R.id.receiver_tel);
        receiverLocation = (EditText) findViewById(R.id.receiver_location);
        expressCompany = (EditText) findViewById(R.id.express_company);
        money = (EditText)findViewById(R.id.money);
        note = (EditText)findViewById(R.id.note);
        receiveThisTask = (Button)findViewById(R.id.receive_this_task);
        type = (EditText)findViewById(R.id.type);

        /*type.setText(currentTask.getType());
        releaseUserNickname.setText(currentTask.getReleaseUserNickname());
        releaseUserTel.setText(currentTask.getReleaseUserTel());
        releaseUserSex.setText(currentTask.getReleaseUserSex());
        releaseUserSchool.setText(currentTask.getReleaseUserSchool());
        releaseUserGrade.setText(Double.valueOf(currentTask.getReleaseUserGrade()).toString());

        expressName.setText(currentTask.getDeliver());
        expressType.setText(currentTask.getExpressType());
        expressWeight.setText(Integer.valueOf(currentTask.getWeight()).toString());
        expressValue.setText(Integer.valueOf(currentTask.getPrice()).toString());
        meetingLocation.setText(currentTask.getMeetingLocation());
        meetingTime.setText(currentTask.getMeetingTime());
        senderName.setText(currentTask.getSenderName());
        senderTel.setText(currentTask.getSenderTel());
        senderLocation.setText(currentTask.getSenderLocation());
        receiverName.setText(currentTask.getReceiverName());
        receiverTel.setText(currentTask.getReceiverTel());
        receiverLocation.setText(currentTask.getReceiverLocation());
        expressCompany.setText(currentTask.getCompany());
        money.setText(Integer.valueOf(currentTask.getMoney()).toString());
        note.setText(currentTask.getNote());*/

        type.setText(currentTask.type);
        releaseUserNickname.setText(currentTask.nickname);
        releaseUserTel.setText(currentTask.tel);
        releaseUserSex.setText(currentTask.sex);
        releaseUserSchool.setText(currentTask.school);
        releaseUserGrade.setText(currentTask.grade);

        expressName.setText(currentTask.express_name);
        expressType.setText(currentTask.express_type);
        expressWeight.setText(currentTask.express_weight);
        expressValue.setText(currentTask.express_value);
        meetingLocation.setText(currentTask.meeting_location);
        meetingTime.setText(currentTask.meeting_time);
        senderName.setText(currentTask.sender_name);
        senderTel.setText(currentTask.sender_tel);
        senderLocation.setText(currentTask.sender_location);
        receiverName.setText(currentTask.receiver_name);
        receiverTel.setText(currentTask.receiver_tel);
        receiverLocation.setText(currentTask.receiver_location);
        expressCompany.setText(currentTask.express_company);
        money.setText(currentTask.money);
        note.setText(currentTask.note);



        phone.setOnClickListener(this);
        receiveThisTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_user_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + releaseUserTel.getText().toString());
                intent.setData(data);
                startActivity(intent);
                break;

            case R.id.receive_this_task:

                httpPost();

                break;
        }
    }

    public void httpPost(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        new Thread(()->{

            try{

                String url = IP+"/ReceiveThisTask";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("task_id",currentTask.task_id)
                        .add("sender_id",currentTask.stu_id)
                        .add("sender_tel",currentTask.tel)
                        .add("receiver_id", UserInfo.stu_id)
                        .add("receiver_tel",UserInfo.tel)
                        .add("praise","5.0")//暂时默认为5.0评分
                        .add("send_time",df.format(new Date()))//暂时默认为当前时间
                        .add("receive_time",df.format(new Date()))
                        .add("finish_time",df.format(new Date()))
                        .add("express_number","123456789101112")//随便默认一个快递单号
                        .add("task_type",currentTask.type)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                if(response.isSuccessful()){
                    String str = response.body().string();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    Type type = new TypeToken<Map<String,String>>(){}.getType();

                    Map<String,String> map = gson.fromJson(str,type);
                    Message message = new Message();

                    if(map.get("tag").equals("success")){
                        message.what = 1;
                    }else{
                        message.what = 2;
                        message.obj = map.get("info");
                    }

                    handler.sendMessage(message);

                }


            }catch (IOException e){
                e.printStackTrace();
            }

        }).start();
    }


}
