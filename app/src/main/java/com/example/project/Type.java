package com.example.project;
import java.io.Serializable;
public class Type implements Serializable {
    private String typeName;
    private String size;
    private String price;
    private String ingredients;
    private int image ;


    public Type() {
        this.typeName = typeName;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
        this.image = image;
    }

    public Type(String typeName, String size, String price, String ingredients, int image) {
        this.typeName = typeName;
        this.size = size;
        this.price = price;
        this.ingredients = ingredients;
        this.image=image;
    }

    // Getters and setters
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}