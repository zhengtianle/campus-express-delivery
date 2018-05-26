package com.example.mrzheng.lanlanapp.Widget.radapter;

/**
 * Created by wcy on 2017/11/26.
 */
public interface RAdapterDelegate<T> {
    Class<? extends RViewHolder<T>> getViewHolderClass(int position);
}
