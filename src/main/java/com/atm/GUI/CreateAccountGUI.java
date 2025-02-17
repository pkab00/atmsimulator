package com.atm.GUI;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import com.atm.User;

public class CreateAccountGUI extends UserInputGUI {
    public CreateAccountGUI(){
        super("Регистрация");
        composeUI();
    }

    @Override
    protected void composeUI() {
        addUpcaseInputPanel("ФАМИЛИЯ", "IVANOV");
        addUpcaseInputPanel("ИМЯ", "IVAN");
        addUpcaseInputPanel("ОТЧЕСТВО", "IVANOVICH");
        addFixatedInputPanel("НОМЕР КАРТЫ");
        addFormattedInputPanel("PIN", "####", "0000");

        JButton loginButton = new JButton("Зарегистрироваться");
        loginButton.addActionListener((e) -> {
            new DBAccessWorker().execute();
        });
        corePane.add(loginButton);
    }
    private class DBAccessWorker extends SwingWorker<User, Void>{
        @Override
        protected User doInBackground() throws Exception {
            ArrayList<Object> data = getUserInput();
            String surname = (String)data.get(0);
            String name = (String)data.get(1);
            String fatherName = (String)data.get(2);
            String cardNumber = (String)data.get(3);
            String PIN = (String)data.get(4);
            User user = User.getNewUser(cardNumber, PIN, surname, name, fatherName);
            return user;
        }
        @Override
        protected void done(){
            try {
                User newUser = get();
                CreateAccountGUI.this.dispose();
                new ATMSessionGUI(newUser);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
