package com.example.mrzheng.lanlanapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mrzheng.lanlanapp.Database.History;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.DataManager;
import com.example.mrzheng.lanlanapp.Utils.binding.Bind;
import com.example.mrzheng.lanlanapp.View.HistoryViewHolder;
import com.example.mrzheng.lanlanapp.Widget.radapter.RAdapter;
import com.example.mrzheng.lanlanapp.Widget.radapter.RSingleDelegate;

import java.util.ArrayList;
import java.util.List;



public class DeliverHistoryActivity extends BaseActivity {
    @Bind(R.id.rv_history_list)
    private RecyclerView rvHistoryList;
    @Bind(R.id.tv_empty)
    private TextView tvEmpty;

    private List<History> historyList = new ArrayList<>();
    private RAdapter<History> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_history);

        adapter = new RAdapter<>(historyList, new RSingleDelegate<>(HistoryViewHolder.class));
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this));
        rvHistoryList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvHistoryList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<History> historyList = DataManager.getInstance().getHistoryList();
        this.historyList.clear();
        this.historyList.addAll(historyList);
        adapter.notifyDataSetChanged();
        tvEmpty.setVisibility(this.historyList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
