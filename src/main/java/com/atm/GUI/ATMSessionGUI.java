package com.atm.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.atm.User;

public class ATMSessionGUI extends CoreGUI {
    private User user;

    public ATMSessionGUI(User user){
        super();
        this.user = user;
        setTitle(String.format("ATM Simulator [%s]", user.getFullName()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        corePane.setLayout(new BorderLayout(5, 5));
        corePane.add(createTitleLabel(), BorderLayout.NORTH);
        corePane.add(createBalancePanel());
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
        JLabel balanceLabel = new JLabel("БАЛАНС:");
        balanceLabel.setFont(coreFont.deriveFont(16f));
        //balancePanel.add(balanceLabel);

        for(int i = 0; i < 6; i++){
            balancePanel.add(new JButton("Кнопка №"+i));
        }

        JTextField balanceTextField = new JTextField(String.valueOf(user.getAccount().getBalance())+" ₽");
        balanceTextField.setFont(coreFont.deriveFont(16f));
        balanceTextField.setEditable(false);
        //balancePanel.add(balanceTextField);
        return balancePanel;
    }
}
