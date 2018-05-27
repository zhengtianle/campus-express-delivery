package com.example.mrzheng.lanlanapp.Fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Activity.DeliverHistoryActivity;
import com.example.mrzheng.lanlanapp.Activity.DeliverSearchActivity;
import com.example.mrzheng.lanlanapp.Activity.HomeActivity;
import com.example.mrzheng.lanlanapp.Database.History;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.PermissionReq;
import com.example.mrzheng.lanlanapp.Utils.SnackbarUtils;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RAdapter;
import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrzheng on 18-5-2.
 */

public class DeliverFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    @Bind(R.id.fragment_deliver)
    private Fragment fragmentDelver;
    @Bind(R.id.tv_search)
    private TextView tvSearch;
    @Bind(R.id.tv_post)
    private TextView tvPost;
    @Bind(R.id.tv_sweep)
    private TextView tvSweep;
    @Bind(R.id.rv_un_check)
    private RecyclerView rvUnCheck;
    @Bind(R.id.tv_empty)
    private TextView tvEmpty;

    private List<History> unCheckList = new ArrayList<>();
    private RAdapter<History> adapter;

   /* private android.support.v4.app.FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DeliverHistoryFragment historyFragment;*/

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public DeliverFragment() {
    }

    public static DeliverFragment newInstance(String param1, String param2) {
        DeliverFragment fragment = new DeliverFragment();
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
        // Inflate the layout fo this fragment
        View v = inflater.inflate(R.layout.fragment_deliver, container, false);

        tvSearch = (TextView)v.findViewById(R.id.tv_search);
        tvPost = (TextView)v.findViewById(R.id.tv_post);
        tvSweep = (TextView)v.findViewById(R.id.tv_sweep);
        rvUnCheck = (RecyclerView)v.findViewById(R.id.rv_un_check);
        tvEmpty = (TextView)v.findViewById(R.id.tv_empty);

        tvEmpty.setVisibility(View.VISIBLE);

        tvSearch.setOnClickListener(this);
        tvPost.setOnClickListener(this);
        tvSweep.setOnClickListener(this);

        rvUnCheck.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUnCheck.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvUnCheck.setAdapter(adapter);

        //loadFragment();


        return v;
    }

    /*public void loadFragment(){

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        historyFragment = new DeliverHistoryFragment();
        fragmentTransaction.add(R.id.fragment_deliver,historyFragment).commit();

    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(getContext(), DeliverSearchActivity.class));
                break;
            case R.id.tv_post:
                /*fragmentTransaction.show(historyFragment);
                HomeActivity.currentMainFragment = historyFragment;*/
                startActivity(new Intent(getContext(), DeliverHistoryActivity.class));
                break;
            case R.id.tv_sweep:
                startCaptureActivity();
                break;
            default:
                break;
        }

    }

    private void startCaptureActivity() {
        PermissionReq.with(this)
                .permissions(Manifest.permission.CAMERA)
                .result(new PermissionReq.Result() {
                    @Override
                    public void onGranted() {
                        CaptureActivity.start(getActivity(), false, 0);
                    }

                    @Override
                    public void onDenied() {
                        SnackbarUtils.show(getActivity(), "没有相机权限，无法打开扫一扫！");
                    }
                })
                .request();
    }


}
