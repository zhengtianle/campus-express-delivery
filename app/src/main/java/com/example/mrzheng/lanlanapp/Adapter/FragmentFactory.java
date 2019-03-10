package com.example.mrzheng.lanlanapp.Adapter;

import android.support.v4.app.Fragment;

import com.example.mrzheng.lanlanapp.Fragment.ReceiveFragment;
import com.example.mrzheng.lanlanapp.Fragment.SendFragment;


/**
 * Created by ZhengTianle
 */
public class FragmentFactory {
    public static Fragment createForNoExpand(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SendFragment();
                break;
            case 1:
                fragment = new ReceiveFragment();
                break;
        }
        return fragment;
    }

}
