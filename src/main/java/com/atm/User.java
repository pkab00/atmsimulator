package com.atm;

public class User {
    private String surname;
    private String name;
    private String fatherName;
    private Account account;

    private User(String surname, String name, String fatherName, String cardNumber){
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.account = Account.getEmptyAccount(cardNumber);
    }

    private User(String surname, String name, String fatherName, AccountDTO accountData){
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.account = Account.getExistingAccount(accountData);
    }

    // Фабричный метод, регистрирует нового пользователя в базе и возвращает объект User
    public static User getNewUser(String cardNumber, String PIN, String surname, String name, String fatherName){
        CommonDAO.addNewUser(cardNumber, PIN, surname, name, fatherName);
        return new User(surname, name, fatherName, cardNumber);
    }

    // Фабричный метод для преобразования DTO в User
    // Принимает пин-код и номер карты, осуществляет запрос к БД через DAO
    // При несоответствии данных возвращает null
    public static User getExistingUser(String PIN, String cardNumber){
        UserDTO userData = (UserDTO)CommonDAO.requestData(cardNumber, REQUEST_TYPE.USERS).get(0);
        AccountDTO accountData = (AccountDTO)CommonDAO.requestData(cardNumber, REQUEST_TYPE.ACCOUNTS).get(0);

        if(userData == null || accountData == null) return null;
        if(!HashHandler.compare(PIN, userData.getPINhash())){
            return null;
        }
        User newUser = new User(userData.getSurname(), userData.getName(), userData.getFatherName(), accountData);
        return newUser;
    }

    @Override
    public String toString(){
        return String.format("{\n\tSurname: %s\n\tName: %s\n\tFather name: %s\n\tAccount: %s\n}",
        surname, name, fatherName, account.toString());
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getFullName(){
        return String.format("%s %s %s", surname, name, fatherName);
    }

    public Account getAccount() {
        return account;
    }
}
