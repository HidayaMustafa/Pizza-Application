package com.example.project.ui.add_special_offers;
import java.io.Serializable;

public class PizzaDetails implements Serializable {
    private String size;
    private int quantity;

    public PizzaDetails(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
