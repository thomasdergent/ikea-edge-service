package com.example.ikeaedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class FilledStoreProduct {

    private String storeName;
    private String province;
    private String city;
    private String street;
    private int number;
    private List<CategoryProduct> categoryProducts;

    public FilledStoreProduct(Store store, List<Product> products) {
        setStoreName(store.getStoreName());
        setProvince(store.getProvince());
        setCity(store.getCity());
        setStreet(store.getStreet());
        setNumber(store.getNumber());
        categoryProducts = new ArrayList<>();
        products.forEach(product -> {
            categoryProducts.add(new CategoryProduct(product.getName(), product.getCategory(), product.getArticleNumber(), product.getDelivery(), product.getMaterial(), product.getMaintenance(), product.getEnvironment(), product.getStock(), product.getPrice(), product.getSize()));
        });

        setCategoryProducts(categoryProducts);
    }

    public FilledStoreProduct(Store store, Product product) {
        setStoreName(store.getStoreName());
        setProvince(store.getProvince());
        setCity(store.getCity());
        setStreet(store.getStreet());
        setNumber(store.getNumber());
        categoryProducts = new ArrayList<>();
        categoryProducts.add(new CategoryProduct(product.getName(), product.getCategory(), product.getArticleNumber(), product.getDelivery(), product.getMaterial(), product.getMaintenance(), product.getEnvironment(), product.getStock(), product.getPrice(), product.getSize()));
        setCategoryProducts(categoryProducts);
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<CategoryProduct> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(List<CategoryProduct> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }
}
