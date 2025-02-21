package com.atm.GUI;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import javax.swing.*;
import com.atm.Account;
import com.atm.User;
import com.atm.GUI.operations.DepositGUI;
import com.atm.GUI.operations.OperationLogGUI;
import com.atm.GUI.operations.TransactionGUI;
import com.atm.GUI.operations.WithdrawGUI;

public class ATMSessionGUI extends CoreGUI {
    private User user;
    private JTextField balanceTextField;
    private GridBagConstraints gbc = new GridBagConstraints();
    private final int COLS = 2;
    private final int ROWS = 4;

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
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JLabel balanceLabel = new JLabel("БАЛАНС:", SwingConstants.CENTER);
        balanceLabel.setFont(coreFont.deriveFont(20f));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(balanceLabel, gbc);

        balanceTextField = new JTextField(String.valueOf(user.getAccount().getBalance())+" ₽");
        balanceTextField.setFont(coreFont.deriveFont(20f));
        balanceTextField.setEditable(false);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(balanceTextField, gbc);

        List<String> titles = List.of("Пополнить", "Снять", "Перевод", "История опреаций");
        List<Function<ATMSessionGUI, UserInputGUI>> constructors = List.of(
            DepositGUI::new, WithdrawGUI::new,
            TransactionGUI::new, OperationLogGUI::new
        );
        int i = 0;
        for(int y = 1; y < ROWS-1; y++){
            for(int x = 0; x < COLS; x++){
                final int index = i;
                JButton newButton = new JButton();
                newButton.setBackground((y%2==0) ? Color.BLACK : Color.WHITE);
                newButton.setForeground((y%2==0) ? Color.WHITE : Color.BLACK);
                newButton.addActionListener((e) -> constructors.get(index).apply(this));
                newButton.setBorderPainted(false);
                newButton.setText(titles.get(i++));
                newButton.setFont(coreFont.deriveFont(26f));

                gbc.fill = GridBagConstraints.BOTH;
                gbc.gridx = x;
                gbc.gridy = y;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                mainPanel.add(newButton, gbc);
            }
        }

        JButton logoutButton = new JButton(new ImageIcon(GRAPHICS+"//logout.png"));
        logoutButton.setText("Выйти из аккаунта");
        logoutButton.setFont(coreFont.deriveFont(20f));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener((e) ->{
            File savedData = new File("src\\main\\resources\\ser\\_user.ser");
            if(savedData.exists()) savedData.delete();
            dispose();
            new LoginGUI();
        });

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = ROWS;
        gbc.gridwidth = 2;
        mainPanel.add(logoutButton, gbc);

        return mainPanel;
    }

    public void updateBalanceLabel(){
        balanceTextField.setText(String.valueOf(user.getAccount().getBalance())+" ₽");
    }

    public Account getAccount(){
        return user.getAccount();
    }
}
