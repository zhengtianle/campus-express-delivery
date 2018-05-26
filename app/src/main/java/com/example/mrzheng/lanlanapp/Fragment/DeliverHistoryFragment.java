package com.example.mrzheng.lanlanapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrzheng.lanlanapp.R;

/**
 * Created by mrzheng on 18-5-24.
 */

public class DeliverHistoryFragment extends android.support.v4.app.Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout fo this fragment
        return inflater.inflate(R.layout.activity_deliver_history, container, false);
    }
}
