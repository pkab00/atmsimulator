package com.atm.GUI.operations;

import javax.swing.*;

import com.atm.Account;
import com.atm.Operation;
import com.atm.GUI.ATMSessionGUI;

public class DepositGUI extends OperationGUI {
    private ATMSessionGUI sessionGUI;
    private Account acc;
    public DepositGUI(ATMSessionGUI sessionGUI){
        super("Пополнить счёт");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.sessionGUI = sessionGUI;
        this.acc = sessionGUI.getAccount();
        composeUI();
    }

    @Override
    protected void composeUI(){
        addDoubleInputPanel("Сумма");
        JButton submitButton = new JButton("ОК");
        submitButton.addActionListener((e) ->{
            try{
                double input = Double.parseDouble((String)getUserInput().get(0));
                new SingleOperationWorker(acc, input, this).execute();
            } catch(NumberFormatException ex){
                showWarning(this, "Введите целое или десятичное число через точку.");
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                showWarning(this, ex.getMessage());
            }
        });
        corePane.add(submitButton);
    }

    @Override
    public ATMSessionGUI getSessionGUI() {
        return sessionGUI;
    }

    
}
