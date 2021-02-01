 package com.example.micrypto.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.micrypto.R;
import com.example.micrypto.adapter.FavoriteAdapter;
import com.example.micrypto.model.FavoriteList;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoriteViewModel viewModel;
    private List<FavoriteList> favCoins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);


        setUpViewModel();


    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        viewModel.getFavoriteCoins().observe(this, new Observer<List<FavoriteList>>() {
            @Override
            public void onChanged(List<FavoriteList> favoriteLists) {
                setUpRecyclerView(favoriteLists);
            }
        });
       // setUpRecyclerView(favCoins);
    }

    private void setUpRecyclerView(List<FavoriteList> favCoins) {
        recyclerView = findViewById(R.id.favRv);
        adapter = new FavoriteAdapter(favCoins, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);
    }
}