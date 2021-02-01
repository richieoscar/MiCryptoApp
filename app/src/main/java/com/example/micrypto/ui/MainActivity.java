package com.example.micrypto.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.micrypto.CoinViewModel;
import com.example.micrypto.R;
import com.example.micrypto.adapter.CoinAdapter;

import com.example.micrypto.model.CoinEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView coinRv;
    public CoinViewModel viewModel;
    private CoinAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private AlertDialog alertDialog;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager notificationManager;
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.micrypto.ui.ACTION_UPDATE_NOTIFICATION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        createNotification();
        setUpViewModel();
        setUpRecyclerView();



        refresh();

    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void setUpRecyclerView() {

        coinRv = findViewById(R.id.coin_rv);
        adapter = new CoinAdapter(this);
        coinRv.setAdapter(adapter);
        coinRv.setLayoutManager(new LinearLayoutManager(this));


    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                setUpViewModel();
                Toast.makeText(MainActivity.this, "Coins Updated", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void setUpViewModel() {

        showProgressBar();
        // swipeRefreshLayout.setRefreshing(true);
        viewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        viewModel.getAllCoins().observe(this, new Observer<List<CoinEntity>>() {
            @Override
            public void onChanged(List<CoinEntity> coinEntities) {

                adapter.setCoins(coinEntities);

                swipeRefreshLayout.setRefreshing(false);


            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crypto_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_about:
                Toast.makeText(this, "We are upcoming in app development", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_price:
                priceAlert();
                return true;
            case R.id.item_search:
                searchForCoin();
                Toast.makeText(this, "searching", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_fav:
                gotoFavoritesActivity();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void gotoFavoritesActivity() {
        startActivity(new Intent(this, FavoriteActivity.class));
    }

    private void searchForCoin() {

    }

    public void priceAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.price_alert, null);
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();

        Button set = view.findViewById(R.id.button_set);
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        set.setOnClickListener((v) -> {
            EditText price = view.findViewById(R.id.editText_price);
            if (price.getText().toString().isEmpty()) price.setError("price required");
            else {
                viewModel.price = price.getText().toString();
                price.setText("");
                Toast.makeText(MainActivity.this, "Notification set" + viewModel.price, Toast.LENGTH_SHORT).show();
                send();
                alertDialog.dismiss();
            }

        });

        imageButton.setOnClickListener((v) -> {
            alertDialog.dismiss();

        });

    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Price Alert")
                .setContentText(String.valueOf(viewModel.price))
                // .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }

    private void createNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Mascot Notifications", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setDescription("Noitification from Mi Crypto");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void send() {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

}