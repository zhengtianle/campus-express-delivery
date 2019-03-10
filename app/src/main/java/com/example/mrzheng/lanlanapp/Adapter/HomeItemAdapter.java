package com.example.mrzheng.lanlanapp.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Model.DataModel;
import com.example.mrzheng.lanlanapp.Model.TaskEntity;
import com.example.mrzheng.lanlanapp.Model.TaskInfo;
import com.example.mrzheng.lanlanapp.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 主页的任务数据适配器
 * Created by mrzheng on 18-5-2.
 */

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.DemoViewHolder>{

    /*private List<TaskEntity> mData;*/
    private List<TaskInfo> mData;
    private Context mContext;

    /*public HomeItemAdapter(Context context,List<TaskEntity> list) {
        mContext = context;
        mData = list;
    }*/
    public HomeItemAdapter(Context context,List<TaskInfo> list) {
        mContext = context;
        mData = list;
    }

    /**
     * 刷新数据
     * @param list
     */
    /*public void update(List<TaskEntity> list){
        mData = list;
        notifyDataSetChanged();
    }*/
    public void update(List<TaskInfo> list){
        mData = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    /**
     * 创建viewholder
     * 引入xml传送给viewholder
     */
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rv_item,parent,false);
        DemoViewHolder myHolder = new DemoViewHolder(view);
        return myHolder;
    }

    /**
     * 操作item的地方
     */
    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        /*TaskEntity s = mData.get(position);
        holder.company.setText(s.getCompany());
        holder.sex.setText(s.getReleaseUserSex());
        holder.deliver.setText(s.getDeliver());
        holder.local.setText(s.getLocal());
        holder.information.setText(Integer.valueOf(s.getWeight()).toString()+"  "+Integer.valueOf(s.getMoney()).toString());
        holder.money.setText(Integer.valueOf(s.getMoney()).toString());
        holder.meetingTime.setText(s.getMeetingTime());
        holder.currentTime.setText(df.format(new Date()));*/

        TaskInfo s = mData.get(position);
        holder.company.setText(s.express_company);
        holder.sex.setText(s.sex);
        holder.deliver.setText(s.express_name);
        holder.local.setText(s.meeting_location);
        holder.information.setText(s.express_weight+"   "+s.express_value);
        holder.money.setText(s.money);
        holder.meetingTime.setText(s.meeting_time);
        holder.currentTime.setText(df.format(new Date()));
        holder.sType = s.type;
        if(s.type.equals("代收快递")){
            holder.type.setBackgroundResource(R.drawable.add_receive_big);
        }else{
            holder.type.setBackgroundResource(R.drawable.add_send_big);

        }

        holder.taskId = Integer.parseInt(s.task_id);//唯一标识这个任务
        //对控件进行监听
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });

        }

    }

    /*@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        *//*((DemoViewHolder) holder).mTxtItem.setText(mDemoData.get(position));*//*
    }*/

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {

        LinearLayout task;
        TextView company;
        TextView sex;
        TextView deliver;
        TextView local;
        TextView information;//格式为 重量   估值
        TextView money;
        TextView meetingTime;
        TextView currentTime;

        TextView type;

        public int taskId;
        public String sType;


        public DemoViewHolder(View itemView) {
            super(itemView);
            task = (LinearLayout) itemView.findViewById(R.id.layout_task);
            company  =(TextView) itemView.findViewById(R.id.tv_company);
            sex = (TextView) itemView.findViewById(R.id.tv_sex);
            deliver = (TextView) itemView.findViewById(R.id.tv_deliver);
            local = (TextView) itemView.findViewById(R.id.tv_local);
            information = (TextView) itemView.findViewById(R.id.tv_information);
            money = (TextView) itemView.findViewById(R.id.tv_money);
            meetingTime = (TextView) itemView.findViewById(R.id.tv_meeting_time);
            currentTime = (TextView) itemView.findViewById(R.id.tv_current_time);
            type = (TextView)itemView.findViewById(R.id.tv_type);

        }
    }
}
