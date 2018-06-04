package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrzheng on 18-6-2.
 */

public class ModifyPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);

        initView();
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

    public void setClickListener(){
        changeAvator.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_avator:
                Toast.makeText(ModifyPersonalInfoActivity.this,"修改头像",Toast.LENGTH_SHORT).show();
                break;
            case R.id.save:
                Toast.makeText(ModifyPersonalInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();


                startActivity(new Intent(ModifyPersonalInfoActivity.this,HomeActivity.class));
                break;
        }
    }


}
