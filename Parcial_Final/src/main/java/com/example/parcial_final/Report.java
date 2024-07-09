package com.example.parcial_final;


public class Report {
    private String clientName;//Atributo del nombre del cliente
    private double amountOfMoney;//Atributo del total de dinero

    public Report(String clientName, double amountOfMoney) {//Constructor
        this.clientName = clientName;// Asigna el nombre del cliente
        this.amountOfMoney = amountOfMoney;//Asigna la cantidad de dinero del cliente
    }

    //Setter y Getters
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
}