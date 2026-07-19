package com.hospitalmanagement.hospital.management.api;

import com.hospitalmanagement.hospital.management.api.db.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementApiApplication {

	public static void main(String[] args) {
		Database.initializeSchema();
		SpringApplication.run(HospitalManagementApiApplication.class, args);
	}
}
