package com.atm.GUI;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public abstract class AutorizationGUI extends CoreGUI{
    private ArrayList<JTextField> fields = new ArrayList<>();

    protected abstract void composeUI();

    protected AutorizationGUI(String titleString){
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

    // Версия addInputPanel() для ввода числовго знвчения по шаблону
    protected void addInputPanel(String title, String format, String placeholder){
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

    // Версия addInputPanel() для ввода строки заглавных латинских букв
    protected void addInputPanel(String title, String placeholder){
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
                if (!(c >= 'A' && c <= 'Z')) {
                    e.consume();
                }
            }
        });
        panel.add(txt);
        panel.add(field);
        fields.add(field);
        corePane.add(panel);
    }

    protected ArrayList<Object> getUserInput(){
        ArrayList<Object> input = new ArrayList<>();
        for(JTextComponent field: fields){
            if(field instanceof JFormattedTextField){
                JFormattedTextField formattedField = (JFormattedTextField)field;
                try {
                    formattedField.commitEdit();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                input.add(formattedField.getValue());
            }
            else if(field instanceof JTextField){
                JTextField plainField = (JTextField)field;
                input.add(plainField.getText());
            }
        }
        return input;
    }
}
