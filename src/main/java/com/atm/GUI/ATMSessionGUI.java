package com.atm.GUI;

import com.atm.User;

public class ATMSessionGUI extends CoreGUI {
    private User user;

    public ATMSessionGUI(User user){
        super();
        this.user = user;
        setTitle(String.format("ATM Simulator [%s]", user.getFullName()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
