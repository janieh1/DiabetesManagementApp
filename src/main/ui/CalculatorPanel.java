package ui;

import model.InsulinCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends ManagerPanel implements ActionListener {
    private InsulinCalculator calculator;
    private JButton calculate;

    private JTextField currentBloodSugar;
    private JTextField insulinCarbRatio;
    private JTextField base;
    private JTextField carbs;
    private JTextField insulinSensitivity;

    private JLabel currentSugarLabel;
    private JLabel ratioLabel;
    private JLabel baseLabel;
    private JLabel carbLabel;
    private JLabel sensitivityLabel;

    public CalculatorPanel(ManagerGUI gui) {
        super(2, 5, gui);
        setPreferredSize(new Dimension(500, 350));
        currentSugarLabel = new JLabel("Current Blood Sugar (mmol/L):");
        ratioLabel = new JLabel("Insulin to carb ratio (units per 1 gram):");
        baseLabel = new JLabel("Base Insulin:");
        carbLabel = new JLabel("Grams of carbs in food:");
        sensitivityLabel = new JLabel("Insulin Sensitivity Factor (units per mmol/L):");
        currentBloodSugar = new JTextField(5);
        insulinCarbRatio = new JTextField(5);
        base = new JTextField(5);
        carbs = new JTextField(5);
        insulinSensitivity = new JTextField(5);

        calculate = new JButton("Calculate");
        calculate.setActionCommand("calculate");
        calculate.addActionListener(this);

        addReturnMainMenuButton();
        addEverything();
    }

    @Override
    protected void addEverything() {
        add(currentSugarLabel);
        add(currentBloodSugar);
        add(ratioLabel);
        add(insulinCarbRatio);
        add(baseLabel);
        add(base);
        add(carbLabel);
        add(carbs);
        add(sensitivityLabel);
        add(insulinSensitivity);
        add(calculate);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("calculate")) {
            calculator = new InsulinCalculator(Double.parseDouble(currentBloodSugar.getText()),
                    Double.parseDouble(carbs.getText()), Integer.parseInt(base.getText()),
                    Double.parseDouble(insulinCarbRatio.getText()), Double.parseDouble(insulinSensitivity.getText()));
            int insulinToGive = calculator.calculateInsulin();
            add(new JLabel("Insulin to give: " + insulinToGive + " units"));
            SwingUtilities.updateComponentTreeUI(this);
        }

    }
}
