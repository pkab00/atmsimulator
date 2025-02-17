package com.atm.GUI;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;

import com.atm.CommonDAO;

public abstract class UserInputGUI extends CoreGUI{
    private ArrayList<JTextField> fields = new ArrayList<>();

    protected abstract void composeUI();

    protected UserInputGUI(String titleString){
        super();
        setTitle(titleString);
        setSize(500, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        BoxLayout layout = new BoxLayout(corePane, BoxLayout.Y_AXIS);
        corePane.setLayout(layout);
        JLabel titleLabel = new JLabel(titleString);
        titleLabel.setFont(coreFont.deriveFont(25f));
        corePane.add(titleLabel);
        corePane.add(Box.createHorizontalStrut(1));
    }

    // Создание текстового поля для ввода числовго знвчения по шаблону
    protected void addFormattedInputPanel(String title, String format, String placeholder){
        title = String.format("%s:\t", title);
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
            formatter.setPlaceholder(placeholder);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JPanel panel = new JPanel(new FlowLayout());
        JLabel txt = new JLabel(title);
        txt.setFont(coreFont.deriveFont(12f));
        JFormattedTextField field = new JFormattedTextField(formatter);
        field.setPreferredSize(new Dimension(250, 20));
        panel.add(txt);
        panel.add(field);
        fields.add(field);
        corePane.add(panel);
    }

    // Создание текстового поля для ввода строки заглавных латинских букв
    protected void addUpcaseInputPanel(String title, String placeholder){
        title = String.format("%s:\t", title);
        JPanel panel = new JPanel(new FlowLayout());
        JLabel txt = new JLabel(title);
        txt.setFont(coreFont.deriveFont(12f));
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(250, 20));
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c < 'A' || c > 'Z') {
                    e.consume();
                }
            }
        });
        panel.add(txt);
        panel.add(field);
        fields.add(field);
        corePane.add(panel);
    }

    // Создание текстового поля для ввода значения типа double
    protected void addDoubleInputPanel(String title){
        title = String.format("%s:\t", title);
        JPanel panel = new JPanel(new FlowLayout());
        JLabel txt = new JLabel(title);
        txt.setFont(coreFont.deriveFont(12f));
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(250, 20));
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c < '0' || c > '9') && c != '.') {
                    e.consume();
                }
            }
        });
        panel.add(txt);
        panel.add(field);
        fields.add(field);
        corePane.add(panel);
    }

    // Создание нередактируемого текстового поля с номером карты
    protected void addFixatedInputPanel(String title){
        title = String.format("%s:\t", title);
        JPanel panel = new JPanel(new FlowLayout());
        JLabel txt = new JLabel(title);
        txt.setFont(coreFont.deriveFont(12f));
        JTextField field = new JTextField(CommonDAO.generateCardNumber());
        field.setPreferredSize(new Dimension(250, 20));
        field.setEditable(false);

        panel.add(txt);
        panel.add(field);
        fields.add(field);
        corePane.add(panel);
    }

    protected ArrayList<Object> getUserInput(){
        ArrayList<Object> input = new ArrayList<>();
        for(JTextComponent field: fields){
            if(field.getClass().equals(JFormattedTextField.class)){
                JFormattedTextField formattedField = (JFormattedTextField)field;
                try {
                    formattedField.commitEdit();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                input.add(formattedField.getValue());
            }
            else if(field.getClass().equals(JTextField.class)){
                JTextField plainField = (JTextField)field;
                input.add(plainField.getText());
            }
        }
        return input;
    }

    protected static void showWarning(UserInputGUI screen, String text){
        JOptionPane.showMessageDialog(screen, text,
                "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
