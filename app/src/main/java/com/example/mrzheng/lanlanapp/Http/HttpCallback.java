package com.example.mrzheng.lanlanapp.Http;

import com.android.volley.VolleyError;

/**
 * Created by hzwangchenyan on 2017/3/22.
 */
public interface HttpCallback<T> {
    void onResponse(T t);

    void onError(VolleyError volleyError);
}
