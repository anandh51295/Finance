package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocRes {
    @SerializedName("response")
    ArrayList<LocVal> response;

    public ArrayList<LocVal> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<LocVal> response) {
        this.response = response;
    }
}
