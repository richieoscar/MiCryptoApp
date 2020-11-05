package com.example.micrypto.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class Quote implements Parcelable {
    public Quote(com.example.micrypto.model.USD USD) {
        this.USD = USD;
    }

    @Embedded
    private USD USD;

    protected Quote(Parcel in) {
        USD = in.readParcelable(com.example.micrypto.model.USD.class.getClassLoader());
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    public com.example.micrypto.model.USD getUSD() {
        return USD;
    }

    public void setUSD(com.example.micrypto.model.USD USD) {
        this.USD = USD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(USD, 0);
    }
}
