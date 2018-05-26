package com.example.mrzheng.lanlanapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟任务数据
 * Created by mrzheng on 18-5-2.
 */

public class DataModel {
    public static List<String> getDemoData() {
        List<String> demoData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            demoData.add("Android -- " + i);
        }
        return demoData;
    }
}
