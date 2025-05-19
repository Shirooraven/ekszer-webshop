package com.example.ekszer_webshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();
    private List<Product> originalList = new ArrayList<>();

    private Spinner spinnerCategory;
    private EditText editTextSearch;
    private ImageButton buttonSearchToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        // === Toolbar beállítása ===
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // === UI elemek ===
        spinnerCategory = findViewById(R.id.spinnerCategory);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearchToggle = findViewById(R.id.buttonSearchToggle);

        // === RecyclerView beállítás ===
        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // === Firestore inicializálás ===
        db = FirebaseFirestore.getInstance();
        loadProductsFromFirestore();

        // === Kategória szűrő Spinner ===
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Összes", "Nyaklánc", "Fülbevaló", "Karkötő", "Gyűrű")
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                filterProducts(selected, editTextSearch.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // === Keresőmező mutatása / elrejtése ===
        buttonSearchToggle.setOnClickListener(v -> {
            if (editTextSearch.getVisibility() == View.GONE) {
                editTextSearch.setVisibility(View.VISIBLE);
            } else {
                editTextSearch.setVisibility(View.GONE);
            }
        });

        // === Kereső szövegfigyelés ===
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String selectedCategory = (String) spinnerCategory.getSelectedItem();
                filterProducts(selectedCategory, s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    // === Firestore-ból betöltés ===
 private void loadProductsFromFirestore() {
    db.collection("products")
        .get()
        .addOnSuccessListener(queryDocumentSnapshots -> {
            productList.clear();
            originalList.clear();
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Product product = doc.toObject(Product.class);
                originalList.add(product);
            }

            // 🔁 Alapértelmezett megjelenítés: összes termék
            filterProducts("Összes", "");

        })
        .addOnFailureListener(e -> {
            Toast.makeText(this, "Hiba a termékek betöltésekor", Toast.LENGTH_SHORT).show();
        });
}


    // === Szűrés kategória + név szerint ===
    private void filterProducts(String category, String keyword) {
        List<Product> filtered = new ArrayList<>();

        for (Product p : originalList) {
            boolean matchCategory = category.equals("Összes") || p.getCategory().equalsIgnoreCase(category);
            boolean matchName = keyword.isEmpty() || p.getName().toLowerCase().contains(keyword.toLowerCase());

            if (matchCategory && matchName) {
                filtered.add(p);
            }
        }

        adapter.filterList(filtered);
    }

  @Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.products_menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_cart) {
        startActivity(new Intent(this, CartActivity.class));
        return true;
    } else if (item.getItemId() == R.id.action_logout) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
}

    
    
}
