package com.example.uts_pm2.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uts_pm2.MainActivity;
import com.example.uts_pm2.R;
import com.google.android.material.button.MaterialButton;

public class Onboard extends AppCompatActivity {

    MaterialButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        getStartedButton = findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(Onboard.this, MainActivity.class);
            startActivity(intent);
        });
    }
}