package com.example.mrzheng.lanlanapp.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Extra.LimitInput;
import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-4-19.
 */

public class RegisterActivity extends AppCompatActivity
        implements View.OnFocusChangeListener,
        View.OnClickListener, HttpService{

    private EditText textPhone;
    private EditText textPassword;
    private EditText textConfirmPassword;
    private EditText textName;
    private EditText textSex;
    private EditText textStuId;
    private EditText textSchool;
    private Button buttonOk;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case 1:
                    //注册完之后还是要登录的！！！
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        limitInput();

        buttonOk.setOnClickListener(this);
    }

    public void initView(){
        textPhone = (EditText)findViewById(R.id.input_userphone);
        textPassword = (EditText)findViewById(R.id.input_userpassword);
        textConfirmPassword = (EditText)findViewById(R.id.confirm_password);
        textName = (EditText)findViewById(R.id.input_name);
        textSex = (EditText)findViewById(R.id.input_sex);
        textStuId = (EditText)findViewById(R.id.input_stuid);
        textSchool = (EditText)findViewById(R.id.input_school);
        buttonOk = (Button)findViewById(R.id.btn_ok);
    }

    /**
     * 限制editext的输入，不能输入空格或者某些特殊字符
     * 并且监听确认密码的输入框，使其必须和密码框内内容一致
     */
    private void limitInput(){
        textPassword = (EditText)findViewById(R.id.input_userpassword);
        textConfirmPassword = (EditText)findViewById(R.id.confirm_password);

        //焦点监听
        textPassword.setOnFocusChangeListener(this);
        textConfirmPassword.setOnFocusChangeListener(this);
        textSex.setOnFocusChangeListener(this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.input_userpassword:
                if(hasFocus){
                    LimitInput.noSpecialCharacter(textPassword);
                    Toast.makeText(RegisterActivity.this,"支持“_”、“-”、数字、字母",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.confirm_password:
                if(hasFocus){

                }
                break;
            case R.id.input_sex:
                if(hasFocus){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    String[] sex = {"男","女"};
                    builder.setItems(sex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String thisSex = "男";
                            if(which ==0){
                                thisSex = "男";
                            }else{
                                thisSex = "女";
                            }

                            textSex.setText(thisSex);
                            //让下一个输入框获取焦点
                            textStuId = (EditText)findViewById(R.id.input_stuid);
                            textStuId.requestFocus();
                        }//onClick
                    });
                    builder.show();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:

                if(textPhone==null||textPhone.getText().toString().equals("")||
                        textPassword==null||textPassword.getText().toString().equals("")||
                        textConfirmPassword==null||textConfirmPassword.getText().toString().equals("")||
                        textSex==null||textSex.getText().toString().equals("")||
                        textStuId==null||textStuId.getText().toString().equals("")||
                        textName==null||textName.getText().toString().equals("")||
                        textSchool==null||textSchool.getText().toString().equals("")){

                    Toast.makeText(RegisterActivity.this,"所有内容为必填！",Toast.LENGTH_SHORT).show();

                }else if(!textConfirmPassword.getText().toString().equals(textPassword.getText().toString())){

                    Toast.makeText(RegisterActivity.this,"两次密码不相符！",Toast.LENGTH_SHORT).show();

                }else if(!("男".equals(textSex.getText().toString())|| "女".equals(textSex.getText().toString()))){

                    Toast.makeText(RegisterActivity.this,"性别一栏只能填写“男”或“女”",Toast.LENGTH_SHORT).show();

                } else{
                    //检测是否已经联网
                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo == null || networkInfo.isAvailable()){
                        //当前有可用网络

                        httpPost(textPhone.getText().toString(),
                                textPassword.getText().toString(),
                                textName.getText().toString(),
                                textSex.getText().toString(),
                                textStuId.getText().toString(),
                                textSchool.getText().toString());

                    }else{
                        //当前没有可用网络
                        Toast toast = Toast.makeText(RegisterActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }

                break;
        }
    }


    public void httpPost(String tel,String passwords,
                         String name,String sex,
                         String stu_id,String school){

        new Thread(()->{

            try{

                String url = IP+"/RegisterServlet";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("tel",tel)
                        .add("passwords",passwords)
                        .add("name",name)
                        .add("sex",sex)
                        .add("stu_id",stu_id)
                        .add("school",school)
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
