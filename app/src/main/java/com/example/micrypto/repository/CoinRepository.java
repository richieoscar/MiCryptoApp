package com.example.micrypto.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.micrypto.CoinRetrofitInstance;
import com.example.micrypto.db.CoinDao;
import com.example.micrypto.db.CoinRoomDatabase;
import com.example.micrypto.interfaces.CoinApiInterface;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.JsonMarket;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinRepository {
    private static final String TAG = "CoinRepository";
    public  CoinDao coinDao;
    public LiveData<List<CoinEntity>> allCoins;


    public  CoinRepository(Application application){
        CoinRoomDatabase coinRoomDatabase = CoinRoomDatabase.getDataBseInstance(application);
        coinDao= coinRoomDatabase.coinDao();
        allCoins = coinDao.getAllCoins();

    }

    public void fetch(){

        CoinApiInterface connect = CoinRetrofitInstance.getCoinApiInstance().create(CoinApiInterface.class);
        Call<JsonMarket> call = connect.getJsonData();

        call.enqueue(new Callback<JsonMarket>() {
            @Override
            public void onResponse(Call<JsonMarket> call, Response<JsonMarket> response) {
                Log.d(TAG, "onResponse: get data " +response.body().getData());
                CoinRoomDatabase.writeToDatabase.execute(()->{
                    coinDao.insertCoin(response.body().getData());
                });

                Log.d(TAG, "onResponse: inserted into database" );

            }

            @Override
            public void onFailure(Call<JsonMarket> call, Throwable t) {
                Log.d(TAG, "onFailure: no data" + t.getMessage().toString());
                // mError.setValue(getErrorFromJsonMarket());




            }
        });
    }

    public  LiveData<List<CoinEntity>> getCoinsDataFromRepo() {

        return allCoins;
        }


}
