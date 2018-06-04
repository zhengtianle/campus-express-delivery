package com.example.mrzheng.lanlanapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Model.TaskEntity;
import com.example.mrzheng.lanlanapp.R;
import com.j256.ormlite.stmt.query.In;

/**
 * Created by mrzheng on 18-6-3.
 */

public class TaskInformationActivity extends AppCompatActivity implements View.OnClickListener {

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

    private TaskEntity currentTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);
        Intent intent = getIntent();
        currentTask= new TaskEntity();
        currentTask = (TaskEntity) intent.getParcelableExtra("currentTask");

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

        type.setText(currentTask.getType());
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
        note.setText(currentTask.getNote());



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
                Toast.makeText(TaskInformationActivity.this,"接受任务成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TaskInformationActivity.this,HomeActivity.class));

                break;
        }
    }


}
