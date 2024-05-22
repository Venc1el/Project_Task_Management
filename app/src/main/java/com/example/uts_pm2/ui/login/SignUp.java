package com.example.uts_pm2.ui.login;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uts_pm2.R;
import com.example.uts_pm2.database.DBConnect;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {
    private DBConnect dbConnect;
    private TextInputEditText etEmail, etUsername, etFullnamae, etPassword;
    private MaterialButton btnSignUp;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbConnect = new DBConnect(this);

        etEmail = findViewById(R.id.emailEditText);
        etUsername = findViewById(R.id.usernameEditText);
        etFullnamae = findViewById(R.id.fullnameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnSignUp = findViewById(R.id.buttonCreateAccount);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> finish());
        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String username = etUsername.getText().toString();
            String fullname = etFullnamae.getText().toString();
            String password = etPassword.getText().toString();

            if(email.isEmpty()){
                etEmail.setError("Email cannot be empty");
            } else if (username.isEmpty()) {
                etUsername.setError("Username cannot be empty");
            }else if (fullname.isEmpty()) {
                etFullnamae.setError("Fullname cannot be empty");
            }else if (password.isEmpty()) {
                etPassword.setError("Password cannot be empty");
            }else{
                dbConnect.createAccount(username, password, email, fullname);
                Toast.makeText(this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }
}