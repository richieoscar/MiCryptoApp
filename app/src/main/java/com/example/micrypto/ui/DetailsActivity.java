package com.example.micrypto.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.micrypto.R;
import com.example.micrypto.model.CoinEntity;

public class DetailsActivity extends AppCompatActivity {
    final String BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView name = findViewById(R.id.name_text);
        TextView price = findViewById(R.id.price_text);
        ImageView image = findViewById(R.id.imageView);
        CoinEntity coin = getIntent().getParcelableExtra("CoinEntity");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(coin.getName());
        actionBar.isHideOnContentScrollEnabled();


       name.setText(coin.getName());
       price.setText(String.valueOf(coin.getQuote().getUSD().getPrice()));
            int coinId = coin.getID();
        String imgType = ".png";
        String imgSize = "128x128/";
        String imgUrl = BASE_URL +imgSize +coinId +imgType;

        Glide.with(this).load(imgUrl).into(image);
    }
}