package com.atm;

public class User {
    private String surname;
    private String firstName;
    private String fatherName;
    private Account account;

    public User(String surname, String firstName, String fatherName, String cardNumber){
        this.surname = surname;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.account = new Account(cardNumber);
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getFullName(){
        return String.format("%s %s %s", surname, firstName, fatherName);
    }

    public Account getAccount() {
        return account;
    }
}
