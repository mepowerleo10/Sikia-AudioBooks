package com.example.sikiaaudiobooks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent loginPage = new Intent(SplashScreen.this, LoginActivity.class);
                Intent gotoSelectLanguage = new Intent(SplashScreen.this, LanguageSelectActivity.class);
                startActivity(gotoSelectLanguage);
                finish();
            }
        }, SPLASH_TIMEOUT);

//        QuickDataPopulater.update();
    }
}