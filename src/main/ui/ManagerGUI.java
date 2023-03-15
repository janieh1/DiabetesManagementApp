package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerGUI extends JFrame implements ActionListener {
    private AddEntryPanel logBookPanel;

    public ManagerGUI() {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Diabetes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SelectionMenuPanel menu = new SelectionMenuPanel();
        menu.setOpaque(true); //content panes must be opaque
        frame.setContentPane(menu);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add entry")) {
            //getReadingFromUser(); // !!!
        } else if (e.getActionCommand().equals("calculate averages")) {
            //displayAverages(); // !!!
        } else if (e.getActionCommand().equals("calculate insulin")) {
            //calculateInsulinFromInput(); // !!!
        } else if (e.getActionCommand().equals("save to file")) {
            //saveLogBook();
        } else if (e.getActionCommand().equals("load from file")) {
            //loadLogBook();
        } else if (e.getActionCommand().equals("return to menu")) {
            // return to main menu
        }
    }
}
