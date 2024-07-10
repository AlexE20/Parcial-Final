package com.example.parcial_final;
import java.time.LocalDate;

public class Transaction {
    private int id;

    private int cardId;
    private LocalDate purchaseDate; //Declares purchaseDate attribute as LocalDate
    private double amount; //Declares amount attribute as double
    private String description; //Declares description attribute as String

    public Transaction(LocalDate purchaseDate, double amount, String description) {
        this.purchaseDate = purchaseDate; //Initializes purchaseDate
        this.amount = amount; //Initializes amount
        this.description = description; //Initializes description
    }

    public Transaction(int id, double moneyAmount, String description, int cardId, LocalDate transactionDate) {
        this.id = id;
        this.amount = moneyAmount;
        this.description = description;
        this.cardId = cardId;
        this.purchaseDate = transactionDate;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}

