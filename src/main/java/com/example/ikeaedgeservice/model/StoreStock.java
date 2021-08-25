package com.example.ikeaedgeservice.model;

public class StoreStock {

    private String storeName;
    private int stock;

    public StoreStock(String storeName, int stock) {
        this.storeName = storeName;
        this.stock = stock;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getStock() {
        return stock;
    }

}

