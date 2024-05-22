package com.example.uts_pm2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uts_pm2.MainActivity;
import com.example.uts_pm2.PrefsManager;
import com.example.uts_pm2.R;
import com.example.uts_pm2.data.UserData;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private TextInputEditText emailInput, passwordInput;
    private TextView tvSignUp;
    private MaterialButton loginButton;
    private DBConnect dbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.buttonLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        dbConnect = new DBConnect(this);

        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (dbConnect.validateLogin(email, password)){
                    UserData userData = dbConnect.getUserData(email);

                    PrefsManager prefsManager = new PrefsManager(Login.this);
                    prefsManager.setUserData(userData);
                    prefsManager.setLogin(true);

                    dbConnect.markUserLoggedIn(email);

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}