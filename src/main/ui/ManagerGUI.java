package ui;

import model.LogBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerGUI extends JFrame implements ActionListener {
    private LogBook book;
    private JList logBookDisplay;

    private AddEntryPanel addEntryPanel;
    private CalculatorPanel calculatorPanel;
    private DisplayStatsPanel statsPanel;

    private static JFrame frame;

    private JPanel mainMenu;

    private static JButton b1; // add entry
    private static JButton b2; // calculate averages and display graph
    private static JButton b3; // open insulin calculator
    private static JButton b4; // save
    private static JButton b5; // load

    public ManagerGUI() {
        book = new LogBook();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setPreferredSize(new Dimension(1200, 500));

        //Create and set up the window.
        frame = new JFrame("Diabetes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMainMenu();
        initializeMainMenu();
        makeLogBook();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void createMainMenu() {
        mainMenu = new JPanel(new GridLayout(2, 1));
        b1 = new JButton("Add Log Book Entry");
        b1.setActionCommand("add entry");
        b1.addActionListener(this);
        b2 = new JButton("Calculate Average Blood Sugars");
        b2.setActionCommand("calculate averages");
        b2.addActionListener(this);
        b3 = new JButton("Calculate Insulin to Give");
        b3.setActionCommand("calculate insulin");
        b3.addActionListener(this);
        b4 = new JButton("Save Log Book to File");
        b4.setActionCommand("save to file");
        b4.addActionListener(this);
        b5 = new JButton("Load Log Book from File");
        b5.setActionCommand("load from file");
        b5.addActionListener(this);
        mainMenu.add(b1);
        mainMenu.add(b2);
        mainMenu.add(b3);
        mainMenu.add(b4);
        mainMenu.add(b5);
    }

    private void initializeMainMenu() {
        frame.setContentPane(mainMenu);
    }

    // EFFECTS: displays contents of logbook on frame
    public void makeLogBook() {
        String[] readings;
        readings = book.getReadingsAsStrings().toArray(new String[0]);

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel listLabel = new JLabel("My LogBook");
        listLabel.setLabelFor(listPane);
        listPane.add(listLabel);
        logBookDisplay = new JList(readings);
        logBookDisplay.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        logBookDisplay.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        logBookDisplay.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(logBookDisplay);
        listScroller.setPreferredSize(new Dimension(250, 80));
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame.add(listPane);

//        logBookDisplay = new JScrollPane(listOfReadings, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        frame.add(logBookDisplay);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add entry")) {
            addEntryPanel = new AddEntryPanel(this);
            frame.setContentPane(addEntryPanel);
            frame.pack();
            frame.setVisible(true);
            addEntryPanel.addEntryToLogBook(book, this);
        } else if (e.getActionCommand().equals("calculate averages")) {
            //displayAverages(); // !!!
        } else if (e.getActionCommand().equals("calculate insulin")) {
            //calculateInsulinFromInput(); // !!!
        } else if (e.getActionCommand().equals("save to file")) {
            //saveLogBook();
        } else if (e.getActionCommand().equals("load from file")) {
            //loadLogBook();
        } else if (e.getActionCommand().equals("return to menu")) {
            frame.setContentPane(mainMenu);
            frame.pack();
            frame.setVisible(true);

        }
    }
}
