package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserRes {
    @SerializedName("response")
    ArrayList<UserVal> response;

    public ArrayList<UserVal> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<UserVal> response) {
        this.response = response;
    }
}
