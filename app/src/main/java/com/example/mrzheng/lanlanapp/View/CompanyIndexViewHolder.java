package com.example.mrzheng.lanlanapp.View;

import android.view.View;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Model.CompanyEntity;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.Widget.radapter.RLayout;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;


/**
 * Created by wcy on 2018/1/20.
 */
@RLayout(R.layout.view_holder_company_index)
public class CompanyIndexViewHolder extends RViewHolder<CompanyEntity> {
    @Bind(R.id.tv_index)
    private TextView tvIndex;

    public CompanyIndexViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void refresh() {
        tvIndex.setText(data.getName());
    }
}
