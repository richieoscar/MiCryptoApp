package com.example.micrypto.interfaces;

import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.JsonMarket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CoinApiInterface {
    @Headers("X-CMC_PRO_API_KEY: b41a2ba4-3b14-4cdb-a1ad-a7bfff0afbe4")

    @GET("/v1/cryptocurrency/listings/latest?start=1&limit=10")
    Call<JsonMarket> getJsonData();
}
