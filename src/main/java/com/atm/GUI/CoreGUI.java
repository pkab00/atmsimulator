package com.atm.GUI;

import javax.swing.*;

public class CoreGUI extends JFrame {
    protected static final String GRAPHICS = "src\\main\\resources\\graphics";

    protected CoreGUI(){
        setIconImage(new ImageIcon(GRAPHICS+"\\app-icon.png").getImage());
        setVisible(true);
        // ...
    }
}
