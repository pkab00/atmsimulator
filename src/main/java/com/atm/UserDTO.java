package com.atm;

public class UserDTO implements iDTO {
    private byte[] PINhash;
    private String surname;
    private String name;
    private String fatherName;
    // UserDTO хранит только данные, уникальные для User
    // Информация о банковском счёте хранится AccountDTO

    public UserDTO(){}

    public byte[] getPINhash() {
        return PINhash;
    }

    public UserDTO setPINhash(byte[] PIN) {
        this.PINhash = PIN;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserDTO setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getFatherName() {
        return fatherName;
    }

    public UserDTO setFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    @Override
    public String getFileName() {
        return "_user.ser";
    }
}
