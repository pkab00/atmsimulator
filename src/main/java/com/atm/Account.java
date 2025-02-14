package com.atm;

import java.security.InvalidParameterException;

public class Account {
    private String cardNumber;
    private double balance;
    private double withdrawLimit;

    private Account(String cardNumber){
        this.cardNumber = cardNumber;
        this.balance = 0f;
        this.withdrawLimit = 100000f;
    }

    private Account(String cardNumber, double balance, double withdrawLimit){
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    // Фабричный метод для создания пустого счёта
    public static Account getEmptyAccount(String cardNumber){
        return new Account(cardNumber);
    }

    // Фабричный метод для преобразования DAO в объект Account
    public static Account getExistingAccount(AccountDTO data){
        return new Account(data.getCardNumber(), data.getBalance(), data.getWithdrawLimit());
    }

    @Override
    public String toString(){
        return String.format("[\n\t\tCard number: %s\n\t\tBalance: %.2f\n\t\tLimit: %.2f\n\t]",
        cardNumber, balance, withdrawLimit);
    }

    public void changeBalance(double money) throws InvalidParameterException{
        balance += money;
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
