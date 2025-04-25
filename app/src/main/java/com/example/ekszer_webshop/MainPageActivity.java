package com.example.ekszer_webshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPageActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private Button logoutButton, productsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        welcomeTextView = findViewById(R.id.textViewWelcome);
        logoutButton    = findViewById(R.id.buttonLogout);
        productsButton  = findViewById(R.id.buttonProducts);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            welcomeTextView.setText("Üdvözlünk, " + user.getEmail() + "!");
        }

        Animation popAnim = AnimationUtils.loadAnimation(this, R.anim.scale_pop);

        productsButton.setOnClickListener(v -> {
            v.startAnimation(popAnim);
            startActivity(new Intent(MainPageActivity.this, ProductsActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            v.startAnimation(popAnim);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
            finish();
        });
    }
}
