package com.atm.GUI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Font;

class CoreGUITest {

    @Test
    void testFontLoading() {
        CoreGUI coreGUI = new CoreGUI() {};
        Font loadedFont = coreGUI.loadFont();
        assertNotNull(loadedFont, "Font should be loaded successfully");
        assertEquals("UNCAGE Regular", loadedFont.getName(), "Loaded font name should match");
    }

    @Test
    void testWindowInitialization() {
        CoreGUI coreGUI = new CoreGUI() {};
        assertTrue(coreGUI.isVisible(), "Window should be visible");
        assertEquals(1000, coreGUI.getWidth(), "Window width should be 1000");
        assertEquals(800, coreGUI.getHeight(), "Window height should be 800");
        assertNotNull(coreGUI.getIconImage(), "Window icon should be set");
    }
}
