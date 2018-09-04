import org.jfree.ui.ApplicationFrame;
import org.junit.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class Main extends ApplicationFrame {
    private static final int COUNT = 5;

    private static int[] Sentence = new int[COUNT + 1];
    private static int[] EncodedMessage = new int[COUNT + 1];
    private static int p = 1;
    private static int k = 1;
    private static int j = 0;


    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        writeRandomWordInFile(COUNT);
        readRandomWordInFile(COUNT);
        Main appFrame = new Main("مقایسه ی ذخیره ی داده های کد گذاری شده");
        appFrame.pack();
        RefineryUtilities.centerFrameOnScreen(appFrame);
        appFrame.setVisible(true);
    }

    private static void writeRandomWordInFile(int count) {
        RandomFile randomFile = new RandomFile();
        randomFile.writeRandomWordsToFile(1);
        String s = "1";
        for (int i = 0; i < count; i++) {
            s += "0";
            randomFile.writeRandomWordsToFile(Integer.parseInt(s));
        }
    }

    private static void readRandomWordInFile(int count) throws IOException, ClassNotFoundException {
        readFromFile("D:/Programming/Java/IntelliJ IDEA/Huffman/file/files/1.txt");

        String s = "1";
        for (int i = 0; i < count; i++) {
            s += "0";
            readFromFile("D:/Programming/Java/IntelliJ IDEA/Huffman/file/files/" + s + ".txt");
        }
    }

    private static void readFromFile(String path) throws IOException, ClassNotFoundException {
        String file = new String(Files.readAllBytes(Paths.get(path)));

        Huffman.compress(file);
        Assert.assertEquals(file, HuffmanDecode.deCode());

        fileSize();
    }

    private static void fileSize() {
        File sentence = new File("D:/Programming/Java/IntelliJ IDEA/Huffman/file/sentence.txt");
        System.out.println(sentence.length());
        Sentence[j] = (int) sentence.length();

        File encodedMessage = new File("D:/Programming/Java/IntelliJ IDEA/Huffman/file/encodedMessage");
        System.out.println(encodedMessage.length());
        EncodedMessage[j] = (int) encodedMessage.length();

        System.out.println();
        j++;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Main(String title) {
        super(title);
        JPanel content = createPanel();
        getContentPane().add(content);
    }

    private JPanel createPanel() {
        return new MyPanel();
    }

    private class MyPanel extends Panel {

        private XYDataset data;

        private MyPanel() {
            super(new BorderLayout());
            this.data = createSampleData();
            add(createContent());
        }

        private XYDataset createSampleData() {
            XYSeries series = new XYSeries("huffman");
            for (int i = 0; i < Sentence.length; i++) {
                series.add(p, Sentence[i]);
                p *= 10;
            }

            XYSeriesCollection result = new XYSeriesCollection(series);
            XYSeries series2 = new XYSeries("improveHuffman");
            for (int i = 0; i < Sentence.length; i++) {
                series2.add(k, EncodedMessage[i]);
                k *= 10;
            }
            result.addSeries(series2);
            return result;
        }

        private JTabbedPane createContent() {
            JTabbedPane tabs = new JTabbedPane();
            tabs.add("نمودار مقایسه دو الگو", createChartPanel());
            return tabs;
        }

        private ChartPanel createChartPanel() {

            NumberAxis xAxis = new NumberAxis("Number");
            xAxis.setAutoRangeIncludesZero(false);
            NumberAxis yAxis = new NumberAxis("Size");
            yAxis.setAutoRangeIncludesZero(false);

            XYSplineRenderer renderer1 = new XYSplineRenderer();
            XYPlot plot = new XYPlot(this.data, xAxis, yAxis, renderer1);
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);
            plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));

            JFreeChart chart = new JFreeChart("Algorithm Huffman", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            addChart(chart);
            ChartUtilities.applyCurrentTheme(chart);
            ChartPanel chartPanel = new ChartPanel(chart);
            return chartPanel;
        }

    }
}
