package com.ingenioustechnologies.finance.model;

import com.google.gson.annotations.SerializedName;

public class CustomerVal {
    @SerializedName("borrowerid")
    String borrowerid;
    @SerializedName("borrower_name")
    String customername;
    @SerializedName("borrower_contact_no")
    String numbers;

    public String getBorrowerid() {
        return borrowerid;
    }

    public void setBorrowerid(String borrowerid) {
        this.borrowerid = borrowerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
