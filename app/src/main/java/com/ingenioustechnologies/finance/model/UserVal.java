package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

public class UserVal {
    @SerializedName("id")
    int userid;
    @SerializedName("username")
    String username;
    @SerializedName("role")
    String role;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
