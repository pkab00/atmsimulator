package com.atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DTOTest {

    @Test
    void testAccountDTO() {
        AccountDTO dto = new AccountDTO()
            .setCardNumber("1234 5678 9012 3456")
            .setBalance(1000.0)
            .setWithdrawLimit(50000.0);
            
        assertEquals("1234 5678 9012 3456", dto.getCardNumber());
        assertEquals(1000.0, dto.getBalance());
        assertEquals(50000.0, dto.getWithdrawLimit());
    }

    @Test
    void testUserDTO() {
        byte[] pinHash = {1, 2, 3, 4};
        UserDTO dto = new UserDTO()
            .setPINhash(pinHash)
            .setSurname("Doe")
            .setName("John")
            .setFatherName("Michael");
            
        assertArrayEquals(pinHash, dto.getPINhash());
        assertEquals("Doe", dto.getSurname());
        assertEquals("John", dto.getName());
        assertEquals("Michael", dto.getFatherName());
    }

    @Test
    void testOperationDTO() {
        OperationDTO dto = new OperationDTO()
            .setFromCardNumber("1111 1111 1111 1111")
            .setToCardNumber("2222 2222 2222 2222")
            .setDateTime("2023-01-01 12:00:00")
            .setSum(1000.0)
            .setCommited(true);
            
        assertEquals("1111 1111 1111 1111", dto.getFromCardNumber());
        assertEquals("2222 2222 2222 2222", dto.getToCardNumber());
        assertEquals("2023-01-01 12:00:00", dto.getDateTime());
        assertEquals(1000.0, dto.getSum());
        assertTrue(dto.isCommited());
    }
}
