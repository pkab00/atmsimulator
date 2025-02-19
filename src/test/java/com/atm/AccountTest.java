package com.atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void testGetEmptyAccount() {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        assertNotNull(account);
        assertEquals("1234 5678 9012 3456", account.getCardNumber());
        assertEquals(0.0, account.getBalance());
        assertEquals(100000.0, account.getWithdrawLimit());
    }

    @Test
    void testGetExistingAccount() {
        AccountDTO dto = new AccountDTO()
            .setCardNumber("1234 5678 9012 3456")
            .setBalance(1000.0)
            .setWithdrawLimit(50000.0);
            
        Account account = Account.getExistingAccount(dto);
        assertNotNull(account);
        assertEquals("1234 5678 9012 3456", account.getCardNumber());
        assertEquals(1000.0, account.getBalance());
        assertEquals(50000.0, account.getWithdrawLimit());
    }

    @Test
    void testChangeBalance() {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        account.changeBalance(1000.0);
        assertEquals(1000.0, account.getBalance());
        account.changeBalance(-500.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void testToString() {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        String expected = String.format("[\n\t\tCard number: %s\n\t\tBalance: %.2f\n\t\tLimit: %.2f\n\t]",
            "1234 5678 9012 3456", 0.00, 100000.00);

        assertEquals(expected, account.toString());
    }
}
