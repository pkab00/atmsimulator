package com.atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void testDepositOperation() throws Operation.InvalidOperationException {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        Operation operation = new Operation(account, 1000.0);
        
        assertEquals(account, operation.getToAccount());
        assertNull(operation.getFromAccount());
        assertEquals(1000.0, operation.getSum());
        assertFalse(operation.isCommited());
    }

    @Test
    void testWithdrawalOperation() throws Operation.InvalidOperationException {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        account.changeBalance(1000.0);
        Operation operation = new Operation(account, -500.0);
        
        assertEquals(account, operation.getToAccount());
        assertNull(operation.getFromAccount());
        assertEquals(-500.0, operation.getSum());
        assertFalse(operation.isCommited());
    }

    @Test
    void testTransferOperation() throws Operation.InvalidOperationException {
        Account fromAccount = Account.getEmptyAccount("1111 1111 1111 1111");
        fromAccount.changeBalance(1000.0);
        Account toAccount = Account.getEmptyAccount("2222 2222 2222 2222");
        
        Operation operation = new Operation(toAccount, fromAccount, 500.0);
        
        assertEquals(toAccount, operation.getToAccount());
        assertEquals(fromAccount, operation.getFromAccount());
        assertEquals(500.0, operation.getSum());
        assertFalse(operation.isCommited());
    }

    @Test
    void testInvalidOperation() {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        
        assertThrows(Operation.InvalidOperationException.class, () -> {
            new Operation(account, -1000.0);
        });
    }

    @Test
    void testCommitOperation() throws Operation.InvalidOperationException {
        Account account = Account.getEmptyAccount("1234 5678 9012 3456");
        Operation operation = new Operation(account, 1000.0);
        
        operation.commit();
        
        assertTrue(operation.isCommited());
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    void testToString() throws Operation.InvalidOperationException {
        // Test deposit operation
        Account toAccount = Account.getEmptyAccount("2222 2222 2222 2222");
        Operation deposit = new Operation(toAccount, 1000.0);
        assertEquals(String.format("Зачисление %.2f руб. на %s", 1000.00, "2222 2222 2222 2222"), deposit.toString());


        // Test withdrawal operation
        Account withdrawAccount = Account.getEmptyAccount("3333 3333 3333 3333");
        withdrawAccount.changeBalance(2000.0);
        Operation withdrawal = new Operation(withdrawAccount, -500.0);
        assertEquals(String.format("Списание %.2f руб. с %s", -500.00, "3333 3333 3333 3333"), withdrawal.toString());


        // Test transfer operation
        Account fromAccount = Account.getEmptyAccount("1111 1111 1111 1111");
        fromAccount.changeBalance(1500.0);
        Operation transfer = new Operation(toAccount, fromAccount, 500.0);
        assertEquals(String.format("Перевод %.2f руб. с %s на %s", 500.00, "1111 1111 1111 1111", "2222 2222 2222 2222"), transfer.toString());

    }

}
