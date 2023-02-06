package ui;

import model.BloodSugarReading;
import model.InsulinCalculator;
import model.LogBook;

import java.util.Scanner;

public class DiabetesManager {
    private Scanner scanner;
    String operation = "";
    LogBook book = new LogBook();

    public DiabetesManager() {
        process();

    }

    public void process() {

        while (true) {

            System.out.println("What would you like to do? \n 1. Add blood sugar reading \n 2. View my log book"
                    + "\n 3. Calculate my insulin dosage \n 4. Calculate my average blood sugars \n 5. Quit");
            scanner = new Scanner(System.in);
            operation = scanner.nextLine();

            if (operation.equals("1")) {
                getReadingFromUser();

            } else if (operation.equals("2")) {
                System.out.println("Here's your logbook: ");

            } else if (operation.equals("3")) {
                new InsulinCalculator();

            } else if (operation.equals("4")) {
                book.calculateAverage();

            } else if (operation.equals("5")) {
                break;
            }
        }

    }

    private void getReadingFromUser() {
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        System.out.println("Enter blood sugar value (in mmol/L): ");
        double val = Double.parseDouble(s1.nextLine());
        System.out.println("Enter date (MM-DD-YYYY): ");
        String date = s2.nextLine();
        System.out.println("Enter time (HH:MM in 24 hour format): ");
        String time = s3.nextLine();
        book.addReading(new BloodSugarReading(val, date, time));

    }
}
