package com.example.mrzheng.lanlanapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Activity.HomeActivity;
import com.example.mrzheng.lanlanapp.Activity.LoginActivity;
import com.example.mrzheng.lanlanapp.Activity.MainActivity;
import com.example.mrzheng.lanlanapp.Activity.TaskInformationActivity;
import com.example.mrzheng.lanlanapp.Adapter.HomeItemAdapter;
import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Model.CurrentUserInfo;
import com.example.mrzheng.lanlanapp.Model.DataModel;
import com.example.mrzheng.lanlanapp.Model.TaskEntity;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Widget.HomeItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-5-2.
 */

public class TaskFragment extends android.support.v4.app.Fragment
        implements View.OnClickListener,HttpService{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    /*private List<TaskEntity> list;*/
    private List<TaskInfo> mList;
    private RecyclerView mRecyclerView;
    HomeItemAdapter adapter;

    private LinearLayout tvLinearLayout;
    private CommonBehavior mToolBarBehavior;
    private TextView textViewOne;
    private TextView textViewTwo;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SendFragment sendFragment;
    private ReceiveFragment receiveFragment;
    private static Fragment currentTaskFragment;


    public TaskFragment() {
    }

    private Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = ((RecyclerView) view.findViewById(R.id.rv_task));
        tvLinearLayout = (LinearLayout) view.findViewById(R.id.linear_task);

       /*mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(new HomeItemAdapter(mActivity));
        mRecyclerView.addItemDecoration(new HomeItemDecoration(mActivity));*/

        mToolBarBehavior = CommonBehavior.from(tvLinearLayout);
        mToolBarBehavior.setMinScrollY(20).setScrollYDistance(100).setDuration(1000).setInterpolator(new BounceInterpolator());
        mToolBarBehavior.setCanScroll(true);

        textViewOne = (TextView) view.findViewById(R.id.text_one);
        textViewTwo = (TextView) view.findViewById(R.id.text_two);

        textViewOne.setOnClickListener(this);
        textViewTwo.setOnClickListener(this);

        //list = DataModel.getDemoData();
        /*Intent intent = getActivity().getIntent();
        String s = intent.getStringExtra("taskInformation");
        Gson gson1 = new Gson();
        //字符串转list
        mList =gson1.fromJson(s, new TypeToken<List<TaskInfo>>() {}.getType());*/
        /**
         * //强转成宿主activity
         */
        mList = ((HomeActivity) mActivity).getTask();
        /*adapter = new HomeItemAdapter(getContext(),list);*/
        adapter = new HomeItemAdapter(getContext(),mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置方向
        mRecyclerView.addItemDecoration(new HomeItemDecoration(mActivity));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new HomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(),position+1+"click",Toast.LENGTH_SHORT).show();
                if (null != mRecyclerView.getChildViewHolder(view)){
                    /*View v = mRecyclerView.getChildAt(position);
                    HomeItemAdapter.DemoViewHolder viewHolder = (HomeItemAdapter.DemoViewHolder)mRecyclerView.getChildViewHolder(v);*/
                    Intent intent = new Intent(getContext(), TaskInformationActivity.class);
                    /*TaskEntity currentTask = new TaskEntity();
                    for(TaskEntity l : list){
                        if(viewHolder.taskId == l.getTaskId()){
                            currentTask = l;
                            break;
                        }
                    }
                    intent.putExtra("currentTask",currentTask);
                    startActivity(intent);*/

                    TaskInfo currentTask = new TaskInfo();
                    /*for(TaskInfo l : mList){
                        if(viewHolder.taskId == Integer.parseInt(l.task_id)&&
                                viewHolder.sType.equals(l.type)){
                            currentTask = l;
                            break;
                        }
                    }*/
                    currentTask = mList.get(position);
                    Gson gson = new Gson();
                    String s = gson.toJson(currentTask);
                    intent.putExtra("currentTask",s);
                    intent.putExtra("visiable","true");
                    startActivity(intent);

                    //do something
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),position+1+" long click",Toast.LENGTH_SHORT).show();
            }
        });

        /*loadFragment();*/

    }

    /**
     * 初始化fragment_task
     */
    /*public void loadFragment(){

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        sendFragment = new SendFragment();
        receiveFragment = new ReceiveFragment();

        fragmentTransaction.add(R.id.fragment_task,sendFragment)
                .add(R.id.fragment_task,receiveFragment)
                .show(sendFragment)
                .hide(receiveFragment);
        fragmentTransaction.commit();

    }*/

    /**
     * 刷新当前fragment界面任务数据
     */
    public void refresh(){
        /**
         * //强转成宿主activity
         */
        mList = ((HomeActivity) mActivity).getTask();
        adapter.update(mList);
    }

    /**
     * 切换代收任务和代发任务(暂时不细分了)
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.text_one:
                /*fragmentTransaction.hide(receiveFragment).show(sendFragment);*/
                textViewTwo.setTextColor(Color.BLACK);
                textViewOne.setTextColor(Color.RED);
                break;
            case R.id.text_two:
                /*fragmentTransaction.hide(sendFragment).show(receiveFragment);*/
                textViewOne.setTextColor(Color.BLACK);
                textViewTwo.setTextColor(Color.RED);
                break;
        }

        /*fragmentTransaction.commit();*/

    }


    /*public void initList() {
        list = new ArrayList<TaskEntity>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式

        //initDemoTask();
        for (int i = 0; i < 20; i++) {
            TaskEntity demoTask =new TaskEntity();

            demoTask.setCompany("顺丰快递");
            *//*demoTask.setSex("女");*//*
            demoTask.setDeliver("MacBook Pro 2017款");
            demoTask.setLocal("六号楼三区0604");
            demoTask.setWeight(2);
            demoTask.setPrice(12000);
            demoTask.setMoney(10);
            demoTask.setMeetingTime("2018-06-01 18:00");

            demoTask.setReleaseUserNickname("懒懒用户");
            demoTask.setReleaseUserSex("男");
            demoTask.setReleaseUserTel("17863113969");
            demoTask.setReleaseUserSchool("山东大学威海校区");
            demoTask.setReleaseUserGrade(5.0);
            demoTask.setExpressType("电子产品");
            demoTask.setMeetingLocation("山威六号楼");
            demoTask.setSenderName("郑天乐");
            demoTask.setSenderTel("17863113969");
            demoTask.setSenderLocation("山东省威海市环翠区文化西路180号山东大学威海");
            demoTask.setReceiverName("郑浩");
            demoTask.setReceiverTel("17860973727");
            demoTask.setReceiverLocation("浙江省台州市");
            demoTask.setNote("无");
            demoTask.setType("代发快递");


            demoTask.setMeetingTime(df.format(new Date()));
            demoTask.setWeight(i+1);
            demoTask.setTaskId(i+1);
            list.add(demoTask);
        }

    }*/



}
