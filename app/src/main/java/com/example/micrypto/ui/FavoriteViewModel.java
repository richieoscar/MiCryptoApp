package com.example.micrypto.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.micrypto.model.FavoriteList;
import com.example.micrypto.repository.CoinRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

   private   CoinRepository repository;
    private LiveData<List<FavoriteList>> mAllCoins;


    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new CoinRepository(application);
        mAllCoins = repository.getFavCoinsFromDataBase();
    }


    public LiveData<List<FavoriteList>> getFavoriteCoins(){
        return  mAllCoins;
    }




}
