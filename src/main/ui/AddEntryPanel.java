package ui;

import model.BloodSugarReading;
import model.LogBook;

import javax.swing.*;

// represents the window that opens when a user wants to add an entry to their logbook
public class AddEntryPanel extends ManagerPanel {
    private JButton add;

    private JTextField value;

    private JTextField years;
    private final JComboBox<String> monthsToChoose;
    private final JComboBox<String> daysToChoose;

    private final JComboBox<String> hoursToChoose;
    private final JComboBox<String> minutesToChoose;
    private final JComboBox<String> catsToChoose;

    private JLabel valueLabel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    private JLabel hourLabel;
    private JLabel minLabel;
    private JLabel catLabel;

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: opens a JPanel with places to add data to add new logbook entry
    public AddEntryPanel(ManagerGUI gui) {
        super(5, 2, gui);
        valueLabel = new JLabel("Value in mmol/L");
        yearLabel = new JLabel("Year");
        monthLabel = new JLabel("Month");
        dayLabel = new JLabel("Day");
        hourLabel = new JLabel("Hour");
        minLabel = new JLabel("Minute");
        catLabel = new JLabel("Category");
        add = new JButton("Add");
        add.setActionCommand("add entry to list of entries");
        add.addActionListener(gui);
        addReturnMainMenuButton();

        value = new JTextField(5);
        years = new JTextField(4);
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        monthsToChoose = new JComboBox<String>(months);
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        daysToChoose = new JComboBox<String>(days);
        String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        hoursToChoose = new JComboBox<String>(hours);
        String[] mins = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        minutesToChoose = new JComboBox<String>(mins);
        String[] cats = {"fasting", "before meal", "after meal"};
        catsToChoose = new JComboBox<String>(cats);
        addEverything();
        setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to the panel
    @Override
    protected void addEverything() {
        add(valueLabel);
        add(value);
        add(yearLabel);
        add(years);
        add(monthLabel);
        add(monthsToChoose);
        add(dayLabel);
        add(daysToChoose);
        add(hourLabel);
        add(hoursToChoose);
        add(minLabel);
        add(minutesToChoose);
        add(catLabel);
        add(catsToChoose);
        add(add);
    }

    // MODIFIES: logbook, jframe main menu
    // EFFECTS: adds entry to logbook and updates logbook display to show newly added reading
    public void addEntryToLogBook(LogBook book) {
        BloodSugarReading bsr = new BloodSugarReading(Double.parseDouble(value.getText()),
                years.getText() + "-" + monthsToChoose.getSelectedItem() + "-" + daysToChoose.getSelectedItem(),
                hoursToChoose.getSelectedItem() + ":" + minutesToChoose.getSelectedItem(),
                (String) catsToChoose.getSelectedItem());
        book.addReading(bsr);
    }


}
