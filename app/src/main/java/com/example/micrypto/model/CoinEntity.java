package com.example.micrypto.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "coin_table")
public class CoinEntity  implements Parcelable {
    protected CoinEntity(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        symbol = in.readString();
        slug = in.readString();
        numberMarketPair = in.readInt();
        quote = in.readParcelable(Quote.class.getClassLoader());

    }

    public CoinEntity(int ID, @NonNull String name, @NonNull String symbol, @NonNull String slug, int numberMarketPair, Quote quote) {
        this.ID = ID;
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.numberMarketPair = numberMarketPair;
        this.quote = quote;
    }

    public static final Creator<CoinEntity> CREATOR = new Creator<CoinEntity>() {
        @Override
        public CoinEntity createFromParcel(Parcel in) {
            return new CoinEntity(in);
        }

        @Override
        public CoinEntity[] newArray(int size) {
            return new CoinEntity[size];
        }
    };

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setSymbol(@NonNull String symbol) {
        this.symbol = symbol;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

    public void setNumberMarketPair(int numberMarketPair) {
        this.numberMarketPair = numberMarketPair;
    }

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int ID;

    @NonNull
    private String name;

    @NonNull
    @PrimaryKey
    private String symbol;

    @NonNull
    private String slug;

    @SerializedName("num_market_pairs")
    @ColumnInfo(name = "num_market_pairs")
    private int numberMarketPair;

    @Embedded
    private Quote quote;

    public int getID() {
        return ID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @NonNull
    public String getSlug() {
        return slug;
    }

    public int getNumberMarketPair() {
        return numberMarketPair;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(slug);
        dest.writeInt(numberMarketPair);
        dest.writeParcelable(quote, 0);
    }
}

