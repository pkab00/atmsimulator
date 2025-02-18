package com.atm.GUI.operations;

import javax.swing.SwingWorker;

import com.atm.Account;
import com.atm.CommonDAO;
import com.atm.Operation;
import com.atm.Operation.InvalidOperationException;

public class DoubleOperationWorker extends SwingWorker<Void, Void> {
    private OperationGUI operationGUI;
    private Account giver, reciever;
    private Operation operation;

    public DoubleOperationWorker(Account giver, Account reciever, double sum, OperationGUI operationGUI)
    throws InvalidOperationException{
        this.operationGUI = operationGUI;
        this.giver = giver;
        this.reciever = reciever;
        this.operation = new Operation(reciever, giver, sum);
    }

    @Override
    protected Void doInBackground() throws Exception {
        Account firstLock = giver.getCardNumber().length() < reciever.getCardNumber().length() ? giver : reciever;
        Account secondLock = giver.getCardNumber().length() < reciever.getCardNumber().length() ? reciever : giver;
        synchronized(firstLock){
            synchronized(secondLock){
                operation.commit();
                CommonDAO.updateBalance(giver);
                CommonDAO.updateBalance(reciever);
            }
            return null;
        }
    }
    
    @Override
    protected void done(){
        operationGUI.getSessionGUI().updateBalanceLabel();
        operationGUI.dispose();
    }
}
