package com.example.ekszer_webshop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class ProductsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        RecyclerView rv = findViewById(R.id.recyclerViewProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Itt a két termék:
        Product[] products = {
                new Product("Necklace", R.drawable.necklace),
                new Product("Earring", R.drawable.earring)
        };
        rv.setAdapter(new ProductAdapter(Arrays.asList(products)));
    }
}
