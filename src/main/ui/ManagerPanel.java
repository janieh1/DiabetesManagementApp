package ui;

import javax.swing.*;
import java.awt.*;

public abstract class ManagerPanel extends JPanel {
    JPanel panel;
    protected ManagerGUI gui;


    public ManagerPanel(int rows, int cols) {
        panel = new JPanel(new GridLayout(rows, cols));
        JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton menuButton = new JButton("Return to Selection Menu");
        menuButton.setActionCommand("return to menu");
        menuButton.addActionListener(gui);
        addMenuButton(menuButton, panel);
    }

    // EFFECTS: Creates a button and adds it to the given panel, changing various attributes of the
    //          color and text of the button
    public void addMenuButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("Arial", Font.BOLD, 12));
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.white);
        panel.add(button1);
        setVisible(true);
    }


    // MODIFIES: ManagerGUI
    // EFFECTS: sets visibility of current panel to false, and shows the main selection menu
    public void returnToSelectionMenu(JPanel menu) {
        menu.setVisible(true);
        this.setVisible(false);
    }
}
