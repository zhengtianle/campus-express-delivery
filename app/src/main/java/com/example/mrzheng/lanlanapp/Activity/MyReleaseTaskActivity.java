package com.example.mrzheng.lanlanapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-5-28.
 */

public class MyReleaseTaskActivity extends AppCompatActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release_task);

    }

    /**
     * 返回键直接返回到主界面
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(MyReleaseTaskActivity.this,HomeActivity.class));
        }

        return true;
    }
}
