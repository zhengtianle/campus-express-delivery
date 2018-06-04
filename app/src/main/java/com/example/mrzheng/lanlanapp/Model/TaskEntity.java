package com.example.mrzheng.lanlanapp.Model;

import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mrzheng on 18-6-2.
 */

public class TaskEntity implements Parcelable{
    private String company;
    //private String sex;
    private String deliver;
    private String local;
    private int weight;
    private int price;
    private int money;
    private String meetingTime;

    private String type;
    private String releaseUserNickname;
    private String releaseUserTel;
    private String releaseUserSex;
    private String releaseUserSchool;
    private double releaseUserGrade;
    private String expressType;
    private String meetingLocation;
    private String senderName;
    private String senderTel;
    private String senderLocation;
    private String receiverName;
    private String receiverTel;
    private String receiverLocation;
    private String note;

    private int taskId;



    public void setTaskId(int taskId){
        this.taskId = taskId;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setNote(String note){
        this.note = note;
    }
    public void setReceiverLocation(String receiverLocation){
        this.receiverLocation = receiverLocation;
    }
    public void setReceiverTel(String receiverTel){
        this.receiverTel = receiverTel;
    }
    public void setReceiverName(String receiverName){
        this.receiverName = receiverName;
    }
    public void setSenderLocation(String senderLocation){
        this.senderLocation = senderLocation;
    }
    public void setSenderTel(String senderTel){
        this.senderTel = senderTel;
    }
    public void setSenderName(String senderName){
        this.senderName = senderName;
    }
    public void setMeetingLocation(String meetingLocation){
        this.meetingLocation = meetingLocation;
    }
    public void setExpressType(String expressType){
        this.expressType = expressType;
    }
    public void setReleaseUserGrade(double releaseUserGrade){
        this.releaseUserGrade = releaseUserGrade;
    }
    public void setReleaseUserSchool(String releaseUserSchool){
        this.releaseUserSchool = releaseUserSchool;
    }
    public void setReleaseUserSex(String releaseUserSex){
        this.releaseUserSex = releaseUserSex;
    }
    public void setReleaseUserTel(String releaseUserTel){
        this.releaseUserTel = releaseUserTel;
    }
    public void setReleaseUserNickname(String releaseUserNickname){
        this.releaseUserNickname = releaseUserNickname;
    }
    public void setCompany(String company){
        this.company=company;
    }
    /*public void setSex(String sex){
        this.sex=sex;
    }*/
    public void setDeliver(String deliver){
        this.deliver=deliver;
    }
    public void setLocal(String local){
        this.local=local;
    }
    public void setWeight(int weight){
        this.weight=weight;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public void setMeetingTime(String meetingTime){
        this.meetingTime = meetingTime;
    }


    public int getTaskId(){
        return taskId;
    }
    public String getType(){
        return type;
    }
    public String getNote(){
       return note;
    }
    public String getReceiverLocation(){
        return receiverLocation;
    }
    public String getReceiverTel(){
        return receiverTel;
    }
    public String getReceiverName(){
        return receiverName;
    }
    public String getSenderLocation(){
        return senderLocation;
    }
    public String getSenderTel(){
        return senderTel;
    }
    public String getSenderName(){
        return senderName;
    }
    public String getMeetingLocation(){
        return meetingLocation;
    }
    public String getExpressType(){
        return expressType;
    }
    public double getReleaseUserGrade(){
        return releaseUserGrade;
    }
    public String getReleaseUserSchool(){
        return releaseUserSchool;
    }
    public String getReleaseUserSex(){
        return releaseUserSex;
    }
    public String getReleaseUserTel(){
        return releaseUserTel;
    }
    public String getReleaseUserNickname(){
        return releaseUserNickname;
    }
    public String getCompany(){
        return company;
    }
    /*public String getSex(){
        return sex;
    }*/
    public String getDeliver(){
        return deliver;
    }
    public String getLocal(){
        return local;
    }
    public String getMeetingTime(){
        return meetingTime;
    }
    public int getWeight(){
        return weight;
    }
    public int getPrice(){
        return price;
    }
    public int getMoney(){
        return money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company);
        /*dest.writeString(sex);*/
        dest.writeString(deliver);
        dest.writeString(local);
        dest.writeInt(weight);
        dest.writeInt(price);
        dest.writeInt(money);
        dest.writeString(meetingTime);
        dest.writeString(type);
        dest.writeString(releaseUserNickname);
        dest.writeString(releaseUserTel);
        dest.writeString(releaseUserSex);
        dest.writeString(releaseUserSchool);
        dest.writeDouble(releaseUserGrade);
        dest.writeString(expressType);
        dest.writeString(meetingLocation);
        dest.writeString(senderName);
        dest.writeString(senderTel);
        dest.writeString(senderLocation);
        dest.writeString(receiverName);
        dest.writeString(receiverTel);
        dest.writeString(receiverLocation);
        dest.writeString(note);
        dest.writeInt(taskId);
    }

    public static final Parcelable.Creator<TaskEntity> CREATOR = new Parcelable.Creator<TaskEntity>(){
        @Override
        public TaskEntity createFromParcel(Parcel parcel) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.company = parcel.readString();
            /*taskEntity.sex = parcel.readString();*/
            taskEntity.deliver = parcel.readString();
            taskEntity.local = parcel.readString();
            taskEntity.weight = parcel.readInt();
            taskEntity.price = parcel.readInt();
            taskEntity.money = parcel.readInt();
            taskEntity.meetingTime = parcel.readString();
            taskEntity.type = parcel.readString();
            taskEntity.releaseUserNickname = parcel.readString();
            taskEntity.releaseUserTel = parcel.readString();
            taskEntity.releaseUserSex = parcel.readString();
            taskEntity.releaseUserSchool = parcel.readString();
            taskEntity.releaseUserGrade = parcel.readDouble();
            taskEntity.expressType = parcel.readString();
            taskEntity.meetingLocation = parcel.readString();
            taskEntity.senderName = parcel.readString();
            taskEntity.senderTel = parcel.readString();
            taskEntity.senderLocation = parcel.readString();
            taskEntity.receiverName = parcel.readString();
            taskEntity.receiverTel = parcel.readString();
            taskEntity.receiverLocation = parcel.readString();
            taskEntity.note = parcel.readString();
            taskEntity.taskId = parcel.readInt();

            return taskEntity;
        }

        @Override
        public TaskEntity[] newArray(int i) {
            return new TaskEntity[i];
        }
    };
}
