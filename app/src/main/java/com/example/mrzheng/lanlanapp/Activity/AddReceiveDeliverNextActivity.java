package com.example.mrzheng.lanlanapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Extra.Extras;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.In;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-5-28.
 */

public class AddReceiveDeliverNextActivity extends AppCompatActivity
        implements View.OnClickListener,HttpService{

    private EditText senderName;
    private EditText senderTel;
    private EditText senderLocation;
    private EditText receiverName;
    private EditText receiverTel;
    private EditText receiverLocation;
    private EditText expressCompany;
    private EditText accessCode;
    private AppCompatButton receiveButtonOk;
    private TextInputLayout selectCompany;

    private SearchInfo searchInfo;

    /**
     * 从前一个界面传过来的数据
     */
    private String expressName;
    private String expressType;
    private String expressWeight;
    private String expressValue;
    private String money;
    private String note;
    private String meetingLocation;
    private String meetingTime;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    UserInfo.release_tasks = (Integer.parseInt(UserInfo.release_tasks)+1)+"";
                    TextView releaseTaskNumber = (TextView)findViewById(R.id.mine_release);
                    releaseTaskNumber.setText(UserInfo.release_tasks);
                    Intent intent = new Intent(AddReceiveDeliverNextActivity.this,MyReleaseTaskActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(AddReceiveDeliverNextActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receive_next);

        getData();

        findView();
        click();


    }

    /**
     * 得到从前一个界面传过来的数据
     */
    public void getData(){

        Bundle bundle = this.getIntent().getExtras();
        expressName = bundle.getString("expressName");
        expressType = bundle.getString("expressType");
        expressWeight = bundle.getString("expressWeight");
        expressValue = bundle.getString("expressValue");
        money = bundle.getString("money");
        note = bundle.getString("note");
        meetingLocation = bundle.getString("meetingLocation");
        meetingTime = bundle.getString("meetingTime");

    }

    public void findView(){

        senderName = (EditText)findViewById(R.id.receive_sender_name);
        senderTel = (EditText)findViewById(R.id.receive_sender_tel);
        senderLocation = (EditText)findViewById(R.id.receive_sender_location);
        receiverName = (EditText)findViewById(R.id.receive_receiver_name);
        receiverTel = (EditText)findViewById(R.id.receive_receiver_tel);
        receiverLocation = (EditText)findViewById(R.id.receive_receiver_location);
        expressCompany = (EditText)findViewById(R.id.receive_express_company);
        accessCode = (EditText)findViewById(R.id.receive_access_code);
        receiveButtonOk = (AppCompatButton) findViewById(R.id.button_receive_ok);

        selectCompany = (TextInputLayout)findViewById(R.id.receive_select_company);

        receiverName.setText(UserInfo.name);
        receiverTel.setText(UserInfo.tel);

    }

    /*public void buttonClick(){

        receiveButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddReceiveDeliverNextActivity.this,MyReleaseTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("return","HomeActivity.class");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }*/

    public void click(){
        receiveButtonOk.setOnClickListener(this);
        /*selectCompany.setOnClickListener(this);
        expressCompany.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.button_receive_ok:

                //检测是否已经联网
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || networkInfo.isAvailable()){
                    //当前有可用网络

                    httpPost();

                }else{
                    //当前没有可用网络
                    Toast toast = Toast.makeText(AddReceiveDeliverNextActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
            /*case R.id.receive_select_company:
            case R.id.receive_express_company:

                Intent intent1 = new Intent(AddReceiveDeliverNextActivity.this,CompanyActivity.class);
                startActivity(intent1);

                break;*/
        }
    }

    public void httpPost(){

        new Thread(()->{
            try{

                String url = IP+"/AddTaskServlet";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("stu_id",UserInfo.stu_id)
                        .add("express_name",expressName)
                        .add("express_type",expressType)
                        .add("express_weight",expressWeight)
                        .add("express_value",expressValue)
                        .add("meeting_location",meetingLocation)
                        .add("meeting_time",meetingTime)
                        .add("note",note)
                        .add("sender_name",senderName.getText().toString())
                        .add("sender_tel",senderTel.getText().toString())
                        .add("sender_location",senderLocation.getText().toString())
                        .add("receiver_name",receiverName.getText().toString())
                        .add("receiver_tel",receiverTel.getText().toString())
                        .add("receiver_location",receiverLocation.getText().toString())
                        .add("express_company",expressCompany.getText().toString())
                        .add("money",money)
                        .add("type","代收快递")
                        .add("tel",UserInfo.tel)
                        .add("nickname",UserInfo.nickname)
                        .add("sex",UserInfo.sex)
                        .add("name",UserInfo.name)
                        .add("school",UserInfo.school)
                        .add("grade",UserInfo.grade)
                        //其实就比sendDeliver多了一个accessCode(取货码)
                        .add("access_code",accessCode.getText().toString())
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
