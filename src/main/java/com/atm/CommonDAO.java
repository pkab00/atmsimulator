package com.atm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

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

    @SuppressWarnings("unchecked")
    public static <T extends iDTO> T deserealizeDTO(T obj){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(obj.getFullFileName()))) {
            T finalDTO = (T)objectInputStream.readObject();
            return finalDTO;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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

    public static void updateBalance(Account acc){
        String sql = "UPDATE ACCOUNTS SET BALANCE = ? WHERE CARD_NUMBER = ?";
        try(Connection conn = connect()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, acc.getBalance());
            statement.setString(2, acc.getCardNumber());
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void addOperation(String dateTime, String toUser, String fromUser, double sum){
        String sql = "INSERT INTO OPERATIONS VALUES (?, ?, ?, ?)";
        try (Connection conn = connect()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, dateTime);
            statement.setString(2, toUser);
            statement.setString(3, fromUser);
            statement.setDouble(4, sum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<iDTO> requestData(String cardNumber, REQUEST_TYPE type){
        ResultSet res = null;
        iDTO outputDTO = null;
        ArrayList<iDTO> outputList = new ArrayList<>();
        String sql;
        if(type != REQUEST_TYPE.OPERATIONS){
            sql = String.format("SELECT * FROM %s WHERE CARD_NUMBER = \"%s\"", type.toString(), cardNumber);
        } else{
            sql = String.format("SELECT * FROM OPERATIONS WHERE TO_USER = \"%s\" OR FROM_USER = \"%s\"", cardNumber, cardNumber);
        }
        try(Connection conn = connect()) {
            PreparedStatement prep = conn.prepareStatement(sql);
            res = prep.executeQuery();
            while(res.next()){
                switch(type){
                    case ACCOUNTS:
                        outputDTO = new AccountDTO()
                        .setCardNumber(res.getString("CARD_NUMBER"))
                        .setBalance(res.getDouble("BALANCE"))
                        .setWithdrawLimit(res.getDouble("LIMIT"));
                        outputList.add((AccountDTO)outputDTO);
                        break;
                    case USERS:
                        outputDTO = new UserDTO()
                        .setPINhash(res.getBytes("PIN_HASH"))
                        .setSurname(res.getString("SURNAME"))
                        .setName(res.getString("NAME"))
                        .setFatherName(res.getString("FATHER_NAME"));
                        outputList.add((UserDTO)outputDTO);
                        break;
                    case OPERATIONS:
                        outputDTO = new OperationDTO()
                        .setDateTime(res.getString("DATE_TIME"))
                        .setToCardNumber(res.getString("TO_USER"))
                        .setFromCardNumber(res.getString("FROM_USER"))
                        .setSum(res.getDouble("SUM"))
                        .setCommited(true);
                        outputList.add((OperationDTO)outputDTO);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputList;
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