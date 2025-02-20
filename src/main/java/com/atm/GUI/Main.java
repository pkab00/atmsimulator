package com.atm.GUI;

import javax.swing.SwingUtilities;

import com.atm.User;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = User.loadSavedData();
            if(user!=null) new ATMSessionGUI(user);
            else new LoginGUI();
        });
    }
}
