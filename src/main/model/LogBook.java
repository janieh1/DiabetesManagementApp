package model;

import java.util.ArrayList;

public class LogBook {
    private ArrayList<BloodSugarReading> readings;

    // EFFECTS: creates new logbook with no readings in it
    public LogBook() {
        this.readings = new ArrayList<BloodSugarReading>();
    }

    // EFFECTS: gets list of readings in log book
    public ArrayList getReadings() {
        return readings;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds blood sugar reading to logbook
    public void addReading(BloodSugarReading reading) {
        readings.add(reading);
    }

    public ArrayList<BloodSugarReading> getReadingsInCategory(String category) {
        ArrayList<BloodSugarReading> readingsInCategory = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getCategory().equals(category)) {
                readingsInCategory.add(bsr);
            }
        }
        return readingsInCategory;
    }

    public ArrayList<Double> getValuesInCategory(String category) {
        ArrayList<Double> valuesInCat = new ArrayList<Double>();
        for (BloodSugarReading bsr : getReadingsInCategory(category)) {
            valuesInCat.add(bsr.getValue());
        }
        return valuesInCat;
    }

    // EFFECTS: calculates overall average blood sugar reading
    public double calculateOverallAverage() {
        double overallSum = 0;
        if (readings.size() == 0) {
            return 0.0;
        } else {
            for (BloodSugarReading r : readings) {
                overallSum += r.getValue();
            }
        }
        return (overallSum / readings.size());
    }

    public double calculateAverageOfCategory(String category) {
        double sum = 0;
        if (getReadingsInCategory(category).size() == 0) {
            return 0.0;
        } else {
            for (BloodSugarReading bsr : getReadingsInCategory(category)) {
                sum += bsr.getValue();
            }
        }
        return (sum / getReadingsInCategory(category).size());
    }

    // REQUIRES: date is in the form YYYY-MM-DD
    // EFFECTS: retrieves all reading value(s) on a given date
    public ArrayList getReadingValuesAndTimeFromDay(String date) {
        ArrayList readingsOnDate = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getDate().equals(date)) {
                readingsOnDate.add(bsr);
            }
        }
        return readingsOnDate;
    }

    // REQUIRES: category is one of: "before meal" "after meal" or "fasting"
    // EFFECTS: retrieves all reading value(s) with given category
    public ArrayList getReadingValuesFromCategory(String category) {
        ArrayList readingsFromCat = new ArrayList<BloodSugarReading>();
        for (BloodSugarReading bsr : readings) {
            if (bsr.getCategory().equals(category)) {
                readingsFromCat.add(bsr);
            }
        }
        return readingsFromCat;
    }
}
