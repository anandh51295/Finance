package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

public class TrackRes {
    @SerializedName("response")
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
