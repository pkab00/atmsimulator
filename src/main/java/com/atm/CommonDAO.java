package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

enum REQUEST_TYPE{
    USERS,
    ACCOUNTS
}

public class CommonDAO {
    private static final String DB_URL = "jdbc:sqlite:src\\main\\resources\\database.db";

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static boolean userExists(String cardNumber){
        Connection conn = connect();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM USERS WHERE CARD_NUMBER = ?");
            prepStat.setString(1, cardNumber);
            ResultSet set = prepStat.executeQuery();
            if(set.next()){
                return true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addNewUser(String cardNumber, String PIN, String surname, String name, String fatherName){
        Connection conn = connect();
        String stat1 = "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
        String stat2 = "INSERT INTO ACCOUNTS VALUES (?, 0.0, 100000.0)";
        // Заменить эту строку на вызов метода хэширования
        String PINhash = String.valueOf(PIN);
        try {
            PreparedStatement prepStat1 = conn.prepareStatement(stat1);
            prepStat1.setString(1, cardNumber);
            prepStat1.setString(2, PINhash);
            prepStat1.setString(3, surname);
            prepStat1.setString(4, name);
            prepStat1.setString(5, fatherName);
            PreparedStatement prepStat2 = conn.prepareStatement(stat2);
            prepStat2.setString(1, cardNumber);
            prepStat1.executeUpdate();
            prepStat2.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static iDTO requestData(String cardNumber, REQUEST_TYPE type){
        ResultSet res = null;
        iDTO outputDTO = null;
        String sql = "SELECT * FROM " + type.toString() + " WHERE CARD_NUMBER = ?";
        try(Connection conn = connect()) {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, cardNumber);
            res = prep.executeQuery();
            if(res.next()){
                switch(type){
                    case ACCOUNTS:
                        outputDTO = new AccountDTO()
                        .setCardNumber(res.getString("CARD_NUMBER"))
                        .setBalance(res.getDouble("BALANCE"))
                        .setWithdrawLimit(res.getDouble("LIMIT"));
                        break;
                    case USERS:
                        outputDTO = new UserDTO()
                        // добавить дехэширование
                        .setPIN(res.getString("PIN_HASH"))
                        .setSurname(res.getString("SURNAME"))
                        .setName(res.getString("NAME"))
                        .setFatherName(res.getString("FATHER_NAME"));
                        break;
                }
            }
            else{
                res = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputDTO;
        // TODO: убедиться, что res закрывается после использования
        // TODO: внедрить хэширование/дехэширование PIN
    }

    public static void main(String[] args) {
        //User pushkin = User.getExistingUser("4444", "12345");
        //System.out.println(pushkin);

        //User me = User.getExistingUser("5555", "5555");
        //System.out.println(me);

        User oleg = User.getNewUser("666666", "9999", 
        "Sheps", "Oleg", "Nikolayevich");
        System.out.println(oleg);
        oleg = User.getExistingUser("9999", "666666");
        System.out.println(oleg);
    }
}