package com.example.mrzheng.lanlanapp.View;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Activity.AddReceiveDeliverNextActivity;
import com.example.mrzheng.lanlanapp.Extra.Extras;
import com.example.mrzheng.lanlanapp.Http.HttpClient;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.example.mrzheng.lanlanapp.R;
import com.bumptech.glide.Glide;
import com.example.mrzheng.lanlanapp.Model.CompanyEntity;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RLayout;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;



/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_company)
public class CompanyNameViewHolder extends RViewHolder<CompanyEntity> implements View.OnClickListener {
    @Bind(R.id.iv_logo)
    private ImageView ivLogo;
    @Bind(R.id.tv_name)
    private TextView tvName;


    public CompanyNameViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void refresh() {
        Glide.with(context)
                .load(HttpClient.urlForLogo(data.getLogo()))
                .dontAnimate()
                .placeholder(R.drawable.ic_default_logo)
                .into(ivLogo);
        tvName.setText(data.getName());
    }

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) context;
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setName(data.getName());
        searchInfo.setLogo(data.getLogo());
        searchInfo.setCode(data.getCode());
        Intent intent = new Intent();
        intent.putExtra(Extras.SEARCH_INFO, searchInfo);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();


    }
}
