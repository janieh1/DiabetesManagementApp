package model;

import java.util.Scanner;

public class InsulinCalculator {
    double currentSugar;
    double numCarbs;
    int baseBolus;
    double insulinCarbRatio;
    double insulinSensitivity;

    // EFFECTS: constructs new insulin calculator with specified values
    public InsulinCalculator(double sugar, double carbs, int base, double ratio, double sensitivity) {
        currentSugar = sugar;
        numCarbs = carbs;
        baseBolus = base;
        insulinCarbRatio = ratio;
        insulinSensitivity = sensitivity;
    }

    // EFFECTS: returns the amount of insulin to give based on the calculator's current values
    public int calculateInsulin() {
        int insulinToGive = baseBolus;
        if ((currentSugar > 10.9) && (currentSugar <= 14.9)) {
            insulinToGive += 1;
        } else if ((currentSugar > 14.9) && (currentSugar <= 18.9)) {
            insulinToGive += 2;
        } else if (currentSugar > 18.9) {
            insulinToGive += 3;
        }
        if (numCarbs > 30) {
            insulinToGive += insulinCarbRatio * (numCarbs - 30);
        }

        return insulinToGive;
    }
}
