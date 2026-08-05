package com.hospitalmanagement.hospital.management.api.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Patient {

    private int id;

    @NotBlank(message = "Emri dhe mbiemri i pacientit nuk mund të jenë bosh")
    @Size(min = 2, max = 50, message = "Emri duhet të jetë midis 2 dhe 50 karaktereve")
    private String name;

    @Min(value = 0, message = "Mosha nuk mund të jetë negative")
    @Max(value = 120, message = "Mosha e vendosur nuk është me vend")
    private int age;

    @NotBlank(message = "Gjinia është fushë e detyrueshme")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gjinia duhet të jetë Male, Female ose Other")
    private String gender;

    @NotBlank(message = "Numri i telefonit është i detyrueshëm")
    @Pattern(regexp = "^[0-9+\\-\\s]{8,15}$", message = "Numri i telefonit nuk është në format të saktë")
    private String phone;

    @NotBlank(message = "Adresa është fushë e detyrueshme")
    private String address;

    @NotBlank(message = "Sëmundja / Diagnoza është fushë e detyrueshme")
    private String disease;

    @NotBlank(message = "Data e pranimit është fushë e detyrueshme")
    private String admitDate;

    private String username;

    public Patient() {
    }

    public Patient(int id, String name, int age, String gender, String phone,
                   String address, String disease, String admitDate, String username) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
        this.admitDate = admitDate;
        this.username = username;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getDisease() { return disease; }
    public String getAdmitDate() { return admitDate; }
    public String getUsername() { return username; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setAdmitDate(String admitDate) { this.admitDate = admitDate; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return String.format("ID:%-4d | User:%-10s | %-20s | Age:%-3d | %-6s | %-12s | %-15s | %-15s | Admitted:%s",
                id, username, name, age, gender, phone, address, disease, admitDate);
    }
}