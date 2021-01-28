package com.example.micrypto.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.micrypto.R;
import com.example.micrypto.model.CoinEntity;

public class FavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        TextView show = findViewById(R.id.textView);

        CoinEntity coin = getIntent().getParcelableExtra("CoinEntity");
        show.setText(coin.getName());


    }
}