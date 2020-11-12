package com.example.micrypto;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.repository.CoinRepository;

import java.util.List;

public class CoinViewModel extends AndroidViewModel {

   public  CoinRepository repository;
    public LiveData<List<CoinEntity>> mAllCoins;

   public String price;

    public CoinViewModel(@NonNull Application application) {
        super(application);
        repository = new CoinRepository(application);
        repository.fetch();
        mAllCoins = repository.getCoinsDataFromRepo();
    }


    public void makeApiCall(){
       repository.fetch();

   }

    public LiveData<List<CoinEntity>> getAllCoins() {
        return mAllCoins;
    }



}
