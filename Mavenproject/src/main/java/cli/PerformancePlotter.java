package cli;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class PerformancePlotter {

    public static void main(String[] args) throws IOException {
        String fileName = "results.csv";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String header = reader.readLine(); // пропускаем заголовок
            if (header == null) throw new IOException("Empty results file");

            Map<Integer, List<Double>> grouped = new TreeMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                int n = Integer.parseInt(parts[0]);
                double time = Double.parseDouble(parts[3]);
                grouped.computeIfAbsent(n, k -> new ArrayList<>()).add(time);
            }

            // усредняем результаты по каждому n
            for (Map.Entry<Integer, List<Double>> e : grouped.entrySet()) {
                double avg = e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                dataset.addValue(avg, "Execution Time (ms)", e.getKey());
            }
        }

        // создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "MaxHeap Performance (Time vs Input Size)",
                "Input Size (n)",
                "Average Execution Time (ms)",
                dataset
        );

        // отображаем график
        JFrame frame = new JFrame("Performance Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);

        // сохраняем график в PNG
        File file = new File("heap_performance.png");
        ChartUtils.saveChartAsPNG(file, chart, 800, 600);
        System.out.println("Graph saved as: " + file.getAbsolutePath());
    }
}
