package com.example.ekszer_webshop;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String name;
    private String category;
    private int price;
    private String imageUrl;
    private int quantity;
    private boolean sale;

    // Szükséges Firestore-hoz
    public CartItem() {}

    // Teljes konstruktor, ha manuálisan hozol létre példányt
    public CartItem(String name, String category, int price, String imageUrl, int quantity, boolean sale) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.sale = sale;
    }

    // Getterek
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
    public boolean isSale() { return sale; }

    // Ez kellhet a Firestore-nak ha automatikusan deszerializál
    public void setSale(boolean sale) {
        this.sale = sale;
    }
}
