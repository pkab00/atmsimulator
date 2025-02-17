package com.atm.GUI.operations;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.atm.*;
import com.atm.GUI.*;

public class TransactionGUI extends UserInputGUI {
    private Account acc;
    public TransactionGUI(Account acc){
        super("Совершить перевод");
        this.acc = acc;
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
                JOptionPane.showMessageDialog(this, "Пользователь с таким номером карты не найден.",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Введите целое или десятичное число через точку.",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        corePane.add(submitButtton);
    }
}
