package com.example.micrypto.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.micrypto.model.CoinDataEntity;
import com.example.micrypto.model.CoinEntity;

import java.util.List;
@Dao
public interface CoinDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoinData(CoinEntity coin);

    @Query("SELECT * from coin_table WHERE id = :id")
    LiveData<CoinEntity> getCoinBYId(int id);

    @Query("SELECT * FROM coin_table ORDER BY name ASC")
    LiveData<CoinEntity>  getCoin();
}
