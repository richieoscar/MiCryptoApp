package com.example.micrypto.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.micrypto.model.CoinEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = CoinEntity.class, version = 1, exportSchema = false)
public abstract class CoinRoomDatabase extends RoomDatabase {

     public abstract CoinDao coinDao();
        private  static  volatile CoinRoomDatabase INSTANCE;
        private static  int NUMBER_OF_THREADS = 4;
        private static  String dataBaseName = "coin_database";
       public static final ExecutorService writeToDatabase = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

         public static  CoinRoomDatabase getDataBseInstance(final Context context){
             if(INSTANCE == null){
                 synchronized (CoinRoomDatabase.class){
                     if (INSTANCE == null){
                         INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                 CoinRoomDatabase.class, dataBaseName).build();
                     }
                 }

             }
             return INSTANCE;
         }
}
