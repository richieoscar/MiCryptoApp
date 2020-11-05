package com.example.micrypto;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinRetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URl = "https://pro-api.coinmarketcap.com/";

        public  static Retrofit getCoinApiInstance(){
            if (retrofit == null ){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URl)
                        .addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;
        }

}

