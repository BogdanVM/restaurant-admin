package com.gr232.restaurantadmin.models;

public class Dish {
    private String name;
    private String ingredients;
    private Integer weight;
    private Double price;

    public Dish(String name, String ingredients, Integer weight, Double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
