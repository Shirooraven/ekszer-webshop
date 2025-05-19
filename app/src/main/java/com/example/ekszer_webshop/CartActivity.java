package com.example.ekszer_webshop;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

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

     protected void onResume() {
        super.onResume();
        loadCartItems(); // újratöltés, amikor visszatérsz az activity-re
    }
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cart);

    // Toolbar beállítása
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // RecyclerView és Firestore inicializálás
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

    TextView totalText = findViewById(R.id.textTotalPrice);
int total = 0;

for (CartItem item : cartItemList) {
    int price = item.getPrice();
    if (item.isSale()) {
        price = (int)(price * 0.85); // -15%
    }
    total += price;
}

totalText.setText("Végösszeg: " + total + " Ft");

    
}


    private void loadCartItems() {
    db.collection("cart")
      .document(uid)
      .collection("products")
      .get()
      .addOnSuccessListener(queryDocumentSnapshots -> {
          cartItemList.clear();
          for (DocumentSnapshot doc : queryDocumentSnapshots) {
              CartItem item = doc.toObject(CartItem.class); // ⬅️ itt kerül be
              cartItemList.add(item);
          }
          cartAdapter.notifyDataSetChanged();

          // Végösszeg kiszámítása
          TextView totalText = findViewById(R.id.textTotalPrice);
          long total = 0;
          for (CartItem item : cartItemList) {
              long price = item.getPrice();
              if (item.isSale()) {
                  price = (long) (price * 0.85); // -15%
              }
              total += price * item.getQuantity(); // többszörözve a mennyiséggel
          }
          totalText.setText("Végösszeg: " + total + " Ft");

          // Gomb engedélyezés, ha van termék
          Button continueButton = findViewById(R.id.buttonContinue);
        
          continueButton.setEnabled(!cartItemList.isEmpty());

      })
      .addOnFailureListener(e ->
          Toast.makeText(this, "Hiba a kosár betöltésekor", Toast.LENGTH_SHORT).show());
}


}
