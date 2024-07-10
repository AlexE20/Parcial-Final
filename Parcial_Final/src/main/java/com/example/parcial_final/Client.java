package com.example.parcial_final;

public class Client {
    private int id; // 00009423 ID del cliente
    private String firstName; // 00009423 Nombre del cliente
    private String lastName; // 00009423 Apellido del cliente
    private String address; // 00009423 Dirección del cliente
    private String phone; // 00009423 Teléfono del cliente

    public Client(int id, String firstName, String lastName, String direction, String phone) {
        this.id = id; // 00009423 Inicializa el ID del cliente
        this.firstName = firstName; // 00009423 Inicializa el nombre del cliente
        this.lastName = lastName; // 00009423 Inicializa el apellido del cliente
        this.address = direction; // 00009423 Inicializa la dirección del cliente
        this.phone = phone; // 00009423 Inicializa el teléfono del cliente
    }

    // Getters and setters
    public int getId() {
        return id; // 00009423 Obtiene el ID del cliente
    }

    public String getFirstName() {
        return firstName; // 00009423 Obtiene el nombre del cliente
    }

    public String getLastName() {
        return lastName; // 00009423 Obtiene el apellido del cliente
    }

    public String getAddress() {
        return address; // 00009423 Obtiene la dirección del cliente
    }

    public String getPhone() {
        return phone; // 00009423 Obtiene el teléfono del cliente
    }
}
