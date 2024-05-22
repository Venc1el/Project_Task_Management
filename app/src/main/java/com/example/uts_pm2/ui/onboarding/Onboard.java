package com.example.uts_pm2.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uts_pm2.MainActivity;
import com.example.uts_pm2.PrefsManager;
import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.example.uts_pm2.ui.login.Login;
import com.google.android.material.button.MaterialButton;

public class Onboard extends AppCompatActivity {

    MaterialButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefsManager prefsManager = new PrefsManager(this);
        DBConnect db = new DBConnect(this);

        if (prefsManager.isFirstLaunch()) {
            setContentView(R.layout.activity_onboard);

            getStartedButton = findViewById(R.id.getStartedButton);
            getStartedButton.setOnClickListener(v -> {
                if (db.isAnyUserLoggedIn()){
                    Intent intent = new Intent(Onboard.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(Onboard.this, Login.class);
                    startActivity(intent);
                }
                finish();
            });

            prefsManager.setFirstLaunch(false);
        } else if (db.isAnyUserLoggedIn()) {
            Intent intent = new Intent(Onboard.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(Onboard.this, Login.class));
            finish();
        }

    }
}