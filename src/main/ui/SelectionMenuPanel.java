package ui;

import javax.swing.*;
import java.awt.*;

/* References code from
  https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
 */

public class SelectionMenuPanel extends JPanel {
    private JButton b1; // add entry
    private JButton b2; // calculate averages and display graph
    private JButton b3; // open insulin calculator
    private JButton b4; // save
    private JButton b5; // load
    private DiabetesManager manager;
    private ManagerGUI gui;

    public SelectionMenuPanel() {
        setPreferredSize(new Dimension(1200, 500));
        b1 = new JButton("Add Log Book Entry");
        b1.setActionCommand("add entry");
        b1.addActionListener(gui);
        b2 = new JButton("Calculate Average Blood Sugars");
        b2.setActionCommand("calculate averages");
        b2.addActionListener(gui);
        b3 = new JButton("Calculate Insulin to Give");
        b3.setActionCommand("calculate insulin");
        b3.addActionListener(gui);
        b4 = new JButton("Save Log Book to File");
        b4.setActionCommand("save to file");
        b4.addActionListener(gui);
        b5 = new JButton("Load Log Book from File");
        b5.setActionCommand("load from file");
        b5.addActionListener(gui);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);

    }

}
