package com.example.parcial_final;

//Clase para transferir datos entre subsistemas de aplicaciones de software y para que la complejidad del codigo sea menor y mas legible.
public class ReportBDto {
    private String clientName;//Atributo del nombre del cliente
    private double amountOfMoney;//Atributo del total de dinero

    public ReportBDto(String clientName, double amountOfMoney) {//Constructor
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