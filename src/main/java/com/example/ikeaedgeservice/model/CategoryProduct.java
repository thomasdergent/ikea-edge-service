package com.example.ikeaedgeservice.model;

public class CategoryProduct {

    private String name;
    private String category;
    private String articleNumber;
    private Boolean delivery;
    private String material;
    private String maintenance;
    private String environment;
    private int stock;
    private double price;
    private String size;

    public CategoryProduct() {
    }

    public CategoryProduct(String name, String category, String articleNumber, Boolean delivery, String material, String maintenance, String environment, int stock, double price, String size) {
        this.name = name;
        this.category = category;
        this.articleNumber = articleNumber;
        this.delivery = delivery;
        this.material = material;
        this.maintenance = maintenance;
        this.environment = environment;
        this.stock =stock;
        this.price = price;
        this.size = size;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArticleNumber(){
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Boolean getDelivery(){
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public String getMaterial(){
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaintenance(){
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getEnvironment(){
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
