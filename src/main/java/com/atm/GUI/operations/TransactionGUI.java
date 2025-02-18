package com.atm.GUI.operations;

import javax.swing.JButton;
import com.atm.*;
import com.atm.GUI.*;

public class TransactionGUI extends OperationGUI {
    private ATMSessionGUI sessionGUI;
    private Account acc;
    public TransactionGUI(ATMSessionGUI sessionGUI){
        super("Совершить перевод");
        this.sessionGUI = sessionGUI;
        this.acc = sessionGUI.getAccount();
        composeUI();
    }

    @Override
    protected void composeUI() {
        addFormattedInputPanel("НОМЕР КАРТЫ", 
        "#### #### #### ####", "0000 0000 0000 0000");
        addDoubleInputPanel("Сумма");
        JButton submitButtton = new JButton("ОК");
        submitButtton.addActionListener((e) ->{
            var input = getUserInput();
            Account sendtoAccount = Account.getExistingAccount((String)input.get(0));
            if(sendtoAccount == null){
                showWarning(this, "Пользователь с таким номером карты не найден.");
                return;
            }
            try{
                double transactionSum = Double.parseDouble((String)input.get(1));
                new DoubleOperationWorker(acc, sendtoAccount, transactionSum, this).execute();;
                dispose();
            } catch(NumberFormatException ex){
                showWarning(this, "Введите целое или десятичное число через точку.");
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                showWarning(this, ex.getMessage());
                ex.printStackTrace();
            }
        });
        corePane.add(submitButtton);
    }

    @Override
    public ATMSessionGUI getSessionGUI() {
        return sessionGUI;
    }
}
