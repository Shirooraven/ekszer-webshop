package com.example.ekszer_webshop;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSeederActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();

        seedProducts();
    }

    private void seedProducts() {
        List<Map<String, Object>> products = new ArrayList<>();

        products.add(makeProduct("Arany nyaklánc", "Nyaklánc", "Arany", 30000, false));
        products.add(makeProduct("Ezüst nyaklánc", "Nyaklánc", "Ezüst", 18000, true));
        products.add(makeProduct("Rubin gyűrű", "Gyűrű", "Piros", 25000, false));
        products.add(makeProduct("Zafír fülbevaló", "Fülbevaló", "Kék", 27000, true));
        products.add(makeProduct("Ezüst karkötő", "Karkötő", "Ezüst", 20000, false));
        products.add(makeProduct("Gyémánt nyaklánc", "Nyaklánc", "Fehér", 50000, true));
        products.add(makeProduct("Arany gyűrű", "Gyűrű", "Arany", 28000, false));
        products.add(makeProduct("Zöld smaragd karkötő", "Karkötő", "Zöld", 32000, false));
        products.add(makeProduct("Fekete ónix fülbevaló", "Fülbevaló", "Fekete", 22000, true));
        products.add(makeProduct("Fehérarany nyaklánc", "Nyaklánc", "Fehér", 35000, false));
        products.add(makeProduct("Platina gyűrű", "Gyűrű", "Szürke", 40000, true));
        products.add(makeProduct("Swarovski karkötő", "Karkötő", "Rózsaszín", 26000, false));
        products.add(makeProduct("Topáz fülbevaló", "Fülbevaló", "Világoskék", 24000, false));
        products.add(makeProduct("Arany fülbevaló", "Fülbevaló", "Arany", 27000, false));
        products.add(makeProduct("Achát gyűrű", "Gyűrű", "Barna", 21000, false));
        products.add(makeProduct("Ezüst medál", "Nyaklánc", "Ezüst", 16000, true));
        products.add(makeProduct("Gyémánt gyűrű", "Gyűrű", "Fehér", 55000, true));
        products.add(makeProduct("Zománc fülbevaló", "Fülbevaló", "Színes", 19000, false));
        products.add(makeProduct("Arany karkötő", "Karkötő", "Arany", 30000, true));
        products.add(makeProduct("Fehér gyöngy nyaklánc", "Nyaklánc", "Fehér", 33000, false));

        for (Map<String, Object> product : products) {
            db.collection("products").add(product)
                    .addOnSuccessListener(documentReference ->
                            Log.d("Seeder", "Sikeres feltöltés: " + documentReference.getId()))
                    .addOnFailureListener(e ->
                            Log.e("Seeder", "Hiba feltöltés közben", e));
        }
    }

    private Map<String, Object> makeProduct(String name, String category, String color, int price, boolean sale) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("category", category);
        product.put("color", color);
        product.put("price", price);
        product.put("sale", sale);
        return product;
    }
}
