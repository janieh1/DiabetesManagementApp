package ui;

import model.LogBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Code here is modelled after Simple bar chart demo at
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/Simplebarchart.htm
 */
public class ChartPanel extends JPanel {
    private LogBook book;
    private ManagerGUI gui;
    private List<Double> values;
    private List<String> names;
    private String title;
    private ArrayList<Color> colours;
    DecimalFormat df = new DecimalFormat("#.##");

    public ChartPanel(ManagerGUI gui) {
        this.book = gui.getBook();
        this.gui = gui;
        this.values = new ArrayList<Double>();
        this.values.add(book.calculateTimeInRange().get("low"));
        this.values.add(book.calculateTimeInRange().get("in range"));
        this.values.add(book.calculateTimeInRange().get("high"));
        this.names = new ArrayList<String>();
        this.names.add("low");
        this.names.add("in range");
        this.names.add("high");
        this.title = "Time in Range:\n Low - " + df.format(values.get(0) * 100) + "%\n"
                + "In Range - " + df.format(values.get(1) * 100) + "%\n"
                + "High - " + df.format(values.get(2) * 100) + "%";
    }

    @SuppressWarnings("methodlength")
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        colours = new ArrayList<Color>();
        colours.add(Color.red);
        colours.add(Color.green);
        colours.add(Color.yellow);
        if (values == null || values.size() == 0) {
            return;
        }
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < values.size(); i++) {
            if (minValue > values.get(i)) {
                minValue = values.get(i);
            }
            if (maxValue < values.get(i)) {
                maxValue = values.get(i);
            }
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = clientWidth / values.size();

        Font titleFont = new Font("SansSerif", Font.BOLD, 16);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, x, y);
        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue) {
            return;
        }
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < values.size(); i++) {
            int valueX = i * barWidth + 1;
            int valueY = top;
            int height = (int) (values.get(i) * scale);
            if (values.get(i) >= 0) {
                valueY += (int) ((maxValue - values.get(i)) * scale);
            } else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }

            g.setColor(colours.get(i));
            g.fillRect(valueX, valueY, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 2, height);
            int labelWidth = labelFontMetrics.stringWidth(names.get(i));
            x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(names.get(i), x, y);
        }
    }

    public void showBarChart() {
        JFrame f = gui.getFrame();
        f.getContentPane().add(new ChartPanel(gui));
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        f.addWindowListener(wndCloser);
        f.setVisible(true);
    }

}
