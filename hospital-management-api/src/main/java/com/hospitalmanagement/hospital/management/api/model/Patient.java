package com.hospitalmanagement.hospital.management.api.model;

import jakarta.validation.constraints.*;

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

    // Fushat e reja klinike
    private String email;
    private String bloodGroup;
    private String allergies;
    private String emergencyContact;
    private String emergencyPhone;
    private String dateOfBirth;
    private String medicalHistory;
    private String insuranceNumber;
    private String occupation;
    private double weight;
    private double height;
    private String status; // Active, Discharged
    private String photoUrl;

    public Patient() {}

    public Patient(int id, String name, int age, String gender, String phone,
                   String address, String disease, String admitDate, String username,
                   String email, String bloodGroup, String allergies, String emergencyContact,
                   String emergencyPhone, String dateOfBirth, String medicalHistory,
                   String insuranceNumber, String occupation, double weight, double height,
                   String status, String photoUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
        this.admitDate = admitDate;
        this.username = username;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.emergencyPhone = emergencyPhone;
        this.dateOfBirth = dateOfBirth;
        this.medicalHistory = medicalHistory;
        this.insuranceNumber = insuranceNumber;
        this.occupation = occupation;
        this.weight = weight;
        this.height = height;
        this.status = status;
        this.photoUrl = photoUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getDisease() { return disease; }
    public String getAdmitDate() { return admitDate; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBloodGroup() { return bloodGroup; }
    public String getAllergies() { return allergies; }
    public String getEmergencyContact() { return emergencyContact; }
    public String getEmergencyPhone() { return emergencyPhone; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getMedicalHistory() { return medicalHistory; }
    public String getInsuranceNumber() { return insuranceNumber; }
    public String getOccupation() { return occupation; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public String getStatus() { return status; }
    public String getPhotoUrl() { return photoUrl; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setAdmitDate(String admitDate) { this.admitDate = admitDate; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public void setInsuranceNumber(String insuranceNumber) { this.insuranceNumber = insuranceNumber; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setStatus(String status) { this.status = status; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}