package com.example.parcial_final;

public class ClientTransaction { //00005923 This is a Data Transfer Object, used to generate specific tables.
    private final String firstName; //00005923 to save the name
    private final String lastName; //00005923 to save the Lastname
    private final int purchaseCount; //00005923to save to save de purchaseCount
    private final double totalSpent; //00005923 to save the total spent

    public ClientTransaction(String firstName, String lastName, int purchaseCount, double totalSpent) { //The constructor
        this.firstName = firstName; //00005923 initialize name
        this.lastName = lastName; //00005923 initialize lastname
        this.purchaseCount = purchaseCount; //00005923 initialize purchase count
        this.totalSpent = totalSpent; //00005923 initialize total spent
    }

    //00005923 setters and getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}