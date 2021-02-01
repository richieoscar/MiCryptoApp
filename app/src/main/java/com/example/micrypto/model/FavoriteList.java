package com.example.micrypto.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_list", indices = {@Index(value = "id", unique = true)})
public class FavoriteList {

    public FavoriteList(int id, @NonNull String name, @NonNull String slug, double price, Quote quote, @NonNull String symbol) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.quote = quote;
        this.symbol = symbol;
    }
    public FavoriteList(){

    }


    @PrimaryKey
    @NonNull
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String slug;
    @NonNull
    private double price;

    @NonNull
    @Embedded(prefix = "fav_quote")
    Quote quote;


    @NonNull
    private String symbol;

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(@NonNull String symbol) {
        this.symbol = symbol;
    }


    public Quote getQuote() {
        return quote;
    }

    public void setQuote(@NonNull Quote quote) {
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
