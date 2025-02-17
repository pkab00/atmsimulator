package com.atm.GUI.operations;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.atm.Account;
import com.atm.Operation;
import com.atm.User;
import com.atm.GUI.UserInputGUI;

public class WithdrawialGUI extends UserInputGUI {
    private Account acc;
    public WithdrawialGUI(Account acc){
        super("Снять средства");
        this.acc = acc;
        composeUI();
    }

    @Override
    protected void composeUI(){
        addDoubleInputPanel("Сумма");
        JButton submitButton = new JButton("ОК");
        submitButton.addActionListener((e) ->{
            try{
                double input = 0 - Double.parseDouble((String)getUserInput().get(0));
                Operation newOp = new Operation(acc, input);
                newOp.commit();
                System.out.println(acc);
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
        corePane.add(submitButton);
    }
}
