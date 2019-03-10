package com.example.mrzheng.lanlanapp.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟任务数据
 * Created by mrzheng on 18-5-2.
 */

public class DataModel {

    public static List<TaskEntity> getDemoData() {
        List<TaskEntity> demoData = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        TaskEntity demoTask =new TaskEntity();

        demoTask.setCompany("顺丰快递");
        /*demoTask.setSex("女");*/
        demoTask.setDeliver("MacBook Pro 2017款");
        demoTask.setLocal("六号楼三区0604");
        demoTask.setWeight(2);
        demoTask.setPrice(12000);
        demoTask.setMoney(10);
        demoTask.setMeetingTime("2018-06-01 18:00");

        demoTask.setReleaseUserNickname("懒懒用户");
        demoTask.setReleaseUserSex("男");
        demoTask.setReleaseUserTel("17863113969");
        demoTask.setReleaseUserSchool("中国某学校");
        demoTask.setReleaseUserGrade(5.0);
        demoTask.setExpressType("电子产品");
        demoTask.setMeetingLocation("山威六号楼");
        demoTask.setSenderName("郑天乐");
        demoTask.setSenderTel("17863113969");
        demoTask.setSenderLocation("山东省威海市环翠区文化西路180号山东大学威海");
        demoTask.setReceiverName("作者");
        demoTask.setReceiverTel("17860973727");
        demoTask.setReceiverLocation("浙江省台州市");
        demoTask.setNote("无");
        demoTask.setType("代发快递");

        //initDemoTask();
        for (int i = 0; i < 20; i++) {

            demoTask.setMeetingTime(df.format(new Date()));
            demoTask.setWeight(i+1);
            demoTask.setTaskId(i+1);
            demoData.add(demoTask);
        }
        return demoData;
    }

    /*public static void initDemoTask(){
        demoTask.setCompany("顺丰快递");
        demoTask.setSex("女");
        demoTask.setDeliver("MacBook Pro 2017款");
        demoTask.setLocal("六号楼三区0604");
        demoTask.setWeight(2);
        demoTask.setPrice(12000);
        demoTask.setMoney(10);
        demoTask.setMeetingTime("2018-06-01 18:00");

        demoTask.setReleaseUserNickname("懒懒用户");
        demoTask.setReleaseUserSex("男");
        demoTask.setReleaseUserTel("17863113969");
        demoTask.setReleaseUserSchool("中国某学校");
        demoTask.setReleaseUserGrade(5.0);
        demoTask.setExpressType("电子产品");
        demoTask.setMeetingLocation("山威六号楼");
        demoTask.setSenderName("郑天乐");
        demoTask.setSenderTel("17863113969");
        demoTask.setSenderLocation("山东省威海市环翠区文化西路180号山东大学威海");
        demoTask.setReceiverName("作者");
        demoTask.setReceiverTel("17860973727");
        demoTask.setReceiverLocation("浙江省台州市");
        demoTask.setNote("无");
        demoTask.setType("代发快递");


    }*/
}
