package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src\\main\\resources\\database.db";
    

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static boolean userExists(String cardNumber){
        Connection conn = connect();
        try {
            ResultSet set = conn.prepareStatement(String.format("SELECT * FROM USERS "
                                                 +"WHERE CARD_NUMBER = %S", cardNumber)).executeQuery();
            if(set.next()){
                return true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(userExists("222"));
    }
}