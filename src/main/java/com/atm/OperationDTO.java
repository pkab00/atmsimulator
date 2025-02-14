package com.atm;

public class OperationDTO implements iDTO {
    private Account fromAccount;
    private Account toAccount;
    private String dateTime;
    private double sum;

    public Account getFromAccount() {
        return fromAccount;
    }
    public OperationDTO setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
        return this;
    }
    public Account getToAccount() {
        return toAccount;
    }
    public OperationDTO setToAccount(Account toAccount) {
        this.toAccount = toAccount;
        return this;
    }
    public String getDateTime() {
        return dateTime;
    }
    public OperationDTO setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }
    public double getSum() {
        return sum;
    }
    public OperationDTO setSum(double sum) {
        this.sum = sum;
        return this;
    }
    
}
