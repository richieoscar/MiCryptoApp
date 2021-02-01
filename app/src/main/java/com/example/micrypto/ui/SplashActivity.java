package com.example.micrypto.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.micrypto.R;

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        loadSplashScreen();

    }

    private void loadSplashScreen() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(SplashActivity.this, MainActivity.class));
               finish();
           }
       }, DELAY);
    }
}