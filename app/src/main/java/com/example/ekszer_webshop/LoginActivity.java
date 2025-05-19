package com.example.ekszer_webshop;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
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
                Toast.makeText(LoginActivity.this, "Add meg az email címed és a jelszót!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Bejelentkezés Firebase-el
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Sikeres bejelentkezés a Firebase-ben");

                            // Átirányítás MainPageActivity-re
                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                            startActivity(intent);
                            finish(); // LoginActivity bezárása
                            Log.d(TAG, "MainPageActivity elindítva, LoginActivity bezárva");
                        } else {
                            String error = task.getException() != null ? task.getException().getMessage() : "ismeretlen hiba";
                            Toast.makeText(LoginActivity.this, "Sikertelen bejelntkezés!: " + error, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Sikertelen bejelentkezés: " + error);
                        }
                    });
        });
    }
}
