package ui;

import model.BloodSugarReading;
import model.InsulinCalculator;
import model.LogBook;

import java.util.Scanner;

public class DiabetesManager {
    private Scanner scanner;
    String operation = "";
    double currentSugar;
    double numCarbs;
    int baseBolus;
    double insulinCarbRatio;
    double insulinSensitivity;
    LogBook book = new LogBook();

    public DiabetesManager() {
        process();
    }

    public void process() {

        while (true) {
            System.out.println("What would you like to do? \n 1. Add blood sugar reading \n 2. View all my readings "
                    + "from a certain category \n 3. Calculate my insulin dosage \n 4. View my average "
                    + "blood sugars \n 5. Add notes to most recently added reading \n 6. Quit");
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
                Scanner s = new Scanner(System.in);
                System.out.println("Notes to add: \n");
                String notes = s.nextLine();
                book.addNotesToLastReading(notes);
            } else if (operation.equals("6")) {
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

    private void displayReadingsInInputCategory() {
        scanner = new Scanner(System.in);
        System.out.println("Which category (before meal, after meal, fasting)? \n");
        String cat = scanner.nextLine();
        System.out.println("Here are your readings in the " + cat + " category: \n" + book.getValuesInCategory(cat));
    }

    public void displayAverages() {
        System.out.println("Overall average: " + book.calculateOverallAverage() + " mmol/L\n");
        System.out.println("Fasting average: " + book.calculateAverageOfCategory("fasting") + " mmol/L\n");
        System.out.println("Before meal average: " + book.calculateAverageOfCategory("before meal")
                + " mmol/L\n");
        System.out.println("After meal average: " + book.calculateAverageOfCategory("after meal")
                + " mmol/L\n");
    }
}
