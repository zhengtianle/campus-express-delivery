package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-5-28.
 */

public class AddReceiveDeliverNextActivity extends AppCompatActivity{

    private EditText senderName;
    private EditText senderTel;
    private EditText senderLocation;
    private EditText receiverName;
    private EditText receiverTel;
    private EditText receiverLocation;
    private EditText expressCompany;
    private AppCompatButton receiveButtonOk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receive_next);

        findView();
        buttonClick();

    }

    public void findView(){

        senderName = (EditText)findViewById(R.id.receive_sender_name);
        senderTel = (EditText)findViewById(R.id.receive_sender_tel);
        senderLocation = (EditText)findViewById(R.id.receive_sender_location);
        receiverName = (EditText)findViewById(R.id.receive_receiver_name);
        receiverTel = (EditText)findViewById(R.id.receive_receiver_tel);
        receiverLocation = (EditText)findViewById(R.id.receive_receiver_location);
        expressCompany = (EditText)findViewById(R.id.receive_express_company);
        receiveButtonOk = (AppCompatButton) findViewById(R.id.button_receive_ok);

    }

    public void buttonClick(){

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

    }

}
