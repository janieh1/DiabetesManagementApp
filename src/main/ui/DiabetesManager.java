package ui;

import exceptions.ReadingNotFoundException;
import model.BloodSugarReading;
import model.InsulinCalculator;
import model.LogBook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Diabetes Manager menu to choose task
public class DiabetesManager {
    // private static final String JSON_STORE = "./data/logbook.json";
    private static final String JSON_STORE = "./data/logbook.json";
    private Scanner scanner;
    String operation = "";
    double currentSugar;
    double numCarbs;
    int baseBolus;
    double insulinCarbRatio;
    double insulinSensitivity;
    LogBook book = new LogBook();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public DiabetesManager() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        process();
    }

    // MODIFIES: this
    // EFFECTS: opens DiabatesManager menu
    @SuppressWarnings("methodlength")
    public void process() {

        while (true) {
            System.out.println("What would you like to do? \n 1. Add blood sugar reading \n 2. View all my readings "
                    + "from a certain category \n 3. Calculate my insulin dosage \n 4. View my average "
                    + "blood sugars \n 5. Add notes to given reading \n 6. Save logbook to file"
                    + "\n 7. Load logbook from file \n 8. Quit \n");

            scanner = new Scanner(System.in);
            operation = scanner.nextLine();
            if (operation.equals("1")) {
                getReadingFromUser();
            } else if (operation.equals("2")) {
                displayReadingsInInputCategory();
            } else if (operation.equals("3")) {
                calculateInsulinFromInput();
            } else if (operation.equals("4")) {
                displayAverages();
            } else if (operation.equals("5")) {
                addNotes();
            } else if (operation.equals("6")) {
                saveLogBook();
            } else if (operation.equals("7")) {
                loadLogBook();
            } else if (operation.equals("8")) {
                break;
            }
        }
    }


    // MODIFIES: book
    // EFFECTS: takes input from user and adds blood sugar reading to logbook
    private void getReadingFromUser() {
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        Scanner s4 = new Scanner(System.in);
        System.out.println("Enter blood sugar value (in mmol/L): ");
        double val = Double.parseDouble(s1.nextLine());
        System.out.println("Enter date (YYYY-MM-DD): ");
        String date = s2.nextLine();
        System.out.println("Enter time (HH:MM in 24 hour format): ");
        String time = s3.nextLine();
        System.out.println("Enter category (must be one of: before meal, after meal, fasting): ");
        String cat = s4.nextLine();
        book.addReading(new BloodSugarReading(val, date, time, cat));

    }

    // EFFECTS: takes input from user and calculates how much insulin to give
    private int calculateInsulinFromInput() {
        scanner = new Scanner(System.in);
        System.out.println("Enter current blood sugar in mmol/L: \n");
        currentSugar = scanner.nextDouble();
        System.out.println("Enter number of carbs in meal in grams: \n");
        numCarbs = scanner.nextDouble();
        System.out.println("Enter base bolus in units of insulin: \n");
        baseBolus = scanner.nextInt();
        System.out.println("Enter insulin to carb ratio in units/grams: \n");
        insulinCarbRatio = scanner.nextDouble();
        System.out.println("Enter insulin sensitivity in units/(mmol/L): \n");
        insulinSensitivity = scanner.nextDouble();

        InsulinCalculator calculator = new InsulinCalculator(currentSugar, numCarbs, baseBolus, insulinCarbRatio,
                insulinSensitivity);

        System.out.println("Insulin to give: " + calculator.calculateInsulin() + " units");
        return calculator.calculateInsulin();

    }

    // EFFECTS: displays readings in terminal
    private void displayReadingsInInputCategory() {
        scanner = new Scanner(System.in);
        System.out.println("Which category (before meal, after meal, fasting)? \n");
        String cat = scanner.nextLine();
        System.out.println("Here are your readings in the " + cat + " category: \n" + book.getValuesInCategory(cat));
    }

    // EFFECTS: displays averages in terminal
    public void displayAverages() {
        System.out.println("Overall average: " + book.calculateOverallAverage() + " mmol/L\n");
        System.out.println("Fasting average: " + book.calculateAverageOfCategory("fasting") + " mmol/L\n");
        System.out.println("Before meal average: " + book.calculateAverageOfCategory("before meal")
                + " mmol/L\n");
        System.out.println("After meal average: " + book.calculateAverageOfCategory("after meal")
                + " mmol/L\n");
    }

    // MODIFIES: book, blood sugar reading
    // EFFECTS: adds notes to given reading on time and date. if incorrect date/time entered, user can try again
    private void addNotes() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter date of reading: \n");
            String date = s.nextLine();
            System.out.println("Enter time: \n");
            String time = s.nextLine();
            System.out.println("Notes to add: \n");
            String notes = s.nextLine();
            book.setNotesOfReadingOnTimeAndDay(time, date, notes);
        } catch (ReadingNotFoundException e) {
            System.out.println("Reading not found at given date and time");
            process();
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
            System.out.println("Loaded logbook from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
