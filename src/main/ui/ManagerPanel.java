package ui;

import javax.swing.*;
import java.awt.*;

public abstract class ManagerPanel extends JPanel {
    JPanel panel;
    ManagerGUI gui;
    protected JButton returnMainMenu;


    public ManagerPanel(int rows, int cols, ManagerGUI gui) {
        panel = new JPanel(new GridLayout(rows, cols));
        this.gui = gui;
    }


    // MODIFIES: ManagerGUI
    // EFFECTS: sets visibility of current panel to false, and shows the main selection menu
    public void addReturnMainMenuButton() {
        returnMainMenu = new JButton("Return to Main Menu");
        returnMainMenu.setActionCommand("return to menu");
        returnMainMenu.addActionListener(gui);
        add(returnMainMenu);
    }

    protected abstract void addEverything();
}
