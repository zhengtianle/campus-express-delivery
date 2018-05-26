package com.example.mrzheng.lanlanapp.Widget.radapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mrzheng.lanlanapp.Utils.binding.ViewBinder;


/**
 * Created by mrzheng
 */
public abstract class RViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context context;
    protected View itemView;
    protected RAdapter<T> adapter;
    protected T data;
    protected int position;

    public RViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        context = itemView.getContext();
        ViewBinder.bind(this, itemView);
    }

    public void setAdapter(RAdapter<T> adapter) {
        this.adapter = adapter;
    }

    public void setData(T t) {
        this.data = t;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public abstract void refresh();
}
