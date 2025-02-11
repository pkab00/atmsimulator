package com.atm.GUI;

import java.awt.*;
import javax.swing.*;

public class LoginGUI extends AutorizationGUI {
    public LoginGUI(){
        super("Вход");
        corePane.add(createLoginArea());
    }

    private Container createLoginArea(){
        Container loginArea = new Container();
        BoxLayout verticalLayout = new BoxLayout(loginArea, BoxLayout.Y_AXIS);
        loginArea.setLayout(verticalLayout);

        JPanel cardNumberPanel = createInputPanel("НОМЕР КАРТЫ", 
        "#### #### #### ####", "0000 0000 0000 0000");
        JPanel pinPanel = createInputPanel("PIN КОД", "####", "0000");

        loginArea.add(cardNumberPanel);
        loginArea.add(pinPanel);

        return loginArea;
    }
}
