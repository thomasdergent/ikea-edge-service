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

    public String getCategory(){
        return category;
    }

    public String getArticleNumber(){
        return articleNumber;
    }

    public Boolean getDelivery(){
        return delivery;
    }

    public String getMaterial(){
        return material;
    }

    public String getMaintenance(){
        return maintenance;
    }

    public String getEnvironment(){
        return environment;
    }

    public int getStock(){
        return stock;
    }

    public double getPrice(){
        return price;
    }

    public String getSize(){
        return size;
    }
}
