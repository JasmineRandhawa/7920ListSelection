package com.example.ListSelection.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ListSelection.R;

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Splash.this, InstructionsActivity.class));
            finish();
        }, secondsDelayed * 1000);
    }
}