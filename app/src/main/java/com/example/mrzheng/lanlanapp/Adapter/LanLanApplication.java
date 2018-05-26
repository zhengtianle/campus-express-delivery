package com.example.mrzheng.lanlanapp.Adapter;

import android.app.Application;
import android.content.Context;

/**
 * Created by mrzheng on 18-5-17.
 */

public class LanLanApplication extends Application{

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * 获取全局的context
     */
    public static Context getContext() {
        return mContext;
    }

}
