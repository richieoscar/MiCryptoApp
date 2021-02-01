package com.example.micrypto.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.micrypto.DetailViewModel;
import com.example.micrypto.R;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.FavoriteList;
import com.example.micrypto.repository.CoinRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    final String BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/";
    private TextView name;
    private TextView price;
    private ImageView image;
    private CoinEntity coin;
    private TextView symbol;
    private TextView percent_Hour;
    private TextView percent_perDay;
    private TextView percent_week;
    private TextView nairaValue;
    private ActionBar actionBar;
    private DetailViewModel viewModel;
    private int coinId;
    private static final String TAG = "DetailsActivity";
    private TextView coinVolume;
    private Button addTofav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bindViews();

        coinId = getIntent().getIntExtra("coinId", 0);
        coin = getIntent().getParcelableExtra("CoinEntity");

        setUpViewModel();
        addToFavorites();


    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // viewModel.fetchCoin(coinId);
        displayDetails();

//        viewModel.getCoinData().observe(this, new Observer<CoinEntity>() {
//            @Override
//            public void onChanged(CoinEntity coinEntity) {
//                Log.d(TAG, "onChanged: getcoin"+coinEntity.toString());
//                //displayDetails(coinEntity);
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_price_alert:
                priceAlert();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindViews() {
        name = findViewById(R.id.name_text);
        price = findViewById(R.id.price_text);
        image = findViewById(R.id.imageView);
        symbol = findViewById(R.id.textView_detail_symbol);
        percent_Hour = findViewById(R.id.textView_detail_hour);
        percent_perDay = findViewById(R.id.textView_detail_perday);
        percent_week = findViewById(R.id.textView_detail_week);
        nairaValue = findViewById(R.id.textView_naira_value);
        coinVolume = findViewById(R.id.textView_coin_volume);
        addTofav = findViewById(R.id.button_favorites);
    }

    private void displayDetails() {
        coin = getIntent().getParcelableExtra("CoinEntity");

        actionBar = getSupportActionBar();
        actionBar.setTitle(coin.getName().toUpperCase());
        actionBar.isHideOnContentScrollEnabled();


        name.setText(coin.getName());
        double priceN = coin.getQuote().getUSD().getPrice();
        double percent24 = coin.getQuote().getUSD().getPercentChangePerDay();
        double percentHour = coin.getQuote().getUSD().getPercentChangePerHour();
        double percentWeek = coin.getQuote().getUSD().getPercentChangeInSevenDays();
        double coinVol = coin.getQuote().getUSD().getVolume24Hours();

        viewModel.price = String.format("%,.3f", priceN);
        viewModel.priceFormat = String.format("%,.2f", convertToNairaValue(priceN));
        viewModel.p24Format = String.format("%,2f", percent24);
        viewModel.pHourFormat = String.format("%,2f", percentHour);
        viewModel.pWeekFormat = String.format("%,2f", percentWeek);
        viewModel.coinVolume = String.format("%,2f", coinVol);

        price.setText("$" + viewModel.price);
        symbol.setText(coin.getSymbol());
        percent_Hour.setText("% change in one hour     " + viewModel.pHourFormat);
        percent_perDay.setText("% change in 24hrs          " + viewModel.p24Format);
        percent_week.setText("% change in one week    " + viewModel.pWeekFormat);
        coinVolume.setText("Vol in 24hrs                    " + String.valueOf(viewModel.coinVolume));
        nairaValue.setText("NGN " + viewModel.priceFormat);


        loadImage();
    }

    private void loadImage() {
        String imgType = ".png";
        String imgSize = "128x128/";
        viewModel.image = BASE_URL + imgSize + coinId + imgType;

        Glide.with(this).load(viewModel.image).into(image);
    }

    private void displayDetails(CoinEntity coinEntity) {
        Log.d(TAG, "displayDetails: getCoinsfromIntent" + coinEntity.toString());

        coin = coinEntity;

        actionBar = getSupportActionBar();
        actionBar.setTitle(coin.getName());
        actionBar.isHideOnContentScrollEnabled();


        name.setText(coin.getName());
        double priceN = coin.getQuote().getUSD().getPrice();
        double percent24 = coin.getQuote().getUSD().getPercentChangePerDay();
        double percentHour = coin.getQuote().getUSD().getPercentChangePerHour();
        double percentWeek = coin.getQuote().getUSD().getPercentChangeInSevenDays();

        viewModel.priceFormat = String.format("%,.2f", priceN);
        viewModel.p24Format = String.format("%,.2f", percent24);
        viewModel.pHourFormat = String.format("%,.2f", percentHour);
        viewModel.pWeekFormat = String.format("%,.2f", percentWeek);


        price.setText("$" + viewModel.priceFormat);
        symbol.setText(coin.getSymbol());
        percent_Hour.setText(viewModel.pHourFormat);
        percent_perDay.setText(viewModel.p24Format);
        percent_week.setText(viewModel.pWeekFormat);


        int coinId = coin.getID();
        loadImage();
    }


    public double convertToNairaValue(double price) {
        return price * 460;
    }

    private void addToFavorites() {
        addTofav.setOnClickListener((v -> {
            FavoriteList favCoin = new FavoriteList();

            favCoin.setId(coin.getID());
            favCoin.setName(coin.getName());
            favCoin.setQuote(coin.getQuote());
            favCoin.setPrice(coin.getQuote().getUSD().getPrice());
            favCoin.setSymbol(coin.getSymbol());
            favCoin.setSlug(coin.getSlug());
            viewModel.addCoinToFavList(favCoin);
            Log.d(TAG, "addToFavorites: added coin" +coin.getName());
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
        }));
    }


    public void priceAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.price_alert, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button set = view.findViewById(R.id.button_set);
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        set.setOnClickListener((v) -> {
            EditText price = view.findViewById(R.id.editText_price);
            if (price.getText().toString().isEmpty()) price.setError("price required");
            else {

                Toast.makeText(DetailsActivity.this, "Notification set", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }

        });

        imageButton.setOnClickListener((v) -> {
            alertDialog.dismiss();

        });

    }
}