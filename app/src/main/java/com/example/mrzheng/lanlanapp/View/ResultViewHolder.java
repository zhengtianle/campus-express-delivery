package com.example.mrzheng.lanlanapp.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Model.SearchResult;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.Utils;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RLayout;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_search_result)
public class ResultViewHolder extends RViewHolder<SearchResult.ResultItem> {
    @Bind(R.id.line)
    private View line;
    @Bind(R.id.iv_logistics)
    private ImageView ivLogistics;
    @Bind(R.id.tv_time)
    private TextView tvTime;
    @Bind(R.id.tv_detail)
    private TextView tvDetail;

    public ResultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void refresh() {
        tvTime.setText(data.getTime());
        tvDetail.setText(data.getContext());
        boolean first = (position == 0);
        line.setPadding(0, Utils.dp2px(context, first ? 12 : 0), 0, 0);
        ivLogistics.setSelected(first);
        tvTime.setSelected(first);
        tvDetail.setSelected(first);
    }
}
