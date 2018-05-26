package com.example.mrzheng.lanlanapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Model.DataModel;
import com.example.mrzheng.lanlanapp.R;

import java.util.List;

/**
 * 主页的任务数据适配器
 * Created by mrzheng on 18-5-2.
 */

public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<String> mDemoData;
    private Context mContext;

    public HomeItemAdapter(Context context) {
        mContext = context;
        mDemoData = DataModel.getDemoData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DemoViewHolder) holder).mTxtItem.setText(mDemoData.get(position));
    }

    @Override
    public int getItemCount() {
        return mDemoData == null ? 0 : mDemoData.size();
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtItem;

        public DemoViewHolder(View itemView) {
            super(itemView);
            mTxtItem = (TextView) itemView.findViewById(R.id.text_item);
        }
    }
}
