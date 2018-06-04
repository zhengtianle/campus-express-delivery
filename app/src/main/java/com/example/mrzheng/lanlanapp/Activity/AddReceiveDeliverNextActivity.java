package com.example.mrzheng.lanlanapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.example.mrzheng.lanlanapp.Extra.Extras;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.example.mrzheng.lanlanapp.R;
import com.j256.ormlite.stmt.query.In;

/**
 * Created by mrzheng on 18-5-28.
 */

public class AddReceiveDeliverNextActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText senderName;
    private EditText senderTel;
    private EditText senderLocation;
    private EditText receiverName;
    private EditText receiverTel;
    private EditText receiverLocation;
    private EditText expressCompany;
    private AppCompatButton receiveButtonOk;
    private TextInputLayout selectCompany;

    private SearchInfo searchInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receive_next);

        findView();
        click();

        /*Intent intent3 = getIntent();
        searchInfo = (SearchInfo) intent3.getSerializableExtra(Extras.SEARCH_INFO);
        if(searchInfo!=null){
            expressCompany.setText(searchInfo.getName());
        }*/

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

        selectCompany = (TextInputLayout)findViewById(R.id.receive_select_company);

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

                Intent intent = new Intent(AddReceiveDeliverNextActivity.this,MyReleaseTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("return","HomeActivity.class");
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            /*case R.id.receive_select_company:
            case R.id.receive_express_company:

                Intent intent1 = new Intent(AddReceiveDeliverNextActivity.this,CompanyActivity.class);
                startActivity(intent1);

                break;*/
        }
    }
}
