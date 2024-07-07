package com.example.parcial_final;
import java.time.LocalDate;

public class Transaction {
    private LocalDate purchaseDate;
    private double amount;
    private String description;

    public Transaction(LocalDate purchaseDate, double amount, String description) {
        this.purchaseDate = purchaseDate;
        this.amount = amount;
        this.description = description;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

