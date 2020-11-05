package com.example.micrypto.model;

import java.util.List;


public class JsonMarket {

    private Status status;

    private List<CoinEntity> data;

    public Status getStatus() {
        return status;
    }

    public List<CoinEntity> getData() {
        return data;
    }


}
