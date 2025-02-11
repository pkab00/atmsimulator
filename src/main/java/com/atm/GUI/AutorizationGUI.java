package com.atm.GUI;

import java.awt.FlowLayout;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public class AutorizationGUI extends CoreGUI {
    private ArrayList<JFormattedTextField> fields = new ArrayList<>();

    protected AutorizationGUI(String titleString){
        super();
        setTitle(titleString);
        setSize(400, 750);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        BoxLayout layout = new BoxLayout(corePane, BoxLayout.Y_AXIS);
        corePane.setLayout(layout);
        JLabel titleLabel = new JLabel(titleString);
        titleLabel.setFont(coreFont.deriveFont(20f));
        corePane.add(titleLabel);
        corePane.add(Box.createHorizontalStrut(5));
    }

    protected JPanel createInputPanel(String title, String format, String placeholder){
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
        panel.add(txt);
        panel.add(field);
        fields.add(field);
        return panel;
    }

    protected ArrayList<String> getUserInput(){
        ArrayList<String> input = new ArrayList<>();
        for(JFormattedTextField field: fields){
            input.add(field.getText());
        }
        return input;
    }
}
