package com.example.ikeaedgeservice.model;

public class Store {

    private String id;
    private String storeName;
    private String articleNumber;
    private int stock;

    public Store() {
    }

    public Store(String storeName, String articleNumber, int stock) {
        this.storeName = storeName;
        this.articleNumber = articleNumber;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}




