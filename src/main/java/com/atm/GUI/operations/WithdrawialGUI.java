package com.atm.GUI.operations;

import javax.swing.JButton;
import com.atm.Account;
import com.atm.Operation;
import com.atm.GUI.ATMSessionGUI;

public class WithdrawialGUI extends OperationGUI {
    private ATMSessionGUI sessionGUI;
    private Account acc;
    public WithdrawialGUI(ATMSessionGUI sessionGUI){
        super("Снять средства");
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
                double input = 0 - Double.parseDouble((String)getUserInput().get(0));
                new SingleOperationWorker(acc, input, this).execute();
            } catch(NumberFormatException ex){
                showWarning(this, "Введите целое или десятичное число через точку.");
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                showWarning(this, ex.getMessage());
                ex.printStackTrace();
            }
        });
        corePane.add(submitButton);
    }

    @Override
    public ATMSessionGUI getSessionGUI() {
        return sessionGUI;
    }
}
