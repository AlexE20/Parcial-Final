package com.example.parcial_final;

public class ClientTransaction {
    private final String firstName;
    private final String lastName;
    private final int purchaseCount;
    private final double totalSpent;

    public ClientTransaction(String firstName, String lastName, int purchaseCount, double totalSpent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchaseCount = purchaseCount;
        this.totalSpent = totalSpent;
    }

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
