package com.example.mrzheng.lanlanapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mrzheng.lanlanapp.DataBaseService.LoginService;
import com.example.mrzheng.lanlanapp.R;

import java.util.Timer;

/**
 * Created by mrzheng on 18-4-12.
 */

public class LoginActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private AppCompatButton buttonLogin;
    private TextView textViewRegister;

    private String info;
    private EditText tel;
    private EditText passwords;
    //返回主线程更新数据
    /*private static Handler handler = new Handler();*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        tel = (EditText)findViewById(R.id.input_phone);
        passwords = (EditText)findViewById(R.id.input_password);

        loginAndRegister();

    }//onCreate

    private void loginAndRegister(){
        /**
         * login
         */
        buttonLogin = (AppCompatButton)findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检测是否已经联网
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || networkInfo.isAvailable()){
                    //当前有可用网络
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    progressBar.setVisibility(View.GONE);
                    startActivity(intent);
                    finish();

                    //创建子线程，进行Post传输
                    /*new Thread(new MyThread()).start();

                    if(info == "Success"){
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        progressBar.setVisibility(View.GONE);
                        startActivity(intent);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }*/

                }else{
                    //当前没有可用网络
                    Toast toast = Toast.makeText(LoginActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }//onClick
        });

        /**
         * register
         */
        textViewRegister = (TextView)findViewById(R.id.link_signup);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    /*//子线程接收数据，主线程修改数据
    public class MyThread implements Runnable{
        @Override
        public void run() {
            info = LoginService.executeHttpPost(tel.getText().toString(),passwords.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //buttonLogin.setText(info);
                }
            });
        }
    }*/


}
