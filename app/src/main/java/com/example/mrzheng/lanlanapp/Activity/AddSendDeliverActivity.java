package com.example.mrzheng.lanlanapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mrzheng on 18-5-27.
 */

public class AddSendDeliverActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText expressName;
    private EditText expressType;
    private EditText expressWeight;
    private EditText expressValue;
    private EditText money;
    private EditText note;
    private EditText meetingLocation;
    private EditText meetingTime;
    private LinearLayout sendSelectTime;
    private AppCompatButton sendButtonNext;

    //时间选择器组件
    private DatePicker meetingDatePicker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_send);

        findView();
        typeClick();
        weightClick();

        initDatePicker();

        sendSelectTime.setOnClickListener(this);
        sendButtonNext.setOnClickListener(this);
        meetingTime.setOnClickListener(this);
        meetingTime.setInputType(InputType.TYPE_NULL);

    }

    public void findView(){

        expressName = (EditText)findViewById(R.id.send_express_name);
        expressType = (EditText)findViewById(R.id.send_express_type);
        expressWeight = (EditText)findViewById(R.id.send_express_weight);
        expressValue = (EditText)findViewById(R.id.send_express_value);
        money = (EditText)findViewById(R.id.send_money);
        note = (EditText)findViewById(R.id.send_note);
        meetingLocation = (EditText)findViewById(R.id.send_meeting_location);
        meetingTime = (EditText)findViewById(R.id.send_meeting_time);
        sendSelectTime = (LinearLayout) findViewById(R.id.send_select_time);
        sendButtonNext = (AppCompatButton) findViewById(R.id.button_send_next);

    }

    /**
     * 物件类型选择框
     */
    private void typeClick(){

        expressType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSendDeliverActivity.this);
                    String[] type = {"电子产品","衣物","日用品","食物"};
                    builder.setItems(type, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String thisSex = "电子产品";
                            switch (which){
                                case 0:thisSex = "电子产品";break;
                                case 1:thisSex = "衣物";break;
                                case 2:thisSex = "日用品";break;
                                case 3:thisSex = "食物";break;
                            }

                            expressType.setText(thisSex);

                        }//onClick
                    });
                    builder.show();
                }
            }
        });

    }//typeClick

    /**
     * 物件重量选择框
     */
    private void weightClick(){

        expressWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSendDeliverActivity.this);
                    String[] weight = {"1","2","3","4","5-8","8-10","10+"};
                    builder.setItems(weight, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String thisSex = "1";
                            switch (which){
                                case 0:thisSex = "1";break;
                                case 1:thisSex = "2";break;
                                case 2:thisSex = "3";break;
                                case 3:thisSex = "4";break;
                                case 4:thisSex = "5-8";break;
                                case 5:thisSex = "8-10";break;
                                case 6:thisSex = "10+";break;
                            }

                            expressWeight.setText(thisSex);

                        }//onClick
                    });
                    builder.show();
                }
            }
        });

    }//weightClick



    /**
     * 时间选择器
     * 下一步按钮
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.send_select_time:

                meetingDatePicker.show(meetingTime.getText().toString());
                //点击获取完时间，让下一个控件获取焦点,即不能手动修改时间
                meetingLocation.requestFocus();
                break;
            case R.id.send_meeting_time:

                meetingDatePicker.show(meetingTime.getText().toString());
                //点击获取完时间，让下一个控件获取焦点,即不能手动修改时间
                meetingLocation.requestFocus();
                break;
            case R.id.button_send_next:

                startActivity(new Intent(AddSendDeliverActivity.this,AddSendDeliverNextActivity.class));
                break;

        }

    }//onClick

    public void initDatePicker(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //currentDate.setText(now.split(" ")[0]);
        meetingTime.setText(now);

        meetingDatePicker = new DatePicker(this, new DatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                meetingTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        meetingDatePicker.showSpecificTime(true); // 显示时和分
        meetingDatePicker.setIsLoop(true); // 允许循环滚动

    }


}
