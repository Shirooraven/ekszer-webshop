package com.example.ekszer_webshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // A login layoutot tölti be

        // Firebase Auth példány
        mAuth = FirebaseAuth.getInstance();

        EditText emailEditText = findViewById(R.id.editTextLoginEmail);
        EditText passwordEditText = findViewById(R.id.editTextLoginPassword);
        Button loginButton = findViewById(R.id.buttonLogin);


        // Login gomb kattintás
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Bejelentkezés Firebase-el
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            // Átirányítás MainPageActivity-re
                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                            startActivity(intent);
                            finish(); // LoginActivity bezárása
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
        });
    }
}
