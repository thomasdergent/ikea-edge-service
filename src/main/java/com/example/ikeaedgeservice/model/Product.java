package com.example.ikeaedgeservice.model;

public class Product {

    private int id;
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

    public Product() {
    }

    public Product(String name, String category, String description, String image, String articleNumber, Boolean delivery, String material, String maintenance, String environment, double price, String size) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.articleNumber = articleNumber;
        this.delivery = delivery;
        this.material = material;
        this.maintenance = maintenance;
        this.environment = environment;
        this.price = price;
        this.size = size;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getImage(){
        return image;
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
