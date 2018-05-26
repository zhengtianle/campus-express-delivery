package com.example.mrzheng.lanlanapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Model.CompanyEntity;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.View.CompanyIndexViewHolder;
import com.example.mrzheng.lanlanapp.View.CompanyNameViewHolder;
import com.example.mrzheng.lanlanapp.Widget.IndexBar;
import com.example.mrzheng.lanlanapp.Widget.radapter.RAdapter;
import com.example.mrzheng.lanlanapp.Widget.radapter.RAdapterDelegate;
import com.example.mrzheng.lanlanapp.Widget.radapter.RViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class CompanyActivity extends BaseActivity implements IndexBar.OnIndexChangedListener {
    @Bind(R.id.rv_company)
    private RecyclerView rvCompany;
    @Bind(R.id.ib_indicator)
    private IndexBar ibIndicator;
    @Bind(R.id.tv_indicator)
    private TextView tvIndicator;

    private List<CompanyEntity> companyList = new ArrayList<>();
    private RAdapter<CompanyEntity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        readCompany();
        adapter = new RAdapter<>(companyList, delegate);
        rvCompany.setLayoutManager(new LinearLayoutManager(this));
        rvCompany.setAdapter(adapter);
        rvCompany.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ibIndicator.setOnIndexChangedListener(this);
    }


    private RAdapterDelegate<CompanyEntity> delegate = new RAdapterDelegate<CompanyEntity>() {
        @Override
        public Class<? extends RViewHolder<CompanyEntity>> getViewHolderClass(int position) {
            if (TextUtils.isEmpty(companyList.get(position).getCode())) {
                return CompanyIndexViewHolder.class;
            } else {
                return CompanyNameViewHolder.class;
            }
        }
    };

    private void readCompany() {
        try {
            InputStream is = getAssets().open("company.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(json).getAsJsonArray();
            for (JsonElement obj : jArray) {
                CompanyEntity company = gson.fromJson(obj, CompanyEntity.class);
                companyList.add(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onIndexChanged(String index, boolean isDown) {
        int position = -1;
        for (CompanyEntity company : companyList) {
            if (TextUtils.equals(company.getName(), index)) {
                position = companyList.indexOf(company);
                break;
            }
        }
        if (position != -1) {
            rvCompany.scrollToPosition(position);
        }
        tvIndicator.setText(index);
        tvIndicator.setVisibility(isDown ? View.VISIBLE : View.GONE);
    }
}
