package ui;

import model.LogBook;

import javax.swing.*;
import java.text.DecimalFormat;

// represents the window that pops up when a user wants to see their stats
public class DisplayStatsPanel extends ManagerPanel {
    private JLabel overallAverage;
    private JLabel fastingAverage;
    private JLabel beforeMealAverage;
    private JLabel afterMealAverage;
    private JLabel timeInRange;
    DecimalFormat df = new DecimalFormat("#.##");



    // MODIFES: this
    // EFFECTS: constructs a panel to display stats
    public DisplayStatsPanel(ManagerGUI gui) {
        super(2, 1, gui);
        LogBook book = gui.getBook();
        overallAverage = new JLabel("Overall average: " + df.format(book.calculateOverallAverage()) + " mmol/L\n");
        fastingAverage = new JLabel("Fasting average: " + df.format(book.calculateAverageOfCategory("fasting"))
                + " mmol/L\n");
        beforeMealAverage = new JLabel("Before meal average: "
                + df.format(book.calculateAverageOfCategory("before meal")) + " mmol/L\n");
        afterMealAverage = new JLabel("After meal average: "
                + df.format(book.calculateAverageOfCategory("after meal")) + " mmol/L\n");
        timeInRange = new JLabel("Time in Range: "
                + df.format(100 * book.calculateTimeInRange().get("in range")) + "%");
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
