package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerRes {
    @SerializedName("response")
    ArrayList<CustomerVal> response;


    public ArrayList<CustomerVal> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<CustomerVal> response) {
        this.response = response;
    }
}
