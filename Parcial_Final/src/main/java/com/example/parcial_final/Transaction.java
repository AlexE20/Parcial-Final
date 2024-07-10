package com.example.parcial_final;
import java.time.LocalDate;

public class Transaction {
    private int id;

    private int cardId;
    private LocalDate purchaseDate; //00106123 Declares purchaseDate attribute as LocalDate 
    private double amount; //00106123 Declares amount attribute as double
    private String description; //00106123 Declares description attribute as String

    public Transaction(LocalDate purchaseDate, double amount, String description) {
        this.purchaseDate = purchaseDate; //00106123 Initializes purchaseDate
        this.amount = amount; //00106123 Initializes amount
        this.description = description; //00106123 Initializes description
    }

    public Transaction(int id, double moneyAmount, String description, int cardId, LocalDate transactionDate) { //00106123 The constructor of the class
        this.id = id; //00106123 Initialize the id
        this.amount = moneyAmount; //00106123 Initialize the moneyAmount
        this.description = description; //00106123 Initialize the description
        this.cardId = cardId; //00106123 Initialize the Card ID
        this.purchaseDate = transactionDate; //00106123 Initialize the purchase Date
    }

    //00106123 getters and setters
    public LocalDate getPurchaseDate() { //00106123 Getter for purchaseDate
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) { //00106123 Setter for purchaseDate
        this.purchaseDate = purchaseDate;
    }

    public double getAmount() { //00106123 Getter for amount
        return amount;
    }

    public void setAmount(double amount) { //00106123 Setter for amount
        this.amount = amount;
    }

    public String getDescription() { //00106123 Getter for description
        return description;
    }

    public void setDescription(String description) { //00106123 Setter for description
        this.description = description;
    }

    public int getId() { //00106123 gets the ID of the Table
        return id;
    }

    public void setId(int id) { //00106123 sets the ID of the Table
        this.id = id;
    }

    public int getCardId() { //00106123 gets the ID of the Card ID
        return cardId;
    }

    public void setCardId(int cardId) { //00106123 sets the ID of the Card ID
        this.cardId = cardId;
    }
}

