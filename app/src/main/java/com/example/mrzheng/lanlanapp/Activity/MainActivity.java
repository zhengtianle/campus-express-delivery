package com.example.mrzheng.lanlanapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Extra.StatusBarUtils;
import com.example.mrzheng.lanlanapp.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.iv_entry) ImageView imageOfWelcome;

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private static final int[] Imgs = {
            R.drawable.welcome1};
    Boolean isFirstStart;

    private static boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次开启应用
        isFirstStart  = false;
        SharedPreferences pref = getSharedPreferences("isFirstIn",0);
        isFirstStart = pref.getBoolean("isFirstStart",true);

        if(isFirstStart){
            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intent);
            finish();
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirstStart",false);
            editor.apply();

            return ;
        }


        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_welcome);

        StatusBarUtils.setImage(this);

        ButterKnife.bind(this);
        startMainActivity();
    }

    private void startMainActivity() {
        Random random = new Random(SystemClock.elapsedRealtime());// 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        imageOfWelcome.setImageResource(Imgs[random.nextInt(Imgs.length)]);

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {

                    @Override
                    public void call(Long aLong) {
                        startAnim();
                    }
                });
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageOfWelcome, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageOfWelcome, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
            }
        });
    }

    /**
     * 屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
