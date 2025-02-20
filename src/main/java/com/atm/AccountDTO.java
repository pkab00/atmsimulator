package com.atm;

public class AccountDTO implements iDTO{
    private String cardNumber;
    private double balance;
    private double withdrawLimit;

    public AccountDTO() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public AccountDTO setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public AccountDTO setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public AccountDTO setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
        return this;
    }

    @Override
    public String getFileName() {
        return "_account.ser";
    }
}
