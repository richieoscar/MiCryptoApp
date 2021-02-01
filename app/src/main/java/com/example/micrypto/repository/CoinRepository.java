package com.example.micrypto.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.micrypto.CoinRetrofitInstance;
import com.example.micrypto.db.CoinDao;
import com.example.micrypto.db.CoinDataDao;
import com.example.micrypto.db.CoinRoomDatabase;
import com.example.micrypto.db.FavDao;
import com.example.micrypto.interfaces.CoinApiInterface;
import com.example.micrypto.model.CoinDataEntity;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.FavoriteList;
import com.example.micrypto.model.JsonMarket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinRepository {
    private static final String TAG = "CoinRepository";
    private CoinDao coinDao;
    private CoinDataDao coinDataDao;
    CoinEntity coinData;
    private LiveData<List<CoinEntity>> allCoins;
    private LiveData<CoinEntity> coin;
    private FavDao favDao;
    private LiveData<List<FavoriteList>> favCoins;




    public CoinRepository(Application application) {
        CoinRoomDatabase coinRoomDatabase = CoinRoomDatabase.getDataBseInstance(application);
        coinDao = coinRoomDatabase.coinDao();
        favDao = coinRoomDatabase.favDao();
       // coinDataDao = coinRoomDatabase.coinDataDao();
        allCoins = coinDao.getAllCoins();

    }

    public void fetch() {

        CoinApiInterface connect = CoinRetrofitInstance.getCoinApiInstance().create(CoinApiInterface.class);
        Call<JsonMarket> call = connect.getJsonData();

        call.enqueue(new Callback<JsonMarket>() {
            @Override
            public void onResponse(Call<JsonMarket> call, Response<JsonMarket> response) {
                Log.d(TAG, "onResponse: get data " + response.body().getData());
                CoinRoomDatabase.writeToDatabase.execute(() -> {
                    coinDao.insertCoin(response.body().getData());
                    Log.d(TAG, "onResponse: inserted into database");
                });


            }

            @Override
            public void onFailure(Call<JsonMarket> call, Throwable t) {
                Log.d(TAG, "onFailure: no data" + t.getMessage().toString());
                // mError.setValue(getErrorFromJsonMarket());


            }
        });
    }


    public void addToFavorites(FavoriteList favCoin){
        CoinRoomDatabase.writeToDatabase.execute(()->{
            favDao.addCoinToFavorite(favCoin);
            Log.d(TAG, "addToFavorites: inserted to favorites  " +favCoin.getName());
        });
    }

    public LiveData<List<FavoriteList>> getFavCoinsFromDataBase(){
        favCoins = favDao.getFavoriteCoins();
        return favCoins;
    }

    public void fetchCoin(int id) {
        CoinApiInterface connect = CoinRetrofitInstance.getCoinApiInstance().create(CoinApiInterface.class);
        Call<CoinDataEntity> call = connect.getCoinData(id);
        call.enqueue(new Callback<CoinDataEntity>() {
            @Override
            public void onResponse(Call<CoinDataEntity> call, Response<CoinDataEntity> response) {
                CoinRoomDatabase.writeToDatabase.execute(() -> {
                    coinDataDao.insertCoinData(response.body().getCoinData());
                    Log.d(TAG, "onResponse: get coin " +response.body().toString());
                  
                });

            }

            @Override
            public void onFailure(Call<CoinDataEntity> call, Throwable t) {

            }
        });

    }

    public LiveData<List<CoinEntity>> getCoinsDataFromRepo() {
        return allCoins;
    }

    public LiveData<CoinEntity> getCoinFromRepo(int id) {
        coin = coinDataDao.getCoinBYId(id);
        Log.d(TAG, "getCoinFromRepo: coin from db");
        return coin;

    }



}
