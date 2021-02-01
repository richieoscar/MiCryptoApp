package com.example.micrypto.model;

import androidx.room.Entity;


public class CoinDataEntity {


 private Status status;
 private CoinEntity coinData;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CoinEntity getCoinData() {
        return coinData;
    }

    public void setCoinData(CoinEntity coinData) {
        this.coinData = coinData;
    }
}
