package com.example.micrypto.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.micrypto.model.CoinEntity;

import java.util.List;



@Dao
public interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoin(List<CoinEntity> coins);

    @Query("DELETE FROM coin_table")
    void delete();

    @Query("SELECT * FROM coin_table ORDER BY name ASC")
   LiveData<List<CoinEntity>>  getAllCoins();

    @Query("SELECT * from coin_table WHERE id = :id")
    LiveData<List<CoinEntity>> getCoinBYId(int id);

}
