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
        //JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //JButton menuButton = new JButton("Return to Selection Menu");
        //menuButton.setActionCommand("return to menu");
        //menuButton.addActionListener(gui);
        //addMenuButton(menuButton, panel);
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
