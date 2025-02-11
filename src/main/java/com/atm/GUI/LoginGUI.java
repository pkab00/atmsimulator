package com.atm.GUI;

import java.awt.*;
import javax.swing.*;

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
            var data = getUserInput();
            // добавить метод для запроса к БД
        });
        corePane.add(submitButton);

        JLabel noAccountLabel = new JLabel("Ещё нет акаунта?");
        noAccountLabel.setFont(coreFont.deriveFont(16f));
        corePane.add(Box.createHorizontalStrut(1));
        corePane.add(noAccountLabel);

        JButton goToRegistrationButton = new JButton("Регистрация");
        goToRegistrationButton.addActionListener((e) -> {
            JFrame source = (JFrame)e.getSource();
            source.dispose();
            // добавить переход к окну регистрации
        });
        corePane.add(goToRegistrationButton);
    }
}
