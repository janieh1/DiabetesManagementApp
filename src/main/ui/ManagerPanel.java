package ui;

import javax.swing.*;
import java.awt.*;

// abstract class to represent secondary windows that pop up after a main menu button is selected
public abstract class ManagerPanel extends JPanel {
    JPanel panel;
    ManagerGUI gui;
    protected JButton returnMainMenu;


    // MODIFIES: this
    // EFFECTS: constructs panel
    public ManagerPanel(int rows, int cols, ManagerGUI gui) {
        panel = new JPanel(new GridLayout(rows, cols));
        this.gui = gui;
    }


    // MODIFIES: this
    // EFFECTS: adds return to main menu button to the panel
    public void addReturnMainMenuButton() {
        returnMainMenu = new JButton("Return to Main Menu");
        returnMainMenu.setActionCommand("return to menu");
        returnMainMenu.addActionListener(gui);
        add(returnMainMenu);
    }

    // MODIFIES: this
    // EFFECTS: adds all components created in constructor to the panel and shows them
    protected abstract void addEverything();
}
