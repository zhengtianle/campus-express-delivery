package com.example.mrzheng.lanlanapp.Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.mrzheng.lanlanapp.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

import static com.example.mrzheng.lanlanapp.R.id.menu_main_item_home;

/**
 * Created by mrzheng on 18-5-2.
 */

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
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

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (item.getItemId()) {
            case menu_main_item_home:
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
