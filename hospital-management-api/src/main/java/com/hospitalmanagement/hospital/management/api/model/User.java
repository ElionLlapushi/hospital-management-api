package com.hospitalmanagement.hospital.management.api.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int clinicId; // Fusha e re për lidhjen me klinikën

    public User() {}

    public User(int id, String username, String password, String role, int clinicId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.clinicId = clinicId;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public int getClinicId() { return clinicId; } // Getter për clinicId

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setClinicId(int clinicId) { this.clinicId = clinicId; } // Setter për clinicId
}