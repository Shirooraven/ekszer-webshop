package com.example.ekszer_webshop;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String color;
    private int price;
    private boolean sale;
    private String imageUrl; 


    // Üres konstruktor Firestore-nak
    public Product() {}

    public Product(String name, String category, String color, int price, boolean sale, String imageUrl) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.price = price;
        this.sale = sale;
        this.imageUrl = imageUrl;
    }

    // Getterek (Firestore + adapter számára)
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public boolean isSale() {
        return sale;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
