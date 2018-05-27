package com.example.mrzheng.lanlanapp.View;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrzheng.lanlanapp.Activity.DeliverHistoryActivity;
import com.example.mrzheng.lanlanapp.Activity.ResultActivity;
import com.example.mrzheng.lanlanapp.Database.History;
import com.example.mrzheng.lanlanapp.Http.HttpClient;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.DataManager;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RLayout;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_history)
public class HistoryViewHolder extends RViewHolder<History> implements View.OnClickListener, View.OnLongClickListener {
    @Bind(R.id.iv_logo)
    private ImageView ivLogo;
    @Bind(R.id.tv_name)
    private TextView tvName;
    @Bind(R.id.tv_post_id)
    private TextView tvPostId;
    @Bind(R.id.tv_is_check)
    private TextView tvIsCheck;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        if (context instanceof DeliverHistoryActivity) {
            itemView.setOnLongClickListener(this);
        }
    }

    @Override
    public void refresh() {
        Glide.with(context)
                .load(HttpClient.urlForLogo(data.getCompany_icon()))
                .dontAnimate()
                .placeholder(R.drawable.ic_default_logo)
                .into(ivLogo);
        String isCheck = data.getIs_check();
        int checkTextColor;
        if (TextUtils.equals(isCheck, "0")) {
            isCheck = "在途中";
            checkTextColor = context.getResources().getColor(R.color.orange_700);
        } else {
            isCheck = "已签收";
            checkTextColor = context.getResources().getColor(R.color.grey);
        }
        tvIsCheck.setText(isCheck);
        tvIsCheck.setTextColor(checkTextColor);
        String remark = data.getRemark();
        if (TextUtils.isEmpty(remark)) {
            tvName.setText(data.getCompany_name());
            tvPostId.setText(data.getPost_id());
        } else {
            tvName.setText(remark);
            tvPostId.setText(data.getCompany_name().concat(" ").concat(data.getPost_id()));
        }
    }

    @Override
    public void onClick(View v) {
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setPost_id(data.getPost_id());
        searchInfo.setCode(data.getCompany_param());
        searchInfo.setName(data.getCompany_name());
        searchInfo.setLogo(data.getCompany_icon());
        ResultActivity.start(context, searchInfo);
    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("您要删除该条记录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.getInstance().deleteById(data.getPost_id());
                        adapter.removeItem(position);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        return true;
    }
}
