package com.example.mrzheng.lanlanapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-6-2.
 */

public class ModifyPersonalInfoActivity extends AppCompatActivity
        implements View.OnClickListener, HttpService{

    private CircleImageView avator;
    private RelativeLayout changeAvator;
    private EditText nickname;
    private EditText sex;
    private EditText name;
    private EditText school;
    private Button save;

    //下面为只读
    private EditText stuId;
    private EditText tel;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    /**
                     * 改变全局变量中的个人用户信息
                     * 跳转到主界面
                     */
                    UserInfo.school = school.getText().toString();
                    UserInfo.nickname = nickname.getText().toString();
                    UserInfo.sex = sex.getText().toString();
                    UserInfo.name = name.getText().toString();

                    Toast.makeText(ModifyPersonalInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ModifyPersonalInfoActivity.this,HomeActivity.class));

                    break;
                case 2:
                    Toast.makeText(ModifyPersonalInfoActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);

        initView();
        initInformation();
        setClickListener();

    }

    public void initView(){
        avator = (CircleImageView)findViewById(R.id.avatar);
        changeAvator = (RelativeLayout)findViewById(R.id.change_avator);
        nickname = (EditText)findViewById(R.id.nickname);
        sex = (EditText)findViewById(R.id.sex);
        name = (EditText)findViewById(R.id.name);
        school = (EditText)findViewById(R.id.school);
        stuId = (EditText)findViewById(R.id.stuid);
        tel = (EditText)findViewById(R.id.tel);
        save = (Button)findViewById(R.id.save);
    }

    public void initInformation(){

        nickname.setText(UserInfo.nickname);
        sex.setText(UserInfo.sex);
        name.setText(UserInfo.name);
        school.setText(UserInfo.school);
        stuId.setText(UserInfo.stu_id);
        tel.setText(UserInfo.tel);

    }

    public void setClickListener(){
        changeAvator.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_avator:
                /**
                 * 保存头像
                 */
                Toast.makeText(ModifyPersonalInfoActivity.this,"修改头像",Toast.LENGTH_SHORT).show();
                break;
            case R.id.save:
                /**
                 * 保存除了头像以外的其他个人信息
                 */
                //检测是否已经联网
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || networkInfo.isAvailable()){
                    //当前有可用网络
                    httpPost(nickname.getText().toString(),
                            sex.getText().toString(),
                            name.getText().toString(),
                            school.getText().toString(),
                            stuId.getText().toString());//学号用来定位查询，并不能更改


                }else{
                    //当前没有可用网络
                    Toast toast = Toast.makeText(ModifyPersonalInfoActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
        }
    }


    public void httpPost(String nickname,String sex,String name,String school,String stu_id){

        new Thread(()->{

            try{
                String url =IP+"/ModifyUserInfoServlet";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("nickname",nickname)
                        .add("name",name)
                        .add("sex",sex)
                        .add("school",school)
                        .add("stu_id",stu_id)
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
