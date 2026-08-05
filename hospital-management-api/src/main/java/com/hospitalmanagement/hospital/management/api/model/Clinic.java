package com.hospitalmanagement.hospital.management.api.model;

public class Clinic {

    private int id;
    private String name;
    private String address;
    private String subscriptionStatus;

    public Clinic() {}

    public Clinic(int id, String name, String address, String subscriptionStatus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subscriptionStatus = subscriptionStatus;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}