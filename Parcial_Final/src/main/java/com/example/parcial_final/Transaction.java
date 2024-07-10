package com.example.parcial_final;
import java.time.LocalDate;

public class Transaction {
    private LocalDate purchaseDate; //00106123 Declares purchaseDate attribute as LocalDate
    private double amount; //00106123 Declares amount attribute as double
    private String description; //00106123 Declares description attribute as String

    public Transaction(LocalDate purchaseDate, double amount, String description) {
        this.purchaseDate = purchaseDate; //00106123 Initializes purchaseDate
        this.amount = amount; //00106123Initializes amount
        this.description = description; //00106123 Initializes description
    }
//00106123getters and setters
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

