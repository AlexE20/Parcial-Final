package com.example.parcial_final;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public Client(int id, String firstName, String lastName, String direction, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = direction;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
