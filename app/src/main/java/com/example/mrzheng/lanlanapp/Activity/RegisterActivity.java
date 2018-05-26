package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Extra.LimitInput;
import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-4-19.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnFocusChangeListener{

    private AppCompatButton buttonRegister;
    private EditText textPhone;
    private EditText textPassword;
    private EditText textConfirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        limitInput();
        register();
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

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.input_userpassword:
                if(hasFocus){
                    Toast.makeText(RegisterActivity.this,"支持“_”、“-”、数组、字母",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.confirm_password:
                if(hasFocus){
                    LimitInput.noSpace(textPassword);
                    LimitInput.noSpecialCharacter(textPassword);
                }
                break;
        }
    }

    private void register(){
        buttonRegister = findViewById(R.id.btn_register);
        textPhone = (EditText)findViewById(R.id.input_userphone);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textPhone==null||textPhone.getText().toString().equals("")||
                        textPassword==null||textPassword.getText().toString().equals("")||
                        textConfirmPassword==null||textConfirmPassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"所有内容为必填！",Toast.LENGTH_SHORT).show();
                }else if(!textConfirmPassword.getText().toString().equals(textPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"两次密码不相符！",Toast.LENGTH_SHORT).show();
                }else{
                    //buttonRegister.setText("Please Wait…");

                    Intent intent = new Intent(RegisterActivity.this,RegisterNextActivity.class);
                    startActivity(intent);
                }

            }
        });


    }


}
