package model;

public class BloodSugarReading {
    private double readingValue;
    private String timeOfDay;

    // REQUIRES: category must be one of: "fasting", "before meal", or "after meal"
    // EFFECTS: constructs a blood sugar reading with specified value and time of day
    public BloodSugarReading(double value, String category) {
        this.readingValue = value;
        this.timeOfDay = category;
    }

    public void viewReading() {
        System.out.println("Reading: " + readingValue + " mmol/L at time " + timeOfDay);
    }
}
