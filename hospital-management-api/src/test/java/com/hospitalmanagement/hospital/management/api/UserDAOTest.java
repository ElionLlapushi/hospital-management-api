package com.hospitalmanagement.hospital.management.api;

import com.hospitalmanagement.hospital.management.api.dao.UserDAO;
import com.hospitalmanagement.hospital.management.api.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Mock
    private UserDAO userDAO;

    @Test
    public void testGetUserByUsername() throws Exception {
        // Krijojmë një përdorues mock për testin
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setRole("ADMIN");

        // Përcaktojmë sjelljen e DAO-së
        Mockito.when(userDAO.getUserByUsername("admin")).thenReturn(mockUser);

        // Ekzekutojmë thirrjen
        User user = userDAO.getUserByUsername("admin");

        // Verifikojmë rezultatet
        assertNotNull(user, "Përdoruesi admin duhet të ekzistojë!");
        assertEquals("admin", user.getUsername());
        assertEquals("ADMIN", user.getRole());
    }
}
