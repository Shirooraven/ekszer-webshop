package com.example.ekszer_webshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imageProduct;
    private TextView textName, textCategory, textColor, textPrice;
    private Button buttonAddToCart;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageProduct = findViewById(R.id.imageProductDetail);
        textName = findViewById(R.id.textNameDetail);
        textCategory = findViewById(R.id.textCategoryDetail);
        textColor = findViewById(R.id.textColorDetail);
        textPrice = findViewById(R.id.textPriceDetail);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        // Átvett termék lekérése
        selectedProduct = (Product) getIntent().getSerializableExtra("product");

        if (selectedProduct != null) {
            textName.setText(selectedProduct.getName());
            textCategory.setText("Kategória: " + selectedProduct.getCategory());
            textColor.setText("Szín: " + selectedProduct.getColor());
            textPrice.setText("Ár: " + selectedProduct.getPrice() + " Ft");

            Glide.with(this)
                    .load(selectedProduct.getImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(imageProduct);
        }

        buttonAddToCart.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("name", selectedProduct.getName());
        cartItem.put("category", selectedProduct.getCategory());
        cartItem.put("color", selectedProduct.getColor());
        cartItem.put("price", selectedProduct.getPrice());
        cartItem.put("imageUrl", selectedProduct.getImageUrl());
        cartItem.put("quantity", 1);

        db.collection("cart")
                .document(uid)
                .collection("products")
                .document(selectedProduct.getName())
                .set(cartItem)
                .addOnSuccessListener(aVoid -> 
                    Toast.makeText(this, "Hozzáadva a kosárhoz!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> 
                    Toast.makeText(this, "Hiba történt!", Toast.LENGTH_SHORT).show());
    }
}
