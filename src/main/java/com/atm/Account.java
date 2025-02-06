package com.atm;

public class Account {
    private String cardNumber;
    private double balance;
    private double withdrawLimit;

    // Дефолтный конструтор для создания пустого счёта
    public Account(String cardNumber){
        this.cardNumber = cardNumber;
        this.balance = 0f;
        this.withdrawLimit = 100000f;
    }

    public void increaseBalance(double money){
        balance += money;
    }

    public void decreaseBalance(double money){
        if(money >= balance && money < withdrawLimit){
            balance -= money;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double money) {
        this.balance = money;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }
}
