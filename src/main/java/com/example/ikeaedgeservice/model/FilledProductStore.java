package com.example.ikeaedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class FilledProductStore {

    private String name;
    private String category;
    private String description;
    private String image;
    private String articleNumber;
    private Boolean delivery;
    private String material;
    private String maintenance;
    private String environment;
    private double price;
    private String size;
    private List<StoreStock> storeStocks;

    public FilledProductStore(Product product, List<Store> stores) {
        setName(product.getName());
        setCategory(product.getCategory());
        setDescription(product.getDescription());
        setImage(product.getImage());
        setArticleNumber(product.getArticleNumber());
        setDelivery(product.getDelivery());
        setMaterial(product.getMaterial());
        setMaintenance(product.getMaintenance());
        setEnvironment(product.getEnvironment());
        setPrice(product.getPrice());
        setSize(product.getSize());
        storeStocks = new ArrayList<>();
        stores.forEach(store -> {
            storeStocks.add(new StoreStock(store.getStoreName(), store.getStock()));
        });
        setStoreStocks(storeStocks);
    }

//    public FilledProductStore(Product product, Store store) {
//
//        setName(product.getName());
//        setCategory(product.getCategory());
//        setDescription(product.getDescription());
//        setImage(product.getImage());
//        setArticleNumber(product.getArticleNumber());
//        setDelivery(product.getDelivery());
//        setMaterial(product.getMaterial());
//        setMaintenance(product.getMaintenance());
//        setEnvironment(product.getEnvironment());
//        setPrice(product.getPrice());
//        setSize(product.getSize());
//        storeStocks = new ArrayList<>();
//        storeStocks.add(new StoreStock(store.getStoreName(), store.getStock()));
//        setStoreStocks(storeStocks);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }


    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<StoreStock> getStoreStocks() {
        return storeStocks;
    }

    public void setStoreStocks(List<StoreStock> storeStocks) {
        this.storeStocks = storeStocks;
    }
}
