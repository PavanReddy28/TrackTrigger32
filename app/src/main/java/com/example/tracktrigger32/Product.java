package com.example.tracktrigger32;

import android.net.Uri;

public class Product {
    private String name;
    private String id;
    private String description;
    private String category;
    private int quantity;
    private Uri uri;

    public Product(String name, String id, String description, String category, int quantity) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

