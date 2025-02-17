package com.atm.GUI.operations;

import javax.swing.JButton;
import com.atm.Account;
import com.atm.Operation;
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
                showWarning(this, "Введите целое или десятичное число через точку.");
                ex.printStackTrace();
            } catch(Operation.InvalidOperationException ex){
                showWarning(this, ex.getMessage());
                ex.printStackTrace();
            }
        });
        corePane.add(submitButton);
    }
}
