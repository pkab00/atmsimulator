package com.atm.GUI.operations;

import javax.swing.JButton;
import com.atm.*;
import com.atm.GUI.*;

public class TransactionGUI extends UserInputGUI {
    private ATMSessionGUI parentGUI;
    private Account acc;
    public TransactionGUI(ATMSessionGUI parentGUI){
        super("Совершить перевод");
        this.parentGUI = parentGUI;
        this.acc = parentGUI.getAccount();
        composeUI();
        System.out.println();
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
                Operation newOp = new Operation(sendtoAccount, acc, transactionSum);
                newOp.commit();
                System.out.println(acc);
                System.out.println(sendtoAccount);
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
}
