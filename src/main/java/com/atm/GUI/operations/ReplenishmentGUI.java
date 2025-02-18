package com.atm.GUI.operations;

import javax.swing.*;

import com.atm.Account;
import com.atm.Operation;
import com.atm.GUI.ATMSessionGUI;
import com.atm.GUI.UserInputGUI;

public class ReplenishmentGUI extends UserInputGUI {
    private ATMSessionGUI parentGUI;
    private Account acc;
    public ReplenishmentGUI(ATMSessionGUI parentGUI){
        super("Пополнить счёт");
        this.parentGUI = parentGUI;
        this.acc = parentGUI.getAccount();
        composeUI();
    }

    @Override
    protected void composeUI(){
        addDoubleInputPanel("Сумма");
        JButton submitButton = new JButton("ОК");
        submitButton.addActionListener((e) ->{
            try{
                double input = Double.parseDouble((String)getUserInput().get(0));
                Operation newOp = new Operation(acc, input);
                newOp.commit();
                System.out.println(acc);
                dispose();
            } catch(NumberFormatException ex){
                showWarning(this, "Введите целое или десятичное число через точку.");
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                showWarning(this, ex.getMessage());
            }
        });
        corePane.add(submitButton);
    }
}
