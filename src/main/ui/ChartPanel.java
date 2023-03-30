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
 * <a href="http://www.java2s.com/Code/Java/2D-Graphics-GUI/Simplebarchart.htm">...</a>
 */

// represents the bar chart that is displayed in the DisplayStatsPanel
public class ChartPanel extends JPanel {
    private final ManagerGUI gui;
    private List<Double> values;
    private final List<String> names;
    private String title;
    DecimalFormat df = new DecimalFormat("#.##");
    private final ArrayList<Color> colours = new ArrayList<Color>();

    // MODIFIES: this
    // EFFECTS: constructs new bar chart with categories and title
    public ChartPanel(ManagerGUI gui) {
        LogBook book = gui.getBook();
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
    // MODIFIES: this
    // EFFECTS: draws bars on bar chart
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        colours.add(Color.red);
        colours.add(Color.green);
        colours.add(Color.yellow);

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
        double scale = (clientHeight - top - bottom);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < values.size(); i++) {
            int valueX = i * barWidth + 1;
            int height = (int) (values.get(i) * scale);
            int valueY = (int) (top + ((1 - values.get(i)) * scale));

            g.setColor(colours.get(i));
            g.fillRect(valueX, valueY, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 2, height);
            int labelWidth = labelFontMetrics.stringWidth(names.get(i));
            x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(names.get(i), x, y);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the bar chart on the panel
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
