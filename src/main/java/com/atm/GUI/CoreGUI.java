package com.atm.GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class CoreGUI extends JFrame {
    protected static final String GRAPHICS = "src\\main\\resources\\graphics";
    protected Container corePane = getRootPane();
    protected Font coreFont = loadFont();

    protected CoreGUI(){
        setIconImage(new ImageIcon(GRAPHICS+"\\app-icon.png").getImage());
        setVisible(true);
        // ...
    
    }

    protected Font loadFont(){
        Font loadedFont = null;
        try {
            loadedFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\Uncage.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return loadedFont;
    }

}
