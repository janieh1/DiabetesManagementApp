package ui;

import exceptions.ReadingNotFoundException;
import model.BloodSugarReading;
import model.LogBook;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ManagerGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/logbook.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private LogBook book;
    private JList logBookDisplay;

    private AddEntryPanel addEntryPanel;
    private CalculatorPanel calculatorPanel;
    private DisplayStatsPanel statsPanel;

    private static JFrame frame;

    private JPanel mainMenu;
    private JPanel listPane;

    private static JButton b1; // add entry
    private static JButton b2; // calculate averages and display graph
    private static JButton b3; // open insulin calculator
    private static JButton b4; // save
    private static JButton b5; // load
    private static JButton beforeOnly;
    private static JButton afterOnly;
    private static JButton fastingOnly;
    private static JButton allReadings;
    private static JButton addNotes;
    private static JLabel addNotesLabel;

    private JTextField notesToAdd;

    public ManagerGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        book = new LogBook();
        book.addReading(new BloodSugarReading(4.5, "2023-06-06", "14:15", "fasting"));
        createAndShowGUI();
    }

    private void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("Diabetes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 500));

        createMainMenu();
        initializeMainMenu();
        makeLogBook(true, "none");

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @SuppressWarnings("methodlength")
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
        beforeOnly = new JButton("Show before meal readings only");
        beforeOnly.setActionCommand("before only");
        beforeOnly.addActionListener(this);
        afterOnly = new JButton("Show after meal readings only");
        afterOnly.setActionCommand("after only");
        afterOnly.addActionListener(this);
        fastingOnly = new JButton("Show fasting readings only");
        fastingOnly.setActionCommand("fasting only");
        fastingOnly.addActionListener(this);
        allReadings = new JButton("Show all readings");
        allReadings.setActionCommand("all");
        allReadings.addActionListener(this);
        addNotes = new JButton("Add Notes");
        addNotes.setActionCommand("add notes");
        addNotes.addActionListener(this);
        addNotesLabel = new JLabel("Notes to add:");
        notesToAdd = new JTextField(20);
        mainMenu.add(b1);
        mainMenu.add(b2);
        mainMenu.add(b3);
        mainMenu.add(b4);
        mainMenu.add(b5);
        mainMenu.add(beforeOnly);
        mainMenu.add(afterOnly);
        mainMenu.add(fastingOnly);
        mainMenu.add(allReadings);
        mainMenu.add(addNotes);
        mainMenu.add(addNotesLabel);
        mainMenu.add(notesToAdd);

    }

    private void initializeMainMenu() {
        frame.setContentPane(mainMenu);
    }

    // EFFECTS: displays contents of logbook on frame
    public void makeLogBook(boolean initialize, String category) {
        JLabel listLabel;
        String[] readings;
        readings = book.getReadingsAsStrings(category).toArray(new String[0]);
        if (!initialize) {
            frame.remove(listPane);
        }
        listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        if (category == "none") {
            listLabel = new JLabel("My LogBook:");
        } else {
            listLabel = new JLabel("My LogBook (" + category + "):");
        }
        listLabel.setLabelFor(listPane);
        listPane.add(listLabel);
        logBookDisplay = new JList(readings);
        logBookDisplay.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        logBookDisplay.setLayoutOrientation(JList.VERTICAL_WRAP);
        JScrollPane listScroller = new JScrollPane(logBookDisplay);
        listScroller.setPreferredSize(new Dimension(250, 100));
        listPane.add(Box.createRigidArea(new Dimension(5,5)));
        listPane.add(listScroller);
        frame.add(listPane);
    }

    private void setNotesForSelectedItem() {
        String notes = notesToAdd.getText();
        int index = logBookDisplay.getSelectedIndex();
        try {
            book.setNotesOfReadingOnTimeAndDay(book.getReadings().get(index).getTime(),
                    book.getReadings().get(index).getDate(), notes);
        } catch (ReadingNotFoundException e) {
            // do nothing for now anyways
        }

    }


    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add entry")) {
            addEntryPanel = new AddEntryPanel(this);
            changePane(addEntryPanel);
        } else if (e.getActionCommand().equals("add entry to list of entries")) {
            addEntryPanel.addEntryToLogBook(book, this);
        } else if (e.getActionCommand().equals("calculate averages")) {
            statsPanel = new DisplayStatsPanel(this);
            changePane(statsPanel);
        } else if (e.getActionCommand().equals("calculate insulin")) {
            calculatorPanel = new CalculatorPanel(this);
            changePane(calculatorPanel);
        } else if (e.getActionCommand().equals("save to file")) {
            saveLogBook();
        } else if (e.getActionCommand().equals("load from file")) {
            loadLogBook();
        } else if (e.getActionCommand().equals("return to menu")) {
            changePane(mainMenu);
            makeLogBook(false, "none");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setPreferredSize(new Dimension(1500, 400));
        } else if (e.getActionCommand().equals("before only")) {
            makeLogBook(false, "before meal");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setPreferredSize(new Dimension(1500, 400));
        } else if (e.getActionCommand().equals("after only")) {
            makeLogBook(false, "after meal");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setPreferredSize(new Dimension(1500, 400));
        } else if (e.getActionCommand().equals("fasting only")) {
            makeLogBook(false, "fasting");
            frame.setPreferredSize(new Dimension(1500, 400));
            SwingUtilities.updateComponentTreeUI(frame);
        } else if (e.getActionCommand().equals("all")) {
            makeLogBook(false, "none");
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setPreferredSize(new Dimension(1500, 400));
        } else if (e.getActionCommand().equals("add notes")) {
            setNotesForSelectedItem();
        }
    }

    // EFFECTS: saves the logbook to file
    private void saveLogBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();
            System.out.println("Saved logbook to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads logbook from file
    private void loadLogBook() {
        try {
            book = jsonReader.read();
            makeLogBook(false, "none");
            SwingUtilities.updateComponentTreeUI(frame);
            System.out.println("Loaded logbook from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public LogBook getBook() {
        return book;
    }

    private void changePane(JPanel panel) {
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
