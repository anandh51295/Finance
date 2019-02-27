package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

public class LoginRes {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("userid")
    int userid;
    @SerializedName("userrole")
    String userrole;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}
