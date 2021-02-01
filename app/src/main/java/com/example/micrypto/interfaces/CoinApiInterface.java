package com.example.micrypto.interfaces;

import com.example.micrypto.model.CoinDataEntity;
import com.example.micrypto.model.JsonMarket;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CoinApiInterface {
    @Headers("X-CMC_PRO_API_KEY: b41a2ba4-3b14-4cdb-a1ad-a7bfff0afbe4")

    @GET("/v1/cryptocurrency/listings/latest?start=1&limit=100")
    Call<JsonMarket> getJsonData();

    @Headers("X-CMC_PRO_API_KEY: b41a2ba4-3b14-4cdb-a1ad-a7bfff0afbe4")
    @GET("/v1/cryptocurrency/quotes/latest")
    Call<CoinDataEntity> getCoinData(@Query("id") int id);
}
