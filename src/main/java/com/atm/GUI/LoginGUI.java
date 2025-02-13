package com.atm.GUI;

import com.atm.User;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

public class LoginGUI extends AutorizationGUI {
    public LoginGUI(){
        super("Вход");
        composeUI();
    }

    @Override
    protected void composeUI(){
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
            this.dispose();
            new CreateAccountGUI();
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
                    JOptionPane.showMessageDialog(LoginGUI.this, "Ошибка входа. Проверьте введённые данные.", 
                    "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    LoginGUI.this.dispose();
                    new ATMSessionGUI(newUser);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
