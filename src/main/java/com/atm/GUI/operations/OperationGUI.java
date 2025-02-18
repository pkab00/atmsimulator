package com.atm.GUI.operations;

import com.atm.GUI.ATMSessionGUI;
import com.atm.GUI.UserInputGUI;

public abstract class OperationGUI extends UserInputGUI {
    protected OperationGUI(String titleString) {
        super(titleString);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public abstract ATMSessionGUI getSessionGUI();
}
