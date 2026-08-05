package com.hospitalmanagement.hospital.management.api.dao;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WhatsAppService {

    public String generateWhatsAppLink(String phone, String patientName, String admitDate) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }

        // Pastron numrin nga hapësirat, vizat apo simbolet
        String cleanPhone = phone.replaceAll("[^0-9]", "");

        String message = "Përshëndetje " + patientName + ", ju kujtojmë vizitën tuaj të planifikuar për datën: " + admitDate + ". Ju presim te spitali ynë!";

        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            return "https://wa.me/" + cleanPhone + "?text=" + encodedMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}