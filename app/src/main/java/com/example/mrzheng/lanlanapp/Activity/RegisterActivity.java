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
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Extra.LimitInput;
import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-4-19.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener{

    private EditText textPhone;
    private EditText textPassword;
    private EditText textConfirmPassword;
    private EditText textName;
    private EditText textSex;
    private EditText textStuId;
    private EditText textSchool;
    private Button buttonOk;

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
                    Toast.makeText(RegisterActivity.this,"支持“_”、“-”、数字、字母",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.confirm_password:
                if(hasFocus){
                    LimitInput.noSpace(textPassword);
                    LimitInput.noSpecialCharacter(textPassword);
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

                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }
}
