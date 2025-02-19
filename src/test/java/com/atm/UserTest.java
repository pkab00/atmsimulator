package com.atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGetNewUser() {
        User user = User.getNewUser("1234 5678 9012 3456", "1234", "Doe", "John", "Michael");
        assertNotNull(user);
        assertEquals("Doe", user.getSurname());
        assertEquals("John", user.getName());
        assertEquals("Michael", user.getFatherName());
        assertNotNull(user.getAccount());
    }

    @Test
    void testGetExistingUser() {
        // Setup test data
        String cardNumber = "1234 5678 9012 3456";
        String PIN = "1234";
        String surname = "Doe";
        String name = "John";
        String fatherName = "Michael";
        
        // Create test user
        User.getNewUser(cardNumber, PIN, surname, name, fatherName);
        
        // Test retrieval
        User user = User.getExistingUser(PIN, cardNumber);
        assertNotNull(user);
        assertEquals("Doe", user.getSurname());
        assertEquals("John", user.getName());
        assertEquals("Michael", user.getFatherName());
        assertNotNull(user.getAccount());
    }

    @Test
    void testGetFullName() {
        User user = User.getNewUser("1234 5678 9012 3456", "1234", "Doe", "John", "Michael");
        assertEquals("Doe John Michael", user.getFullName());
    }

    @Test
    void testToString() {
        User user = User.getNewUser("1234 5678 9012 3456", "1234", "Doe", "John", "Michael");
        String expected = String.format("{\n\tSurname: %s\n\tName: %s\n\tFather name: %s\n\tAccount: %s\n}",
            "Doe", "John", "Michael", 
            String.format("[\n\t\tCard number: %s\n\t\tBalance: %.2f\n\t\tLimit: %.2f\n\t]", 
                "1234 5678 9012 3456", 0.00, 100000.00));


        assertEquals(expected, user.toString());
    }
}
