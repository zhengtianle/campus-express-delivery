package com.example.mrzheng.lanlanapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Fragment.DeliverFragment;
import com.example.mrzheng.lanlanapp.Fragment.MineFragment;
import com.example.mrzheng.lanlanapp.Fragment.MoreFragment;
import com.example.mrzheng.lanlanapp.Fragment.TaskFragment;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.mrzheng.lanlanapp.DataBaseService.HttpService.IP;
import static com.example.mrzheng.lanlanapp.R.id.menu_main_item_home;

/**
 * Created by mrzheng on 18-5-2.
 */

public class HomeActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{
    private FrameLayout mFrameLayout;
    private TaskFragment taskFragment;
    private DeliverFragment deliverFragment;
    private MineFragment mineFragment;
    private MoreFragment moreFragment;
    //private LinearLayout tvLinearLayout;
    //private CommonBehavior mToolBarBehavior;
    private BottomNavigationView mBottomMainNavigation;
    private CommonBehavior mBottomBehavior;
    //private CommonBehavior mFloatButtonBehavior;
    //private FloatingActionButton mFloatingActionButton;

    private ClipData.Item itemHome;
    private ClipData.Item itemDeliver;
    private ClipData.Item itemMine;
    private ClipData.Item itemMore;

    public static Fragment currentMainFragment;//全局变量保存当前fragment
    public static FragmentManager manager;
    public static FragmentTransaction transaction;

    private FloatingActionsMenu menuMultipleActions;
    private FloatingActionButton addSendDeliver;
    private FloatingActionButton addReceiveDeliver;

    private long firstPressedTime;

    private List<TaskInfo> list;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    taskFragment.refresh();
                    Toast.makeText(HomeActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(HomeActivity.this,"刷新失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);

        //StatusBarUtils.setColor(this,Color.parseColor("#00c853"));

        //itemHome = findViewById(R.id.menu_main_item_home);


        mFrameLayout = (FrameLayout) findViewById(R.id.frame_main);
        //mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_mode);
        mBottomMainNavigation = (BottomNavigationView) findViewById(R.id.bottom_main_navigation);
        menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        loadFragment(savedInstanceState);

        //mToolBarBehavior = CommonBehavior.from(tvLinearLayout);

        mBottomBehavior = CommonBehavior.from(mBottomMainNavigation);
        //mFloatButtonBehavior = CommonBehavior.from(mFloatingActionButton);
        MenuItem item = mBottomMainNavigation.getMenu().getItem(0);
        onNavigationItemSelected(item);//默认选中第一个
        mBottomMainNavigation.setOnNavigationItemSelectedListener(this);

        mBottomBehavior.setMinScrollY(20).setScrollYDistance(100).setDuration(1000).setInterpolator(new BounceInterpolator());
        //mFloatButtonBehavior.setMinScrollY(20).setScrollYDistance(100).setDuration(1000).setInterpolator(new BounceInterpolator());


        addSendDeliver = (FloatingActionButton)findViewById(R.id.action_b);
        addReceiveDeliver = (FloatingActionButton)findViewById(R.id.action_a);
        addSendDeliver.setOnClickListener(this);
        addReceiveDeliver.setOnClickListener(this);

        //从登录界面获取初始任务数据
        Intent intent = this.getIntent();
        String s = intent.getStringExtra("taskInformation");
        Gson gson1 = new Gson();
        //字符串转list
        list =gson1.fromJson(s, new TypeToken<List<TaskInfo>>() {}.getType());

    }

    public List<TaskInfo> getTask(){
        return list;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (item.getItemId()) {
            case menu_main_item_home:

                if(currentMainFragment == taskFragment){
                    /**
                     * 本来就在当前界面，又点击当前此item
                     * 刷新任务
                     */
                    //开启子线程获取最新任务信息
                    new Thread(()->{
                        String url = IP+"/AllTaskInfoServlet";
                        OkHttpClient okHttpClient = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            if(response.isSuccessful()){
                                String str = response.body().string();
                                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                Type type = new TypeToken<Map<String,String>>(){}.getType();

                                Map<String,String> map = gson.fromJson(str,type);
                                Message message = new Message();
                                if(map.get("tag").equals("success")){
                                    message.what = 1;
                                    list =gson.fromJson(map.get("info"), new TypeToken<List<TaskInfo>>() {}.getType());
                                }else{
                                    message.what = 2;
                                }
                                handler.sendMessage(message);


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }).start();

                }

                /**
                 * 浮动按钮和底部导航栏都可以滑动
                 */
                //mFloatingActionButton.setVisibility(View.VISIBLE);
                mBottomBehavior.setCanScroll(true);
                //mFloatButtonBehavior.setCanScroll(true);
                menuMultipleActions.setVisibility(View.VISIBLE);

                transaction.show(taskFragment).hide(deliverFragment)
                        .hide(mineFragment).hide(moreFragment);

                currentMainFragment = taskFragment;


                break;
            case R.id.menu_main_item_deliver:

                //mFloatingActionButton.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.GONE);

                mBottomBehavior.setCanScroll(false);
                transaction.show(deliverFragment).hide(mineFragment)
                        .hide(taskFragment).hide(moreFragment);

                currentMainFragment = deliverFragment;
                break;
            case R.id.menu_main_item_mine:
                //mFloatingActionButton.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.GONE);

                mBottomBehavior.setCanScroll(false);
                transaction.show(mineFragment).hide(deliverFragment)
                        .hide(taskFragment).hide(moreFragment);

                currentMainFragment = deliverFragment;
                break;
            case R.id.menu_main_item_more:
                //mFloatingActionButton.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.GONE);

                mBottomBehavior.setCanScroll(false);
                transaction.show(moreFragment).hide(mineFragment)
                        .hide(taskFragment).hide(deliverFragment);

                currentMainFragment = moreFragment;
                break;
        }
        transaction.commit();
        return true;
    }



    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            taskFragment = new TaskFragment();
            deliverFragment = new DeliverFragment();
            mineFragment = new MineFragment();
            moreFragment = new MoreFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_main, taskFragment, taskFragment.getClass().getSimpleName())
                    .add(R.id.frame_main, deliverFragment, deliverFragment.getClass().getSimpleName())
                    .add(R.id.frame_main, mineFragment, mineFragment.getClass().getSimpleName())
                    .add(R.id.frame_main, moreFragment, moreFragment.getClass().getSimpleName())
                    .show(taskFragment)
                    .hide(deliverFragment)
                    .hide(mineFragment)
                    .hide(moreFragment)
                    .commit();
        } else {
            taskFragment = (TaskFragment) getSupportFragmentManager()
                    .findFragmentByTag(TaskFragment.class.getSimpleName());
            deliverFragment = (DeliverFragment) getSupportFragmentManager()
                    .findFragmentByTag(DeliverFragment.class.getSimpleName());
            mineFragment = (MineFragment) getSupportFragmentManager()
                    .findFragmentByTag(MineFragment.class.getSimpleName());
            moreFragment = (MoreFragment) getSupportFragmentManager()
                    .findFragmentByTag(MoreFragment.class.getSimpleName());
            getSupportFragmentManager().beginTransaction()
                    .show(taskFragment)
                    .hide(deliverFragment)
                    .hide(mineFragment)
                    .hide(moreFragment)
                    .commit();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.action_a:
                //代收
                startActivity(new Intent(HomeActivity.this,AddReceiveDeliverActivity.class));
                break;
            case R.id.action_b:
                //代发
                startActivity(new Intent(HomeActivity.this,AddSendDeliverActivity.class));
                break;
        }

    }

    // System.currentTimeMillis() 当前系统的时间
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            finish();
            System.exit(0);
        } else {
            Toast.makeText(HomeActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }
}
