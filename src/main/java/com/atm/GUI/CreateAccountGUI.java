package com.atm.GUI;

import java.util.ArrayList;
import javax.swing.JButton;
import com.atm.User;

public class CreateAccountGUI extends AutorizationGUI {
    public CreateAccountGUI(){
        super("Регистрация");
        composeUI();
    }

    @Override
    protected void composeUI() {
        addInputPanel("ФАМИЛИЯ", "IVANOV");
        addInputPanel("ИМЯ", "IVAN");
        addInputPanel("ОТЧЕСТВО", "IVANOVICH");
        addInputPanel("НОМЕР КАРТЫ");
        addInputPanel("PIN", "####", "0000");

        JButton loginButton = new JButton("Зарегистрироваться");
        loginButton.addActionListener((e) -> {
            ArrayList<Object> data = getUserInput();
            String surname = (String)data.get(0);
            String name = (String)data.get(1);
            String fatherName = (String)data.get(2);
            String cardNumber = (String)data.get(3);
            String PIN = (String)data.get(4);
            User.getNewUser(cardNumber, PIN, surname, name, fatherName);
            this.dispose();
            // запуск сессии
        });
        corePane.add(loginButton);
    }
    
}
