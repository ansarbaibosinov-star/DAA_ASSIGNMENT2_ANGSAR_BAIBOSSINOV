package com.daa.assignment2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlotGenerator {

    private static final String CSV_FILE = "results/results.csv";
    private static final String PLOTS_DIR = "results/plots";

    /*
     * Real benchmark sizes.
     */
    private static final int[] SIZES = {
            100,
            1000,
            10000,
            100000
    };

    /*
     * Labels displayed on the X-axis.
     *
     * 0 is included as the starting point.
     * There is no actual benchmark for n = 0.
     */
    private static final String[] X_LABELS = {
            "0",
            "100",
            "1000",
            "10000",
            "100000"
    };

    public void generateAllPlots() throws IOException {

        File plotsDirectory = new File(PLOTS_DIR);

        if (!plotsDirectory.exists()) {
            if (!plotsDirectory.mkdirs()) {
                throw new IOException(
                        "Could not create plots directory."
                );
            }
        }

        List<BenchmarkResult> results = readResults();

        generateW1Time(results);
        generateW1Operations(results);

        generateW2Time(results);
        generateW2Operations(results);

        generateW3Time(results);
        generateW3Operations(results);

        generateW4Time(results);
        generateW4Operations(results);

        System.out.println("All plots generated successfully.");
    }

    // ============================================================
    // W1 - RANDOM ACCESS
    // ============================================================

    private void generateW1Time(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addTimeSeries(
                dataset,
                results,
                "W1",
                "DynamicArray",
                "-"
        );

        addTimeSeries(
                dataset,
                results,
                "W1",
                "MyLinkedList",
                "-"
        );

        JFreeChart chart = createLineChart(
                "W1 - Random Access: Time vs n",
                "Input size n",
                "Time (ms)",
                dataset
        );

        saveChart(chart, "W1_time.png");
    }

    private void generateW1Operations(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addMetricSeries(
                dataset,
                results,
                "W1",
                "DynamicArray",
                "-",
                "Steps"
        );

        addMetricSeries(
                dataset,
                results,
                "W1",
                "MyLinkedList",
                "-",
                "Steps"
        );

        JFreeChart chart = createLineChart(
                "W1 - Random Access: Steps vs n",
                "Input size n",
                "Steps",
                dataset
        );

        saveChart(chart, "W1_operations.png");
    }

    // ============================================================
    // W2 - SEARCH
    // ============================================================

    private void generateW2Time(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addTimeSeries(
                dataset,
                results,
                "W2",
                "DynamicArray",
                "-"
        );

        addTimeSeries(
                dataset,
                results,
                "W2",
                "MyLinkedList",
                "-"
        );

        JFreeChart chart = createLineChart(
                "W2 - Search: Time vs n",
                "Input size n",
                "Time (ms)",
                dataset
        );

        saveChart(chart, "W2_time.png");
    }

    private void generateW2Operations(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addMetricSeries(
                dataset,
                results,
                "W2",
                "DynamicArray",
                "-",
                "Steps"
        );

        addMetricSeries(
                dataset,
                results,
                "W2",
                "MyLinkedList",
                "-",
                "Steps"
        );

        addMetricSeries(
                dataset,
                results,
                "W2",
                "DynamicArray",
                "-",
                "Comparisons"
        );

        addMetricSeries(
                dataset,
                results,
                "W2",
                "MyLinkedList",
                "-",
                "Comparisons"
        );

        JFreeChart chart = createLineChart(
                "W2 - Search: Steps and Comparisons vs n",
                "Input size n",
                "Operations",
                dataset
        );

        saveChart(chart, "W2_operations.png");
    }

    // ============================================================
    // W3 - INSERT & REMOVE
    // ============================================================

    private void generateW3Time(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addTimeSeries(
                dataset,
                results,
                "W3",
                "DynamicArray",
                "head"
        );

        addTimeSeries(
                dataset,
                results,
                "W3",
                "MyLinkedList",
                "head"
        );

        addTimeSeries(
                dataset,
                results,
                "W3",
                "DynamicArray",
                "middle"
        );

        addTimeSeries(
                dataset,
                results,
                "W3",
                "MyLinkedList",
                "middle"
        );

        JFreeChart chart = createLineChart(
                "W3 - Insert & Remove: Time vs n",
                "Input size n",
                "Time (ms)",
                dataset
        );

        saveChart(chart, "W3_time.png");
    }

    private void generateW3Operations(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addTotalOperationsSeries(
                dataset,
                results,
                "W3",
                "DynamicArray",
                "head"
        );

        addTotalOperationsSeries(
                dataset,
                results,
                "W3",
                "MyLinkedList",
                "head"
        );

        addTotalOperationsSeries(
                dataset,
                results,
                "W3",
                "DynamicArray",
                "middle"
        );

        addTotalOperationsSeries(
                dataset,
                results,
                "W3",
                "MyLinkedList",
                "middle"
        );

        JFreeChart chart = createLineChart(
                "W3 - Insert & Remove: Operations vs n",
                "Input size n",
                "Steps + Moves + Comparisons",
                dataset
        );

        saveChart(chart, "W3_operations.png");
    }

    // ============================================================
    // W4 - PRIORITY PROCESSING
    // ============================================================

    private void generateW4Time(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addTimeSeries(
                dataset,
                results,
                "W4",
                "MinHeap",
                "-"
        );

        JFreeChart chart = createLineChart(
                "W4 - Priority Processing: Time vs n",
                "Input size n",
                "Time (ms)",
                dataset
        );

        saveChart(chart, "W4_time.png");
    }

    private void generateW4Operations(
            List<BenchmarkResult> results
    ) throws IOException {

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        addMetricSeries(
                dataset,
                results,
                "W4",
                "MinHeap",
                "-",
                "Steps"
        );

        addMetricSeries(
                dataset,
                results,
                "W4",
                "MinHeap",
                "-",
                "Moves"
        );

        addMetricSeries(
                dataset,
                results,
                "W4",
                "MinHeap",
                "-",
                "Comparisons"
        );

        JFreeChart chart = createLineChart(
                "W4 - Priority Processing: Operations vs n",
                "Input size n",
                "Operations",
                dataset
        );

        saveChart(chart, "W4_operations.png");
    }

    // ============================================================
    // ADD TIME SERIES
    // ============================================================

    private void addTimeSeries(
            DefaultCategoryDataset dataset,
            List<BenchmarkResult> results,
            String workload,
            String structure,
            String variant
    ) {

        String seriesName = structure;

        if (workload.equals("W3")) {
            seriesName =
                    structure + " - " + variant;
        }

        /*
         * Add starting point 0.
         *
         * There is no actual benchmark for n = 0,
         * therefore the value is null.
         */
        dataset.addValue(
                null,
                seriesName,
                X_LABELS[0]
        );

        /*
         * Add real benchmark values.
         */
        for (int i = 0; i < SIZES.length; i++) {

            int n = SIZES[i];

            BenchmarkResult result =
                    findResult(
                            results,
                            workload,
                            structure,
                            variant,
                            n
                    );

            if (result != null) {

                dataset.addValue(
                        result.getTimeMs(),
                        seriesName,
                        X_LABELS[i + 1]
                );
            }
        }
    }

    // ============================================================
    // ADD METRIC SERIES
    // ============================================================

    private void addMetricSeries(
            DefaultCategoryDataset dataset,
            List<BenchmarkResult> results,
            String workload,
            String structure,
            String variant,
            String metric
    ) {

        String seriesName =
                structure + " - " + metric;

        dataset.addValue(
                null,
                seriesName,
                X_LABELS[0]
        );

        for (int i = 0; i < SIZES.length; i++) {

            int n = SIZES[i];

            BenchmarkResult result =
                    findResult(
                            results,
                            workload,
                            structure,
                            variant,
                            n
                    );

            if (result != null) {

                long value =
                        getMetricValue(
                                result,
                                metric
                        );

                dataset.addValue(
                        value,
                        seriesName,
                        X_LABELS[i + 1]
                );
            }
        }
    }

    // ============================================================
    // ADD TOTAL OPERATIONS SERIES
    // ============================================================

    private void addTotalOperationsSeries(
            DefaultCategoryDataset dataset,
            List<BenchmarkResult> results,
            String workload,
            String structure,
            String variant
    ) {

        String seriesName =
                structure + " - " + variant;

        dataset.addValue(
                null,
                seriesName,
                X_LABELS[0]
        );

        for (int i = 0; i < SIZES.length; i++) {

            int n = SIZES[i];

            BenchmarkResult result =
                    findResult(
                            results,
                            workload,
                            structure,
                            variant,
                            n
                    );

            if (result != null) {

                long total =
                        result.getSteps()
                                + result.getMoves()
                                + result.getComparisons();

                dataset.addValue(
                        total,
                        seriesName,
                        X_LABELS[i + 1]
                );
            }
        }
    }

    // ============================================================
    // FIND RESULT
    // ============================================================

    private BenchmarkResult findResult(
            List<BenchmarkResult> results,
            String workload,
            String structure,
            String variant,
            int n
    ) {

        for (BenchmarkResult result : results) {

            if (!result.getWorkload().equals(workload)) {
                continue;
            }

            if (!result.getStructure().equals(structure)) {
                continue;
            }

            if (!result.getVariant().equals(variant)) {
                continue;
            }

            if (result.getN() != n) {
                continue;
            }

            return result;
        }

        return null;
    }

    // ============================================================
    // GET METRIC VALUE
    // ============================================================

    private long getMetricValue(
            BenchmarkResult result,
            String metric
    ) {

        switch (metric) {

            case "Steps":
                return result.getSteps();

            case "Moves":
                return result.getMoves();

            case "Comparisons":
                return result.getComparisons();

            default:
                throw new IllegalArgumentException(
                        "Unknown metric: " + metric
                );
        }
    }

    // ============================================================
    // CREATE LINE CHART
    // ============================================================

    private JFreeChart createLineChart(
            String title,
            String xAxisLabel,
            String yAxisLabel,
            DefaultCategoryDataset dataset
    ) {

        JFreeChart chart =
                ChartFactory.createLineChart(
                        title,
                        xAxisLabel,
                        yAxisLabel,
                        dataset
                );

        CategoryPlot plot =
                chart.getCategoryPlot();

        /*
         * Fixed X-axis categories:
         *
         * 0
         * 100
         * 1000
         * 10000
         * 100000
         */
        CategoryAxis domainAxis =
                new CategoryAxis(xAxisLabel);

        domainAxis.setCategoryMargin(0.05);

        domainAxis.setMaximumCategoryLabelLines(1);

        domainAxis.setMaximumCategoryLabelWidthRatio(0.20f);

        /*
         * Slight rotation so that 100000
         * is easier to read.
         */
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions
                        .createUpRotationLabelPositions(
                                Math.PI / 12
                        )
        );

        plot.setDomainAxis(domainAxis);

        /*
         * Y-axis starts from zero.
         */
        NumberAxis rangeAxis =
                new NumberAxis(yAxisLabel);

        rangeAxis.setAutoRangeIncludesZero(true);

        plot.setRangeAxis(rangeAxis);

        /*
         * Lines and points.
         */
        LineAndShapeRenderer renderer =
                new LineAndShapeRenderer();

        renderer.setDefaultShapesVisible(true);

        renderer.setDefaultLinesVisible(true);

        plot.setRenderer(renderer);

        return chart;
    }

    // ============================================================
    // SAVE CHART
    // ============================================================

    private void saveChart(
            JFreeChart chart,
            String fileName
    ) throws IOException {

        File file =
                new File(
                        PLOTS_DIR,
                        fileName
                );

        ChartUtils.saveChartAsPNG(
                file,
                chart,
                1000,
                600
        );

        System.out.println(
                "Created: " + file.getPath()
        );
    }

    // ============================================================
    // READ CSV
    // ============================================================

    private List<BenchmarkResult> readResults()
            throws IOException {

        List<BenchmarkResult> results =
                new ArrayList<>();

        File file =
                new File(CSV_FILE);

        if (!file.exists()) {

            throw new IOException(
                    "CSV file not found: "
                            + file.getAbsolutePath()
            );
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            /*
             * Skip CSV header.
             */
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts =
                        line.split(",");

                if (parts.length != 8) {
                    continue;
                }

                String workload =
                        parts[0];

                String variant =
                        parts[1];

                String structure =
                        parts[2];

                int n =
                        Integer.parseInt(parts[3]);

                double timeMs =
                        Double.parseDouble(parts[4]);

                long steps =
                        Long.parseLong(parts[5]);

                long moves =
                        Long.parseLong(parts[6]);

                long comparisons =
                        Long.parseLong(parts[7]);

                BenchmarkResult result =
                        new BenchmarkResult(
                                workload,
                                variant,
                                structure,
                                n,
                                timeMs,
                                steps,
                                moves,
                                comparisons
                        );

                results.add(result);
            }
        }

        return results;
    }
}