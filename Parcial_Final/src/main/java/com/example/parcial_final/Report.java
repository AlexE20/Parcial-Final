package com.example.parcial_final;

public class Report {
    private String clientName;//00009423 Atributo del nombre del cliente
    private double amountOfMoney;// 00009423 Atributo del total de dinero

    public Report(String clientName, double amountOfMoney) {//Constructor
        this.clientName = clientName;//00009423 Asigna el nombre del cliente
        this.amountOfMoney = amountOfMoney;//00009423 Asigna la cantidad de dinero del cliente
    }

    //00009423 Setter y Getters
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
