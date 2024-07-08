package com.example.parcial_final;
import java.time.LocalDate;

public class Transaction {
    private LocalDate purchaseDate; //Declares purchaseDate attribute as LocalDate
    private double amount; //Declares amount attribute as double
    private String description; //Declares description attribute as String

    public Transaction(LocalDate purchaseDate, double amount, String description) {
        this.purchaseDate = purchaseDate; //Initializes purchaseDate
        this.amount = amount; //Initializes amount
        this.description = description; //Initializes description
    }

    public LocalDate getPurchaseDate() { //Getter for purchaseDate
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) { //Setter for purchaseDate
        this.purchaseDate = purchaseDate;
    }

    public double getAmount() { //Getter for amount
        return amount;
    }

    public void setAmount(double amount) { //Setter for amount
        this.amount = amount;
    }

    public String getDescription() { //Getter for description
        return description;
    }

    public void setDescription(String description) { //Setter for description
        this.description = description;
    }
}

