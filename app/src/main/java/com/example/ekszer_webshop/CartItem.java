package com.example.ekszer_webshop;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String name;
    private String category;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartItem() {}

    public CartItem(String name, String category, int price, String imageUrl, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
}
