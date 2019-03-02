package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckRes {
    @SerializedName("response")
    ArrayList<CheckVAl> response;

    public ArrayList<CheckVAl> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<CheckVAl> response) {
        this.response = response;
    }
}
