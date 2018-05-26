package com.example.mrzheng.lanlanapp.View;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Activity.CompanyActivity;
import com.example.mrzheng.lanlanapp.Activity.ResultActivity;
import com.example.mrzheng.lanlanapp.Extra.RequestCode;
import com.example.mrzheng.lanlanapp.Model.CompanyEntity;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RLayout;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_suggestion)
public class SuggestionViewHolder extends RViewHolder<CompanyEntity> implements View.OnClickListener {
    @Bind(R.id.tv_suggestion)
    private TextView tvSuggestion;

    public SuggestionViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void refresh() {
        tvSuggestion.setText(Html.fromHtml(data.getName()));
    }

    @Override
    public void onClick(View v) {
        if (position == adapter.getDataList().size() - 1) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(new Intent(activity, CompanyActivity.class), RequestCode.REQUEST_COMPANY);
            return;
        }
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setPost_id((String) adapter.getTag());
        searchInfo.setCode(data.getCode());
        searchInfo.setName(data.getName());
        searchInfo.setLogo(data.getLogo());
        ResultActivity.start(context, searchInfo);
    }
}
