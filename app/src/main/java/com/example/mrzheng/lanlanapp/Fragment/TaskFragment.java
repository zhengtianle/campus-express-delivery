package com.example.mrzheng.lanlanapp.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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

import com.example.mrzheng.lanlanapp.Adapter.HomeItemAdapter;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Widget.HomeItemDecoration;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

/**
 * Created by mrzheng on 18-5-2.
 */

public class TaskFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;

    private LinearLayout tvLinearLayout;
    private CommonBehavior mToolBarBehavior;
    private TextView textViewOne;
    private TextView textViewSecond;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(new HomeItemAdapter(mActivity));
        mRecyclerView.addItemDecoration(new HomeItemDecoration(mActivity));

        mToolBarBehavior = CommonBehavior.from(tvLinearLayout);
        mToolBarBehavior.setMinScrollY(20).setScrollYDistance(100).setDuration(1000).setInterpolator(new BounceInterpolator());
        mToolBarBehavior.setCanScroll(true);

        /*textViewOne = (TextView) view.findViewById(R.id.text_one);
        textViewSecond = (TextView) view.findViewById(R.id.text_two);

        textViewOne.setOnClickListener(this);
        textViewSecond.setOnClickListener(this);

        loadFragment();*/

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
     * 切换代收任务和代发任务
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.text_one:
                fragmentTransaction.hide(receiveFragment).show(sendFragment);
                break;
            case R.id.text_two:
                fragmentTransaction.hide(sendFragment).show(receiveFragment);
                break;
        }

        fragmentTransaction.commit();

    }



}
