package com.example.mrzheng.lanlanapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-4-19.
 */

public class RegisterNextActivity extends AppCompatActivity{
    private ProgressBar progressBar;
    private AppCompatButton buttonOk;
    private EditText textName;
    private EditText textSex;
    private EditText textStuId;
    private EditText textSchool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);



        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        sexClick();
        buttonClick();
    }

    /**
     * 实现男女性别选择框
     */
    private void sexClick(){
        textSex = (EditText)findViewById(R.id.input_sex);
        textSex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNextActivity.this);
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
            }
        });

    }

    /**
     * 注册按钮
     */
    private void buttonClick(){
        textName = (EditText)findViewById(R.id.input_name);
        textSchool = (EditText)findViewById(R.id.input_school);
        buttonOk = (AppCompatButton)findViewById(R.id.btn_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textSex==null||textSex.getText().toString().equals("")||
                        textStuId==null||textStuId.getText().toString().equals("")||
                        textName==null||textName.getText().toString().equals("")||
                        textSchool==null||textSchool.getText().toString().equals("")){

                    Toast.makeText(RegisterNextActivity.this,"所有内容为必填！",Toast.LENGTH_SHORT).show();
                }else if(!("男".equals(textSex.getText().toString())||
                        "女".equals(textSex.getText().toString()))){

                    Toast.makeText(RegisterNextActivity.this,"性别一栏只能填写“男”或“女”",Toast.LENGTH_SHORT).show();
                }else{
                    /**
                     * 注册成功
                     */
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(RegisterNextActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }//buttonClick
}
