package com.atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

class CommonDAOTest {

    @BeforeEach
    void setUp() {
        // Clean database using SQLite API directly
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db")) {
            conn.createStatement().execute("DELETE FROM USERS");
            conn.createStatement().execute("DELETE FROM ACCOUNTS");
            conn.createStatement().execute("DELETE FROM OPERATIONS");
        } catch (SQLException e) {
            fail("Failed to clean database: " + e.getMessage());
        }
    }

    @Test
    void testAddNewUser() {
        String cardNumber = "1234 5678 9012 3456";
        CommonDAO.addNewUser(cardNumber, "1234", "Doe", "John", "Michael");
        
        ArrayList<iDTO> users = CommonDAO.requestData(cardNumber, REQUEST_TYPE.USERS);
        assertEquals(1, users.size());
        
        UserDTO user = (UserDTO)users.get(0);
        assertEquals("Doe", user.getSurname());
        assertEquals("John", user.getName());
        assertEquals("Michael", user.getFatherName());
    }

    @Test
    void testUpdateBalance() {
        String cardNumber = "1234 5678 9012 3456";
        CommonDAO.addNewUser(cardNumber, "1234", "Doe", "John", "Michael");
        
        Account account = Account.getExistingAccount(cardNumber);
        account.setBalance(1000.0);
        CommonDAO.updateBalance(account);
        
        ArrayList<iDTO> accounts = CommonDAO.requestData(cardNumber, REQUEST_TYPE.ACCOUNTS);
        AccountDTO updatedAccount = (AccountDTO)accounts.get(0);
        assertEquals(1000.0, updatedAccount.getBalance());
    }

    @Test
    void testAddOperation() {
        String cardNumber = "1234 5678 9012 3456";
        CommonDAO.addNewUser(cardNumber, "1234", "Doe", "John", "Michael");
        
        CommonDAO.addOperation("2023-01-01 12:00:00", cardNumber, null, 1000.0);
        
        ArrayList<iDTO> operations = CommonDAO.requestData(cardNumber, REQUEST_TYPE.OPERATIONS);
        assertEquals(1, operations.size());
        
        OperationDTO operation = (OperationDTO)operations.get(0);
        assertEquals(cardNumber, operation.getToCardNumber());
        assertEquals(1000.0, operation.getSum());
    }

    @Test
    void testGenerateCardNumber() {
        String cardNumber = CommonDAO.generateCardNumber();
        assertNotNull(cardNumber);
        assertEquals(19, cardNumber.length()); // 16 digits + 3 spaces
    }
}
