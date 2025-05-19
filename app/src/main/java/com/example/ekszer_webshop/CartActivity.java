package com.example.ekszer_webshop;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadCartItems();
        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, OrderActivity.class));
        });
    }

    private void loadCartItems() {
        db.collection("cart")
          .document(uid)
          .collection("products")
          .get()
          .addOnSuccessListener(queryDocumentSnapshots -> {
              cartItemList.clear();
              for (DocumentSnapshot doc : queryDocumentSnapshots) {
                  CartItem item = doc.toObject(CartItem.class);
                  cartItemList.add(item);
              }
              cartAdapter.notifyDataSetChanged();
          })
          .addOnFailureListener(e -> 
              Toast.makeText(this, "Hiba a kosár betöltésekor", Toast.LENGTH_SHORT).show());
    }

}
