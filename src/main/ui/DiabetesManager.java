package ui;

import model.BloodSugarReading;
import model.InsulinCalculator;
import model.LogBook;

import java.util.Scanner;

public class DiabetesManager {
    private Scanner scanner;
    String operation = "";

    public DiabetesManager() {
        process();

    }

    public void process() {
        LogBook book = new LogBook();

        while (true) {

            System.out.println("What would you like to do? \n 1. Add blood sugar reading \n 2. View my log book"
                    + "\n 3. Calculate my insulin dosage \n 4. Quit");
            scanner = new Scanner(System.in);
            operation = scanner.nextLine();

            if (operation.equals("1")) {
                Scanner s1 = new Scanner(System.in);
                Scanner s2 = new Scanner(System.in);
                System.out.println("Enter blood sugar value (in mmol/L): ");
                double val = Double.parseDouble(s1.nextLine());
                System.out.println("Enter date and time (MM-DD-YYYY, HH:MM (24 hour format)): ");
                String time = s2.nextLine();
                book.addReading(new BloodSugarReading(val, time));

            } else if (operation.equals("2")) {
                System.out.println("Here's your logbook: ");
                book.getReadings();

            } else if (operation.equals("3")) {
                new InsulinCalculator();

            } else if (operation.equals("4")) {
                break;
            }
        }

    }
}
