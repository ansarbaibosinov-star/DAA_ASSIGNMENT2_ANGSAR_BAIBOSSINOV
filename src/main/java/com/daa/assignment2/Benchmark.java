package com.daa.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100,
            1_000,
            10_000,
            100_000
    };

    private static final int SEED = 42;

    private static final int W1_OPERATIONS = 10_000;
    private static final int W2_OPERATIONS = 1_000;
    private static final int W3_OPERATIONS = 1_000;

    private static final int MEASURED_RUNS = 5;
    private static final int TOTAL_RUNS = MEASURED_RUNS + 1;

    private static final String CSV_PATH =
            "results/results.csv";

    public void run() throws IOException {

        Path resultsDirectory = Paths.get("results");

        Files.createDirectories(resultsDirectory);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(CSV_PATH))) {

            writer.write(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            writer.newLine();

            runW1(writer);
            runW2(writer);
            runW3(writer);
            runW4(writer);
        }

        System.out.println();
        System.out.println("Benchmark finished.");
        System.out.println("CSV saved to: " + CSV_PATH);
    }

    // =========================================================
    // W1 - RANDOM ACCESS
    // =========================================================

    private void runW1(BufferedWriter writer) throws IOException {

        System.out.println();
        System.out.println("===== W1 - Random Access =====");

        for (int n : SIZES) {

            int[] data = generateData(n, SEED);
            int[] indices = generateRandomIndices(
                    n,
                    W1_OPERATIONS,
                    SEED
            );

            BenchmarkResult arrayResult =
                    benchmarkW1Array(data, indices, n);

            writeResult(writer, arrayResult);

            BenchmarkResult listResult =
                    benchmarkW1List(data, indices, n);

            writeResult(writer, listResult);
        }
    }

    private BenchmarkResult benchmarkW1Array(
            int[] data,
            int[] indices,
            int n) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            fillArray(array, data);

            metrics.reset();

            metrics.startTimer();

            long checksum = 0;

            for (int index : indices) {
                checksum += array.get(index);
            }

            metrics.stopTimer();

            if (checksum == Long.MIN_VALUE) {
                System.out.println("Impossible checksum.");
            }

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W1",
                "-",
                "DynamicArray",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    private BenchmarkResult benchmarkW1List(
            int[] data,
            int[] indices,
            int n) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            fillList(list, data);

            metrics.reset();

            metrics.startTimer();

            long checksum = 0;

            for (int index : indices) {
                checksum += list.get(index);
            }

            metrics.stopTimer();

            if (checksum == Long.MIN_VALUE) {
                System.out.println("Impossible checksum.");
            }

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W1",
                "-",
                "MyLinkedList",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W2 - SEARCH
    // =========================================================

    private void runW2(BufferedWriter writer) throws IOException {

        System.out.println();
        System.out.println("===== W2 - Search =====");

        for (int n : SIZES) {

            int[] data = generateData(n, SEED);

            int[] queries = generateSearchQueries(
                    data,
                    n,
                    SEED
            );

            BenchmarkResult arrayResult =
                    benchmarkW2Array(data, queries, n);

            writeResult(writer, arrayResult);

            BenchmarkResult listResult =
                    benchmarkW2List(data, queries, n);

            writeResult(writer, listResult);
        }
    }

    private BenchmarkResult benchmarkW2Array(
            int[] data,
            int[] queries,
            int n) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            fillArray(array, data);

            metrics.reset();

            metrics.startTimer();

            int found = 0;

            for (int query : queries) {

                if (array.contains(query)) {
                    found++;
                }
            }

            metrics.stopTimer();

            if (found < 0) {
                System.out.println("Impossible result.");
            }

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W2",
                "-",
                "DynamicArray",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    private BenchmarkResult benchmarkW2List(
            int[] data,
            int[] queries,
            int n) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            fillList(list, data);

            metrics.reset();

            metrics.startTimer();

            int found = 0;

            for (int query : queries) {

                if (list.contains(query)) {
                    found++;
                }
            }

            metrics.stopTimer();

            if (found < 0) {
                System.out.println("Impossible result.");
            }

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W2",
                "-",
                "MyLinkedList",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W3 - INSERT & REMOVE
    // =========================================================

    private void runW3(BufferedWriter writer) throws IOException {

        System.out.println();
        System.out.println("===== W3 - Insert & Remove =====");

        for (int n : SIZES) {

            int[] data = generateData(n, SEED);

            int[] insertedValues = generateData(
                    W3_OPERATIONS,
                    SEED + n
            );

            BenchmarkResult arrayHead =
                    benchmarkW3Array(
                            data,
                            insertedValues,
                            n,
                            "head"
                    );

            writeResult(writer, arrayHead);

            BenchmarkResult listHead =
                    benchmarkW3List(
                            data,
                            insertedValues,
                            n,
                            "head"
                    );

            writeResult(writer, listHead);

            BenchmarkResult arrayMiddle =
                    benchmarkW3Array(
                            data,
                            insertedValues,
                            n,
                            "middle"
                    );

            writeResult(writer, arrayMiddle);

            BenchmarkResult listMiddle =
                    benchmarkW3List(
                            data,
                            insertedValues,
                            n,
                            "middle"
                    );

            writeResult(writer, listMiddle);
        }
    }

    private BenchmarkResult benchmarkW3Array(
            int[] data,
            int[] insertedValues,
            int n,
            String variant) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            fillArray(array, data);

            metrics.reset();

            metrics.startTimer();

            for (int i = 0; i < W3_OPERATIONS; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = array.size() / 2;
                }

                array.add(index, insertedValues[i]);
            }

            for (int i = 0; i < W3_OPERATIONS; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = array.size() / 2;
                }

                array.remove(index);
            }

            metrics.stopTimer();

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W3",
                variant,
                "DynamicArray",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    private BenchmarkResult benchmarkW3List(
            int[] data,
            int[] insertedValues,
            int n,
            String variant) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            fillList(list, data);

            metrics.reset();

            metrics.startTimer();

            for (int i = 0; i < W3_OPERATIONS; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = list.size() / 2;
                }

                list.add(index, insertedValues[i]);
            }

            for (int i = 0; i < W3_OPERATIONS; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = list.size() / 2;
                }

                list.remove(index);
            }

            metrics.stopTimer();

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W3",
                variant,
                "MyLinkedList",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W4 - PRIORITY PROCESSING
    // =========================================================

    private void runW4(BufferedWriter writer) throws IOException {

        System.out.println();
        System.out.println("===== W4 - Priority Processing =====");

        for (int n : SIZES) {

            int[] data = generateData(n, SEED);

            BenchmarkResult result =
                    benchmarkW4Heap(data, n);

            writeResult(writer, result);
        }
    }

    private BenchmarkResult benchmarkW4Heap(
            int[] data,
            int n) {

        double[] times = new double[MEASURED_RUNS];
        long[] steps = new long[MEASURED_RUNS];
        long[] moves = new long[MEASURED_RUNS];
        long[] comparisons = new long[MEASURED_RUNS];

        for (int run = 0; run < TOTAL_RUNS; run++) {

            Metrics metrics = new Metrics();
            MinHeap heap = new MinHeap(metrics);

            for (int value : data) {
                heap.insert(value);
            }

            metrics.reset();

            metrics.startTimer();

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "Heap output is not sorted."
                    );
                }

                previous = current;
            }

            metrics.stopTimer();

            if (run > 0) {

                int position = run - 1;

                times[position] =
                        metrics.getElapsedTimeMillis();

                steps[position] =
                        metrics.getSteps();

                moves[position] =
                        metrics.getMoves();

                comparisons[position] =
                        metrics.getComparisons();
            }
        }

        return createMedianResult(
                "W4",
                "-",
                "MinHeap",
                n,
                times,
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // DATA GENERATION
    // =========================================================

    private int[] generateData(int n, int seed) {

        Random random = new Random(seed);

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }

        return data;
    }

    private int[] generateRandomIndices(
            int n,
            int count,
            int seed) {

        Random random = new Random(seed);

        int[] indices = new int[count];

        for (int i = 0; i < count; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private int[] generateSearchQueries(
            int[] data,
            int n,
            int seed) {

        Random random = new Random(seed + 1000);

        int[] queries = new int[W2_OPERATIONS];

        // First half: values that are present.
        for (int i = 0; i < W2_OPERATIONS / 2; i++) {

            int randomIndex = random.nextInt(n);

            queries[i] = data[randomIndex];
        }

        // Second half: values that are not present.
        for (int i = W2_OPERATIONS / 2;
             i < W2_OPERATIONS;
             i++) {

            queries[i] = Integer.MIN_VALUE;

            while (containsValue(data, queries[i])) {

                queries[i] = random.nextInt();
            }
        }

        return queries;
    }

    private boolean containsValue(
            int[] data,
            int value) {

        for (int element : data) {

            if (element == value) {
                return true;
            }
        }

        return false;
    }

    // =========================================================
    // FILL STRUCTURES
    // =========================================================

    private void fillArray(
            DynamicArray array,
            int[] data) {

        for (int value : data) {
            array.add(value);
        }
    }

    private void fillList(
            MyLinkedList list,
            int[] data) {

        for (int value : data) {
            list.add(value);
        }
    }

    // =========================================================
    // MEDIAN
    // =========================================================

    private BenchmarkResult createMedianResult(
            String workload,
            String variant,
            String structure,
            int n,
            double[] times,
            long[] steps,
            long[] moves,
            long[] comparisons) {

        double medianTime = median(times);

        long medianSteps = median(steps);
        long medianMoves = median(moves);
        long medianComparisons = median(comparisons);

        return new BenchmarkResult(
                workload,
                variant,
                structure,
                n,
                medianTime,
                medianSteps,
                medianMoves,
                medianComparisons
        );
    }

    private double median(double[] values) {

        double[] copy = values.clone();

        for (int i = 0; i < copy.length - 1; i++) {

            for (int j = 0; j < copy.length - i - 1; j++) {

                if (copy[j] > copy[j + 1]) {

                    double temp = copy[j];

                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }

        return copy[copy.length / 2];
    }

    private long median(long[] values) {

        long[] copy = values.clone();

        for (int i = 0; i < copy.length - 1; i++) {

            for (int j = 0; j < copy.length - i - 1; j++) {

                if (copy[j] > copy[j + 1]) {

                    long temp = copy[j];

                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }

        return copy[copy.length / 2];
    }

    // =========================================================
    // CSV
    // =========================================================

    private void writeResult(
            BufferedWriter writer,
            BenchmarkResult result) throws IOException {

        writer.write(
                result.getWorkload() + "," +
                        result.getVariant() + "," +
                        result.getStructure() + "," +
                        result.getN() + "," +
                        String.format(
                                java.util.Locale.US,
                                "%.6f",
                                result.getTimeMs()
                        ) + "," +
                        result.getSteps() + "," +
                        result.getMoves() + "," +
                        result.getComparisons()
        );

        writer.newLine();

        System.out.println(
                result.getWorkload() +
                        " | " +
                        result.getVariant() +
                        " | " +
                        result.getStructure() +
                        " | n=" +
                        result.getN() +
                        " | " +
                        String.format(
                                java.util.Locale.US,
                                "%.4f ms",
                                result.getTimeMs()
                        )
        );
    }
}