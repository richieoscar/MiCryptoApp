package com.example.micrypto.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.FavoriteList;

import java.util.List;

@Dao
public interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCoinToFavorite (FavoriteList coin);

    @Query("SELECT * FROM favorite_list ORDER BY name ASC")
    public LiveData<List<FavoriteList>> getFavoriteCoins();

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_list WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void removeFromFavorites(CoinEntity coin);

}
