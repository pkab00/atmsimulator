package com.atm.GUI;

import java.awt.*;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import com.atm.User;

public class LoginGUI extends AutorizationGUI {
    public LoginGUI(){
        super("Вход");
        compainUI();
    }

    private void compainUI(){
        addInputPanel("НОМЕР КАРТЫ", 
        "#### #### #### ####", "0000 0000 0000 0000");
        addInputPanel("PIN КОД", "####", "0000");

        JButton submitButton = new JButton("Войти");
        submitButton.addActionListener(e -> {
            new DBAccessWorker().execute();
        });
        corePane.add(submitButton);

        JLabel noAccountLabel = new JLabel("Ещё нет акаунта?");
        noAccountLabel.setFont(coreFont.deriveFont(16f));
        corePane.add(Box.createHorizontalStrut(1));
        corePane.add(noAccountLabel);

        JButton goToRegistrationButton = new JButton("Регистрация");
        goToRegistrationButton.addActionListener((e) -> {
            // добавить переход к окну регистрации
        });
        corePane.add(goToRegistrationButton);
    }

    private class DBAccessWorker extends SwingWorker<User, Void>{
        @Override
        protected User doInBackground() throws Exception {
            var data = getUserInput();
            String cardNumber = (String)data.get(0);
            String PIN = (String)data.get(1);
            User newUser = User.getExistingUser(PIN, cardNumber);
            return newUser;
        }

        @Override
        protected void done(){
            try {
                User newUser = get();
                if(newUser == null){
                    JOptionPane.showMessageDialog(LoginGUI.this, "Пользователь не найден", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    LoginGUI.this.dispose();
                    // запуск сессии
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
