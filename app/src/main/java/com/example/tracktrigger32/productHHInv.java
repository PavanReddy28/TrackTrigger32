package com.example.tracktrigger32;

public class productHHInv {
    private String name;
    private String id;
    private String description;
    private String category;
    private int quantity;
    private int pos;

    public productHHInv(){}

    public productHHInv(String name, String id, String description, String category, int quantity, int pos) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

