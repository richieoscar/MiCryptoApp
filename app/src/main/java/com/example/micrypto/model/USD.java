package com.example.micrypto.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class USD implements Parcelable {
    public USD(double price, double volume24Hours, double percentChangePerHour, double percentChangePerDay, double percentChangeInSevenDays) {
        this.price = price;
        this.volume24Hours = volume24Hours;
        this.percentChangePerHour = percentChangePerHour;
        this.percentChangePerDay = percentChangePerDay;
        this.percentChangeInSevenDays = percentChangeInSevenDays;
    }

    protected USD(Parcel in) {
        price = in.readDouble();
        volume24Hours = in.readDouble();
        percentChangePerHour = in.readDouble();
        percentChangePerDay = in.readDouble();
        percentChangeInSevenDays = in.readDouble();
    }

    public static final Creator<USD> CREATOR = new Creator<USD>() {
        @Override
        public USD createFromParcel(Parcel in) {
            return new USD(in);
        }

        @Override
        public USD[] newArray(int size) {
            return new USD[size];
        }
    };

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVolume24Hours(double volume24Hours) {
        this.volume24Hours = volume24Hours;
    }

    public void setPercentChangePerHour(double percentChangePerHour) {
        this.percentChangePerHour = percentChangePerHour;
    }

    public void setPercentChangePerDay(double percentChangePerDay) {
        this.percentChangePerDay = percentChangePerDay;
    }

    public void setPercentChangeInSevenDays(double percentChangeInSevenDays) {
        this.percentChangeInSevenDays = percentChangeInSevenDays;
    }

    private double price;

    @SerializedName("volume_24h")
    @ColumnInfo(name = "volume_24h" )
    private double volume24Hours;

    @SerializedName("percent_change_1h")
    @ColumnInfo(name = "percent_change_1h")
    private  double percentChangePerHour;

    @SerializedName("percent_change_24h")
    @ColumnInfo(name = "percent_change_24h")
    private  double percentChangePerDay;

    @SerializedName("percent_change_7d")
    @ColumnInfo(name = "percent_change_7d")
    private  double percentChangeInSevenDays;

    public double getPrice() {
        return price;
    }

    public double getVolume24Hours() {
        return volume24Hours;
    }

    public double getPercentChangePerHour() {
        return percentChangePerHour;
    }

    public double getPercentChangePerDay() {
        return percentChangePerDay;
    }

    public double getPercentChangeInSevenDays() {
        return percentChangeInSevenDays;
    }

    @Override
    public String toString() {
        return "USD{" +
                "price=" + price +
                ", volume24Hours=" + volume24Hours +
                ", percentChangePerHour=" + percentChangePerHour +
                ", percentChangePerDay=" + percentChangePerDay +
                ", percentChangeInSevenDays=" + percentChangeInSevenDays +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeDouble(volume24Hours);
        dest.writeDouble(percentChangePerHour);
        dest.writeDouble(percentChangePerDay);
        dest.writeDouble(percentChangeInSevenDays);
    }
}
