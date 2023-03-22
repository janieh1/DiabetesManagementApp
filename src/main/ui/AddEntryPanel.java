package ui;

import model.BloodSugarReading;
import model.LogBook;

import javax.swing.*;
import java.awt.*;

public class AddEntryPanel extends ManagerPanel {
    private JButton add;
//    private JButton returnMainMenu;

    private JTextField value;

    private JTextField years;
    private JComboBox<String> monthsToChoose;
    private JComboBox<String> daysToChoose;

    private JComboBox<String> hoursToChoose;
    private JComboBox<String> minutesToChoose;
    private JComboBox<String> catsToChoose;

    private String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] mins = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private String[] cats = {"fasting", "before meal", "after meal"};

    private JLabel valueLabel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    private JLabel hourLabel;
    private JLabel minLabel;
    private JLabel catLabel;

    // EFFECTS: opens a JPanel with places to add data to add new logbook entry
    public AddEntryPanel(ManagerGUI gui) {
        super(5, 2, gui);
        setPreferredSize(new Dimension(500, 200));
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
        monthsToChoose = new JComboBox<String>(months);
        daysToChoose = new JComboBox<String>(days);
        hoursToChoose = new JComboBox<String>(hours);
        minutesToChoose = new JComboBox<String>(mins);
        catsToChoose = new JComboBox<String>(cats);
        addEverything();
        setOpaque(true);
    }

    @Override
    protected void addEverything() {
        add(value);
        add(valueLabel);
        add(years);
        add(yearLabel);
        add(monthsToChoose);
        add(monthLabel);
        add(daysToChoose);
        add(dayLabel);
        add(hoursToChoose);
        add(hourLabel);
        add(minutesToChoose);
        add(minLabel);
        add(catsToChoose);
        add(catLabel);
        add(add);
//        add(returnMainMenu);
    }

    public void addEntryToLogBook(LogBook book, ManagerGUI gui) {
        BloodSugarReading bsr = new BloodSugarReading(Double.parseDouble(value.getText()),
                years.getText() + "-" + monthsToChoose.getSelectedItem() + "-" + daysToChoose.getSelectedItem(),
                hoursToChoose.getSelectedItem() + ":" + minutesToChoose.getSelectedItem(),
                (String) catsToChoose.getSelectedItem());
        book.addReading(bsr);
    }


}
