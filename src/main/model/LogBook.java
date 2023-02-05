package model;

import java.util.ArrayList;
import java.util.List;

public class LogBook {
    private ArrayList<BloodSugarReading> readings;

    public LogBook() {
        this.readings = new ArrayList<BloodSugarReading>();
    }

    public void addReading(BloodSugarReading reading) {
        readings.add(reading);
    }

    public void getReadings() {
        for (BloodSugarReading r : readings) {
            r.viewReading();
        }
    }
}
