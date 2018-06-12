package com.example.mrzheng.lanlanapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.Activity.LoginActivity;
import com.example.mrzheng.lanlanapp.Activity.ModifyPersonalInfoActivity;
import com.example.mrzheng.lanlanapp.Activity.MyAcceptTaskActivity;
import com.example.mrzheng.lanlanapp.Activity.MyReleaseTaskActivity;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mrzheng on 18-5-2.
 */

public class MineFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView mReleaseTask;
    private TextView mAcceptTask;
    private RelativeLayout userFeedback;
    private Button logOff;
    private TextView changeUserInformation;

    //上半部分的个人信息
    private TextView nickname;
    private CircleImageView avatar;
    private TextView releaseTaskNumber;
    private TextView receiveTaskNumber;
    private TextView grade;

    public MineFragment() {
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View v = inflater.inflate(R.layout.fragment_mine, container, false);

        mReleaseTask = (TextView)v.findViewById(R.id.tv_task_release);
        mAcceptTask = (TextView)v.findViewById(R.id.tv_task_accept);
        userFeedback = (RelativeLayout)v.findViewById(R.id.user_feedback);
        logOff = (Button) v.findViewById(R.id.log_off);
        changeUserInformation = (TextView)v.findViewById(R.id.change_user_information);

        //部分个人信息
        avatar = (CircleImageView)v.findViewById(R.id.mine_avatar);
        nickname = (TextView)v.findViewById(R.id.mine_nickname);
        releaseTaskNumber = (TextView)v.findViewById(R.id.mine_release);
        receiveTaskNumber = (TextView)v.findViewById(R.id.mine_receive);
        grade = (TextView)v.findViewById(R.id.mine_grade);

        setInformation();

        setClickListener();

        return v;
    }

    /**
     * 初始化界面中的信息
     */
    public void setInformation(){

        nickname.setText(UserInfo.nickname);
        releaseTaskNumber.setText(UserInfo.release_tasks);
        receiveTaskNumber.setText(UserInfo.receive_tasks);
        grade.setText(UserInfo.grade);

        //avatar头像的设置

    }

    public void setClickListener(){

        changeUserInformation.setOnClickListener(this);
        mReleaseTask.setOnClickListener(this);
        mAcceptTask.setOnClickListener(this);
        userFeedback.setOnClickListener(this);
        logOff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.change_user_information:
                startActivity(new Intent(getContext(),ModifyPersonalInfoActivity.class));
                break;
            case R.id.tv_task_release:
                Intent intent = new Intent(getContext(),MyReleaseTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("return","HomeActivity.class");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_task_accept:
                startActivity(new Intent(getContext(), MyAcceptTaskActivity.class));
                break;
            case R.id.user_feedback:
                Toast.makeText(getContext(),"请联系本APP作者",Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_off:
                //由于全部的activity都是singleTask模式，只要到登录界面，就会把在其上的所有活动都出栈(销毁)
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;

        }
    }


}
