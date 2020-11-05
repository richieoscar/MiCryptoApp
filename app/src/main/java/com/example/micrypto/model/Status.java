package com.example.micrypto.model;

import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;


public class Status {

    @SerializedName("error_message")
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
