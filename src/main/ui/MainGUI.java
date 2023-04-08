package ui;

import model.EventLog;

import javax.swing.*;

// Represents the GUI
// TODO: print to console all the events that occurred WHEN THE APPLICATION IS CLOSED
public class MainGUI {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ManagerGUI();
            }
        });
    }
}

