package com.atm.GUI.operations;

import javax.swing.SwingWorker;

import com.atm.Account;
import com.atm.CommonDAO;
import com.atm.Operation;
import com.atm.Operation.InvalidOperationException;

public class SingleOperationWorker extends SwingWorker<Void, Void> {
    private OperationGUI operationGUI;
    private Account reciever;
    private Operation operation;

    public SingleOperationWorker(Account reciever, double sum, OperationGUI operationGUI)
    throws InvalidOperationException{
        this.reciever = reciever;
        this.operation = new Operation(reciever, sum);
        this.operationGUI = operationGUI;
    }

    @Override
    protected Void doInBackground() {
        synchronized(reciever){
            operation.commit();
            CommonDAO.updateBalance(reciever);
        }
        return null;
    }

    @Override
    protected void done(){
        operationGUI.getSessionGUI().updateBalanceLabel();
        operationGUI.dispose();
    }
}
