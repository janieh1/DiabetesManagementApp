package model;

import java.util.ArrayList;
import java.util.List;

public class LogBook {
    private ArrayList<BloodSugarReading> readings;

    // EFFECTS: creates new logbook with no readings in it
    public LogBook() {
        this.readings = new ArrayList<BloodSugarReading>();
    }

    public ArrayList getReadings() {
        return readings;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds blood sugar reading to logbook
    public void addReading(BloodSugarReading reading) {
        readings.add(reading);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: calculates overall average blood sugar reading
    public double calculateAverage() {
        int overallSum = 0;
        for (BloodSugarReading r: readings) {
            overallSum += r.getValue();
        }
        return overallSum / readings.size();
    }

}
