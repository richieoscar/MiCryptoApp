package com.example.micrypto;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.micrypto.db.CoinDataDao;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.FavoriteList;
import com.example.micrypto.repository.CoinRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    public Boolean show = false;

    public String price;
    public String priceFormat;
    public String p24Format;
    public String pHourFormat;
    public String pWeekFormat;
    public String image;
    public String coinVolume;
    private int id;
    public CoinRepository repository;
    public CoinDataDao coinDataDao;
    public LiveData<CoinEntity> coin;


    public DetailViewModel(Application application) {
        super(application);
        repository = new CoinRepository(application);
    }

    public void fetchCoin(int id){
        repository.fetchCoin(id);
       coin = repository.getCoinFromRepo(id);
    }

    public void addCoinToFavList(FavoriteList coin){
        repository.addToFavorites(coin);
    }



    public void setId(int id) {
        this.id = id;
    }

    public int getCoinId() {
        return id;
    }

    public LiveData<CoinEntity> getCoinData() {
        return coin;
    }
}
