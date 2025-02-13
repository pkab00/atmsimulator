package com.atm.GUI;

import java.awt.*;
import javax.swing.*;
import com.atm.User;

public class ATMSessionGUI extends CoreGUI {
    private User user;

    public ATMSessionGUI(User user){
        super();
        this.user = user;
        setTitle(String.format("ATM Simulator [%s]", user.getFullName()));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        corePane.setLayout(new BorderLayout());
        corePane.add(createTitleLabel(), BorderLayout.NORTH);
        corePane.add(createBalancePanel(), BorderLayout.CENTER);
    }
    private JLabel createTitleLabel(){
        JLabel titleLabel = new JLabel("ATM Simulator", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setPreferredSize(new Dimension(getHeight(), getWidth()/10));
        titleLabel.setFont(coreFont.deriveFont(30f));
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }
    private JPanel createBalancePanel(){
        JPanel balancePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel balanceLabel = new JLabel("БАЛАНС:", SwingConstants.CENTER);
        balanceLabel.setFont(coreFont.deriveFont(16f));
        balancePanel.add(balanceLabel);

        JTextField balanceTextField = new JTextField(String.valueOf(user.getAccount().getBalance())+" ₽");
        balanceTextField.setFont(coreFont.deriveFont(16f));
        balanceTextField.setEditable(false);
        balancePanel.add(balanceTextField);

        String[] titles = {"Пополнить", "Снять", "Перевод", "Настройки"};
        for(int i = 0; i < 4; i++){
            JButton newButton = new JButton(titles[i]);
            newButton.setFont(coreFont.deriveFont(16f));
            newButton.setFocusPainted(false);
            if(i==0 || i==3){
                newButton.setForeground(Color.BLACK);
                newButton.setBackground(Color.WHITE);
            } else{
                newButton.setForeground(Color.WHITE);
                newButton.setBackground(Color.BLACK);
            }
            balancePanel.add(newButton);
        }

        return balancePanel;
    }
}
