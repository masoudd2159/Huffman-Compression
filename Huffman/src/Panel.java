import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Panel extends JPanel {

    List charts;

    public Panel(LayoutManager layout) {
        super(layout);
        this.charts = new java.util.ArrayList();
    }

    public void addChart(JFreeChart chart) {
        this.charts.add(chart);
    }

    public JFreeChart[] getCharts() {
        int chartCount = this.charts.size();
        JFreeChart[] charts = new JFreeChart[chartCount];
        for (int i = 0; i < chartCount; i++) {
            charts[i] = (JFreeChart) this.charts.get(i);
        }
        return charts;
    }

}