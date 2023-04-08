package ui;

import exceptions.ReadingNotFoundException;
import model.Event;
import model.EventLog;
import model.LogBook;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// represents the main JFrame object where all the gui components are contained
public class ManagerGUI extends JFrame implements ActionListener, WindowListener, WindowFocusListener,
        WindowStateListener {
    private static final String JSON_STORE = "./data/logbook.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private LogBook book;
    private JList logBookDisplay;

    private AddEntryPanel addEntryPanel;

    private static JFrame frame;

    private JPanel mainMenu;
    private JPanel listPane;

    private JTextField notesToAdd;

    // MODIFIES: this
    // EFFECTS: constructs a JFrame with the main menu showing
    public ManagerGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        book = new LogBook();
        createAndShowGUI();
    }

    public LogBook getBook() {
        return book;
    }

    public JFrame getFrame() {
        return frame;
    }

    // MODIFIES: this
    // EFFECTS: sets up the main JFrame object
    private void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("Diabetes Manager");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.setLayout(new GridLayout());

        createMainMenu();
        makeLogBook(true, "none");

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(this);
        frame.addWindowFocusListener(this);
        frame.addWindowStateListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the main menu (selection buttons) to the frame
    @SuppressWarnings("methodlength")
    private void createMainMenu() {
        mainMenu = new JPanel(new GridLayout(3, 2));

        JButton b1 = new JButton("Add Log Book Entry");
        b1.setActionCommand("add entry");
        b1.addActionListener(this);

        JButton b2 = new JButton("View Average Blood Sugars");
        b2.setActionCommand("calculate averages");
        b2.addActionListener(this);

        JButton b3 = new JButton("Calculate Insulin to Give");
        b3.setActionCommand("calculate insulin");
        b3.addActionListener(this);

        JButton b4 = new JButton("Save Log Book to File");
        b4.setActionCommand("save to file");
        b4.addActionListener(this);

        JButton b5 = new JButton("Load Log Book from File");
        b5.setActionCommand("load from file");
        b5.addActionListener(this);

        ImageIcon refIcon = new ImageIcon("./data/Refresh_icon.png");
        Image image = refIcon.getImage(); // transform it
        Image newImage = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        refIcon = new ImageIcon(newImage);  // transform it back
        JButton refresh = new JButton(refIcon);
        refresh.setActionCommand("refresh");
        refresh.addActionListener(this);

        mainMenu.add(b1);
        mainMenu.add(b2);
        mainMenu.add(b3);
        mainMenu.add(b4);
        mainMenu.add(b5);
        mainMenu.add(refresh);

        frame.getContentPane().add(mainMenu);
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: displays contents of logbook on frame
    public void makeLogBook(boolean initialize, String category) {
        JLabel listLabel;
        String[] readings;
        readings = book.getReadingsAsStrings(category).toArray(new String[0]);
        if (!initialize) {
            frame.remove(listPane);
        }
        listPane = new JPanel(new GridLayout(2, 5));
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        if (category.equals("none")) {
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
        listScroller.setPreferredSize(new Dimension(250, 250));
        listPane.add(Box.createRigidArea(new Dimension(5,5)));
        listPane.add(listScroller);

        JButton beforeOnly = new JButton("Show before meal readings only");
        beforeOnly.setActionCommand("before only");
        beforeOnly.addActionListener(this);
        JButton afterOnly = new JButton("Show after meal readings only");
        afterOnly.setActionCommand("after only");
        afterOnly.addActionListener(this);
        JButton fastingOnly = new JButton("Show fasting readings only");
        fastingOnly.setActionCommand("fasting only");
        fastingOnly.addActionListener(this);
        JButton allReadings = new JButton("Show all readings");
        allReadings.setActionCommand("all");
        allReadings.addActionListener(this);
        JButton addNotes = new JButton("Add Notes");
        addNotes.setActionCommand("add notes");
        addNotes.addActionListener(this);
        JLabel addNotesLabel = new JLabel("Notes to add:");
        notesToAdd = new JTextField(20);

        listPane.add(beforeOnly);
        listPane.add(afterOnly);
        listPane.add(fastingOnly);
        listPane.add(allReadings);
        listPane.add(addNotesLabel);
        listPane.add(notesToAdd);
        listPane.add(addNotes);

        frame.getContentPane().add(listPane);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    // MODIFIES: this, book
    // EFFECTS: adds notes to selected reading in logbook, and displays notes on screen
    private void setNotesForSelectedItem() {
        String notes = notesToAdd.getText();
        int index = logBookDisplay.getSelectedIndex();
        try {
            book.setNotesOfReadingOnTimeAndDay(book.getReadings().get(index).getTime(),
                    book.getReadings().get(index).getDate(), notes);
        } catch (ReadingNotFoundException e) {
            // do nothing, this exception shouldn't be thrown if selecting reading from list
        }
    }


    // MODIFIES: this
    // EFFECTS: perform the given action based on which button is pushed
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add entry")) {
            addEntryPanel = new AddEntryPanel(this);
            changePane(addEntryPanel);
        } else if (e.getActionCommand().equals("add entry to list of entries")) {
            addEntryPanel.addEntryToLogBook(book);
        } else if (e.getActionCommand().equals("calculate averages")) {
            DisplayStatsPanel statsPanel = new DisplayStatsPanel(this);
            changePane(statsPanel);
            displayBarChart();
        } else if (e.getActionCommand().equals("calculate insulin")) {
            CalculatorPanel calculatorPanel = new CalculatorPanel(this);
            changePane(calculatorPanel);
        } else if (e.getActionCommand().equals("save to file")) {
            saveLogBook();
        } else if (e.getActionCommand().equals("load from file")) {
            loadLogBook();
        } else if (e.getActionCommand().equals("return to menu")) {
            backToMain();
        } else if (e.getActionCommand().equals("before only")) {
            makeLogBook(false, "before meal");
        } else if (e.getActionCommand().equals("after only")) {
            makeLogBook(false, "after meal");
        } else if (e.getActionCommand().equals("fasting only")) {
            makeLogBook(false, "fasting");
        } else if (e.getActionCommand().equals("all")) {
            makeLogBook(false, "none");
        } else if (e.getActionCommand().equals("add notes")) {
            setNotesForSelectedItem();
        } else if (e.getActionCommand().equals("refresh")) {
            SwingUtilities.updateComponentTreeUI(frame);
            makeLogBook(false, "none");
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

    // MODIFIES: this
    // EFFECTS: changes the contents of the frame to the given panel
    private void changePane(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: changes the content of the frame back to the main menu and logbook display
    private void backToMain() {
        frame.getContentPane().removeAll();
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.getContentPane().add(mainMenu);
        makeLogBook(false, "none");
        SwingUtilities.updateComponentTreeUI(frame);

    }

    // MODIFIES: this
    // EFFECTS: shows bar chart
    public void displayBarChart() {
        ChartPanel chart = new ChartPanel(this);
        chart.showBarChart();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLog(EventLog.getInstance());
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("deactivated");

    }

    @Override
    public void windowGainedFocus(WindowEvent e) {

    }

    @Override
    public void windowLostFocus(WindowEvent e) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {

    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.getDescription());
        }
    }
}
