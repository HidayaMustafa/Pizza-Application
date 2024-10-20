package com.example.project.ui.Special_offers;

public class Offer {
    private String name;
    private int totalPrice;
    private String startDate;
    private String endDate;
    private String pizzaDetails;
    private byte[] img;

    public Offer() {

    }
    // Constructor
    public Offer(String name, int totalPrice, String startDate, String endDate, String pizzaDetails, byte[] img) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pizzaDetails = pizzaDetails;
        this.img = img;
    }
    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }

    // Getters and setters (optional, for accessing the fields)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPizzaDetails() {
        return pizzaDetails;
    }

    public void setPizzaDetails(String pizzaDetails) {
        this.pizzaDetails = pizzaDetails;
    }
}
