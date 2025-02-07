package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

enum REQUEST_TYPE{
    USERS,
    ACCOUNTS
}

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src\\main\\resources\\database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static boolean userExists(String cardNumber){
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

    public static void addNewUser(String cardNumber, int PIN, String surname, String name, String fatherName){
        Connection conn = connect();
        String stat1 = "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
        String stat2 = "INSERT INTO ACCOUNTS VALUES (?, 0.0, 100000.0)";
        try {
            PreparedStatement prepStat1 = conn.prepareStatement(stat1);
            prepStat1.setString(1, cardNumber);
            prepStat1.setInt(2, PIN);
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

    public static ResultSet requestData(String cardNumber, REQUEST_TYPE type){
        if(!userExists(cardNumber)){
            return null;
        }
        ResultSet res = null;
        Connection conn = connect();
        String sql = "SELECT * FROM ? WHERE CARD_NUMBER = ?";
        try {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, type.toString());
            prep.setString(2, cardNumber);
            res = prep.executeQuery();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        User us = new User("Bushukin", "Vadim", "Dmitrievich", "222");
        System.out.println(us);
    }
}