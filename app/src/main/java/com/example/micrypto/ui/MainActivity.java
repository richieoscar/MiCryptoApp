package com.example.micrypto.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.micrypto.CoinRetrofitInstance;
import com.example.micrypto.CoinViewModel;
import com.example.micrypto.R;
import com.example.micrypto.adapter.CoinAdapter;
import com.example.micrypto.datasource.RemoteDataSource;
import com.example.micrypto.interfaces.CoinApiInterface;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.JsonMarket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView coinRv;
    public CoinViewModel viewModel;
    private CoinAdapter adapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        setUpRecyclerView();
        setUpViewModel();

        refresh();

    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);

    }
    private  void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void setUpRecyclerView(){
        coinRv = findViewById(R.id.coin_rv);
         adapter = new CoinAdapter(this);
        coinRv.setAdapter(adapter);
        coinRv.setLayoutManager(new LinearLayoutManager(this));


    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                setUpViewModel();
                Toast.makeText(MainActivity.this, "Coins Updated", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void setUpViewModel(){

        showProgressBar();
       // swipeRefreshLayout.setRefreshing(true);
        viewModel = new ViewModelProvider(this).get(CoinViewModel.class);
       viewModel.getAllCoins().observe(this, new Observer<List<CoinEntity>>() {
           @Override
           public void onChanged(List<CoinEntity> coinEntities) {

               adapter.setCoins(coinEntities);
               swipeRefreshLayout.setRefreshing(false);
               hideProgressBar();

           }

       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crypto_menu, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.item_settings).setTitle("Day Mode");
        }
        else menu.findItem(R.id.item_settings).setTitle("Night Mode");
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_settings:
                int nighMode = AppCompatDelegate.getDefaultNightMode();
                if(nighMode == AppCompatDelegate.MODE_NIGHT_YES){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                Log.d(TAG, "onOptionsItemSelected: recreate");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        recreate();
                    }
                });
                    return  true;
                

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}