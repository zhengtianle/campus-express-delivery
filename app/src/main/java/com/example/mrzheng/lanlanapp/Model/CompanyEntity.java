package com.example.mrzheng.lanlanapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MrZheng
 */
public class CompanyEntity {
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("logo")
    private String logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
