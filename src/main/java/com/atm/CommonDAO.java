package com.atm;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

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

    public static void addNewUser(String cardNumber, String PIN, String surname, String name, String fatherName){
        Connection conn = connect();
        String stat1 = "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
        String stat2 = "INSERT INTO ACCOUNTS VALUES (?, 0.0, 100000.0)";
        try {
            PreparedStatement prepStat1 = conn.prepareStatement(stat1);
            prepStat1.setString(1, cardNumber);
            prepStat1.setBytes(2, HashHandler.hash(PIN));
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
                        .setPINhash(res.getBytes("PIN_HASH"))
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
    }

    public static String generateCardNumber(){
        String cardNumber = "";
        ArrayList<String> existingNumbers = getCardNumbers();
        Random gen = new Random();
        do{
            for(int i = 0; i < 4; i++){
                String part = "";
                for(int j = 0; j < 4; j++){
                    part += gen.nextInt(10);
                }
                cardNumber += (part + " ");
            }
            cardNumber = cardNumber.substring(0, cardNumber.length()-1);
        } while(existingNumbers.contains(cardNumber));
        return cardNumber;
    }

    // Вспомогательный метод, получает существующие номера карт
    private static ArrayList<String> getCardNumbers(){
        ResultSet res = null;
        ArrayList<String> output = new ArrayList<>();
        String sql = "SELECT CARD_NUMBER FROM USERS";
        try(Connection conn = connect()){
            PreparedStatement statement = conn.prepareStatement(sql);
            res = statement.executeQuery();
            while (res.next()) {
                output.add(res.getString("CARD_NUMBER"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return output;
    }
}