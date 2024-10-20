package com.example.project;

import android.provider.ContactsContract;

public class Order {
    private String customerEmail;
    private String pizzaName;
    private long orderTime;
    private String data;
    private String sizeQuantity;
    private int totalPrice;
    private byte [] Image;
    private int isOffer;

    // Default constructor
    public Order() {
    }
    // Parameterized constructor
    public Order(String customerEmail, String pizzaName, long orderTime, String sizeQuantity, int totalPrice, String data,byte [] Image, int isOffer) {
        this.customerEmail = customerEmail;
        this.pizzaName = pizzaName;
        this.orderTime = orderTime;
        this.sizeQuantity = sizeQuantity;
        this.totalPrice = totalPrice;
        this.data = data;
        this.Image = Image;
        this.isOffer = isOffer;
    }

    // Getter and setter methods
    public int getIsOffer() {
        return isOffer;
    }
    public void setIsOffer(int isOffer) {
        this.isOffer = isOffer;
    }
    public byte[] getImage() {
        return Image;
    }
    public void setImage(byte[] image) {
        Image = image;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getSizeQuantity() {
        return sizeQuantity;
    }

    public void setSizeQuantity(String sizeQuantity) {
        this.sizeQuantity = sizeQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
