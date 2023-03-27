package ui;

import model.LogBook;

import javax.swing.*;

// represents the window that pops up when a user wants to see their stats
public class DisplayStatsPanel extends ManagerPanel {
    private JLabel overallAverage;
    private JLabel fastingAverage;
    private JLabel beforeMealAverage;
    private JLabel afterMealAverage;
    private JLabel timeInRange;


    // MODIFES: this
    // EFFECTS: constructs a panel to display stats
    public DisplayStatsPanel(ManagerGUI gui) {
        super(2, 1, gui);
        LogBook book = gui.getBook();
        overallAverage = new JLabel("Overall average: " + book.calculateOverallAverage() + " mmol/L\n");
        fastingAverage = new JLabel("Fasting average: " + book.calculateAverageOfCategory("fasting") + " mmol/L\n");
        beforeMealAverage = new JLabel("Before meal average: " + book.calculateAverageOfCategory("before meal")
                + " mmol/L\n");
        afterMealAverage = new JLabel("After meal average: " + book.calculateAverageOfCategory("after meal")
                + " mmol/L\n");
        timeInRange = new JLabel("Time in Range: " + 100 * book.calculateTimeInRange().get("in range") + "%");
        addReturnMainMenuButton();
        addEverything();
        setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to the panel
    @Override
    protected void addEverything() {
        add(overallAverage);
        add(fastingAverage);
        add(beforeMealAverage);
        add(afterMealAverage);
        add(timeInRange);
    }


}
