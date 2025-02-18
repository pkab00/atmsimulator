package com.atm.GUI.operations;

import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.atm.Account;
import com.atm.CommonDAO;
import com.atm.OperationDTO;
import com.atm.GUI.ATMSessionGUI;
import com.atm.GUI.UserInputGUI;
import com.atm.REQUEST_TYPE;

public class OperationLogGUI extends UserInputGUI {
    private Account acc;
    public OperationLogGUI(ATMSessionGUI sessionGUI){
        super("История операций");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.acc = sessionGUI.getAccount();
        composeUI();
    }

    @Override
    protected void composeUI() {
        Vector<OperationDTO> data = CommonDAO.requestData(acc.getCardNumber(), REQUEST_TYPE.OPERATIONS)
        .stream().map((x) -> (OperationDTO)x).collect(Collectors.toCollection(Vector::new));

        JList<OperationDTO> logJList = new JList<>(data);
        logJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        corePane.add(new JScrollPane(logJList));
    }
}
