package com.daa.assignment2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100,
            1000,
            10000,
            100000
    };

    private static final int W1_OPERATIONS = 10000;
    private static final int W2_OPERATIONS = 1000;
    private static final int W3_OPERATIONS = 1000;

    private static final int MEASURED_RUNS = 5;

    private static final int RANDOM_SEED = 42;

    private static final String RESULTS_DIRECTORY = "results";
    private static final String CSV_FILE = "results/results.csv";

    public void run() throws IOException {

        File resultsDirectory =
                new File(RESULTS_DIRECTORY);

        if (!resultsDirectory.exists()) {
            if (!resultsDirectory.mkdirs()) {
                throw new IOException(
                        "Could not create results directory."
                );
            }
        }

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(CSV_FILE)
                     )) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            runW1(writer);
            runW2(writer);
            runW3(writer);
            runW4(writer);
        }

        System.out.println(
                "Benchmark completed successfully."
        );

        System.out.println(
                "Results saved to: " + CSV_FILE
        );
    }

    // ============================================================
    // W1 - RANDOM ACCESS
    // ============================================================

    private void runW1(PrintWriter writer) {

        System.out.println();
        System.out.println("===== W1 - Random Access =====");

        for (int n : SIZES) {

            BenchmarkResult arrayResult =
                    measureW1Array(n);

            writeResult(
                    writer,
                    arrayResult
            );

            BenchmarkResult listResult =
                    measureW1List(n);

            writeResult(
                    writer,
                    listResult
            );
        }
    }

    private BenchmarkResult measureW1Array(int n) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] indices =
                generateIndices(n, W1_OPERATIONS);

        /*
         * Warm-up run.
         */
        runW1ArrayOnce(
                data,
                indices,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            DynamicArray array =
                    new DynamicArray(metrics);

            fillArray(
                    array,
                    data
            );

            metrics.reset();

            metrics.startTimer();

            for (int i = 0;
                 i < W1_OPERATIONS;
                 i++) {

                array.get(indices[i]);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W1",
                "-",
                "DynamicArray",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    private BenchmarkResult measureW1List(int n) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] indices =
                generateIndices(n, W1_OPERATIONS);

        /*
         * Warm-up run.
         */
        runW1ListOnce(
                data,
                indices,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            MyLinkedList list =
                    new MyLinkedList(metrics);

            fillList(
                    list,
                    data
            );

            metrics.reset();

            metrics.startTimer();

            for (int i = 0;
                 i < W1_OPERATIONS;
                 i++) {

                list.get(indices[i]);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W1",
                "-",
                "MyLinkedList",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    // ============================================================
    // W2 - SEARCH
    // ============================================================

    private void runW2(PrintWriter writer) {

        System.out.println();
        System.out.println("===== W2 - Search =====");

        for (int n : SIZES) {

            BenchmarkResult arrayResult =
                    measureW2Array(n);

            writeResult(
                    writer,
                    arrayResult
            );

            BenchmarkResult listResult =
                    measureW2List(n);

            writeResult(
                    writer,
                    listResult
            );
        }
    }

    private BenchmarkResult measureW2Array(int n) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] queries =
                generateSearchQueries(
                        data
                );

        /*
         * Warm-up.
         */
        runW2ArrayOnce(
                data,
                queries,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            DynamicArray array =
                    new DynamicArray(metrics);

            fillArray(
                    array,
                    data
            );

            metrics.reset();

            metrics.startTimer();

            for (int query : queries) {
                array.contains(query);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W2",
                "-",
                "DynamicArray",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    private BenchmarkResult measureW2List(int n) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] queries =
                generateSearchQueries(
                        data
                );

        /*
         * Warm-up.
         */
        runW2ListOnce(
                data,
                queries,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            MyLinkedList list =
                    new MyLinkedList(metrics);

            fillList(
                    list,
                    data
            );

            metrics.reset();

            metrics.startTimer();

            for (int query : queries) {
                list.contains(query);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W2",
                "-",
                "MyLinkedList",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    // ============================================================
    // W3 - INSERT & REMOVE
    // ============================================================

    private void runW3(PrintWriter writer) {

        System.out.println();
        System.out.println("===== W3 - Insert & Remove =====");

        for (int n : SIZES) {

            BenchmarkResult arrayHead =
                    measureW3Array(
                            n,
                            true
                    );

            writeResult(
                    writer,
                    arrayHead
            );

            BenchmarkResult listHead =
                    measureW3List(
                            n,
                            true
                    );

            writeResult(
                    writer,
                    listHead
            );

            BenchmarkResult arrayMiddle =
                    measureW3Array(
                            n,
                            false
                    );

            writeResult(
                    writer,
                    arrayMiddle
            );

            BenchmarkResult listMiddle =
                    measureW3List(
                            n,
                            false
                    );

            writeResult(
                    writer,
                    listMiddle
            );
        }
    }

    private BenchmarkResult measureW3Array(
            int n,
            boolean head
    ) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] insertedValues =
                generateInsertedValues(
                        n
                );

        /*
         * Warm-up.
         */
        runW3ArrayOnce(
                data,
                insertedValues,
                n,
                head,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            DynamicArray array =
                    new DynamicArray(metrics);

            fillArray(
                    array,
                    data
            );

            int index;

            if (head) {
                index = 0;
            } else {
                index = n / 2;
            }

            metrics.reset();

            metrics.startTimer();

            for (int i = 0;
                 i < W3_OPERATIONS;
                 i++) {

                array.add(
                        index,
                        insertedValues[i]
                );
            }

            for (int i = 0;
                 i < W3_OPERATIONS;
                 i++) {

                array.remove(index);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W3",
                head ? "head" : "middle",
                "DynamicArray",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    private BenchmarkResult measureW3List(
            int n,
            boolean head
    ) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        int[] data =
                generateData(n);

        int[] insertedValues =
                generateInsertedValues(
                        n
                );

        /*
         * Warm-up.
         */
        runW3ListOnce(
                data,
                insertedValues,
                n,
                head,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            MyLinkedList list =
                    new MyLinkedList(metrics);

            fillList(
                    list,
                    data
            );

            int index;

            if (head) {
                index = 0;
            } else {
                index = n / 2;
            }

            metrics.reset();

            metrics.startTimer();

            for (int i = 0;
                 i < W3_OPERATIONS;
                 i++) {

                list.add(
                        index,
                        insertedValues[i]
                );
            }

            for (int i = 0;
                 i < W3_OPERATIONS;
                 i++) {

                list.remove(index);
            }

            metrics.stopTimer();

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W3",
                head ? "head" : "middle",
                "MyLinkedList",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    // ============================================================
    // W4 - PRIORITY PROCESSING
    // ============================================================

    private void runW4(PrintWriter writer) {

        System.out.println();
        System.out.println("===== W4 - Priority Processing =====");

        for (int n : SIZES) {

            BenchmarkResult result =
                    measureW4(n);

            writeResult(
                    writer,
                    result
            );
        }
    }

    private BenchmarkResult measureW4(int n) {

        double[] times =
                new double[MEASURED_RUNS];

        long finalSteps = 0;
        long finalMoves = 0;
        long finalComparisons = 0;

        /*
         * Same input data for every run.
         */
        int[] data =
                generateData(n);

        /*
         * Warm-up.
         */
        runW4Once(
                data,
                false
        );

        /*
         * Five measured runs.
         */
        for (int run = 0;
             run < MEASURED_RUNS;
             run++) {

            Metrics metrics =
                    new Metrics();

            MinHeap heap =
                    new MinHeap(metrics);

            /*
             * IMPORTANT:
             *
             * The timer starts BEFORE insert().
             *
             * Therefore W4 measures:
             *
             * 1. insert n values
             * 2. extractMin n times
             *
             * This matches the assignment workload.
             */
            metrics.reset();

            metrics.startTimer();

            for (int i = 0;
                 i < n;
                 i++) {

                heap.insert(data[i]);
            }

            int previous =
                    Integer.MIN_VALUE;

            for (int i = 0;
                 i < n;
                 i++) {

                int current =
                        heap.extractMin();

                /*
                 * Verify non-decreasing output.
                 */
                if (i > 0 && current < previous) {

                    throw new IllegalStateException(
                            "MinHeap output is not sorted."
                    );
                }

                previous = current;
            }

            metrics.stopTimer();

            /*
             * The heap must be empty after
             * extracting all n elements.
             */
            if (heap.size() != 0) {

                throw new IllegalStateException(
                        "Heap is not empty after W4."
                );
            }

            times[run] =
                    metrics.getElapsedTimeMillis();

            finalSteps =
                    metrics.getSteps();

            finalMoves =
                    metrics.getMoves();

            finalComparisons =
                    metrics.getComparisons();
        }

        return new BenchmarkResult(
                "W4",
                "-",
                "MinHeap",
                n,
                median(times),
                finalSteps,
                finalMoves,
                finalComparisons
        );
    }

    // ============================================================
    // W1 WARM-UP
    // ============================================================

    private void runW1ArrayOnce(
            int[] data,
            int[] indices,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        DynamicArray array =
                new DynamicArray(metrics);

        fillArray(
                array,
                data
        );

        if (measure) {
            metrics.startTimer();
        }

        for (int index : indices) {
            array.get(index);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    private void runW1ListOnce(
            int[] data,
            int[] indices,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        MyLinkedList list =
                new MyLinkedList(metrics);

        fillList(
                list,
                data
        );

        if (measure) {
            metrics.startTimer();
        }

        for (int index : indices) {
            list.get(index);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    // ============================================================
    // W2 WARM-UP
    // ============================================================

    private void runW2ArrayOnce(
            int[] data,
            int[] queries,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        DynamicArray array =
                new DynamicArray(metrics);

        fillArray(
                array,
                data
        );

        if (measure) {
            metrics.startTimer();
        }

        for (int query : queries) {
            array.contains(query);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    private void runW2ListOnce(
            int[] data,
            int[] queries,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        MyLinkedList list =
                new MyLinkedList(metrics);

        fillList(
                list,
                data
        );

        if (measure) {
            metrics.startTimer();
        }

        for (int query : queries) {
            list.contains(query);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    // ============================================================
    // W3 WARM-UP
    // ============================================================

    private void runW3ArrayOnce(
            int[] data,
            int[] insertedValues,
            int n,
            boolean head,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        DynamicArray array =
                new DynamicArray(metrics);

        fillArray(
                array,
                data
        );

        int index =
                head ? 0 : n / 2;

        if (measure) {
            metrics.startTimer();
        }

        for (int i = 0;
             i < W3_OPERATIONS;
             i++) {

            array.add(
                    index,
                    insertedValues[i]
            );
        }

        for (int i = 0;
             i < W3_OPERATIONS;
             i++) {

            array.remove(index);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    private void runW3ListOnce(
            int[] data,
            int[] insertedValues,
            int n,
            boolean head,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        MyLinkedList list =
                new MyLinkedList(metrics);

        fillList(
                list,
                data
        );

        int index =
                head ? 0 : n / 2;

        if (measure) {
            metrics.startTimer();
        }

        for (int i = 0;
             i < W3_OPERATIONS;
             i++) {

            list.add(
                    index,
                    insertedValues[i]
            );
        }

        for (int i = 0;
             i < W3_OPERATIONS;
             i++) {

            list.remove(index);
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    // ============================================================
    // W4 WARM-UP
    // ============================================================

    private void runW4Once(
            int[] data,
            boolean measure
    ) {

        Metrics metrics =
                new Metrics();

        MinHeap heap =
                new MinHeap(metrics);

        if (measure) {
            metrics.startTimer();
        }

        for (int value : data) {
            heap.insert(value);
        }

        int previous =
                Integer.MIN_VALUE;

        for (int i = 0;
             i < data.length;
             i++) {

            int current =
                    heap.extractMin();

            if (i > 0 && current < previous) {

                throw new IllegalStateException(
                        "MinHeap output is not sorted."
                );
            }

            previous = current;
        }

        if (measure) {
            metrics.stopTimer();
        }
    }

    // ============================================================
    // DATA GENERATION
    // ============================================================

    private int[] generateData(int n) {

        int[] data =
                new int[n];

        Random random =
                new Random(RANDOM_SEED);

        for (int i = 0;
             i < n;
             i++) {

            data[i] =
                    random.nextInt();
        }

        return data;
    }

    private int[] generateIndices(
            int n,
            int count
    ) {

        int[] indices =
                new int[count];

        Random random =
                new Random(RANDOM_SEED);

        for (int i = 0;
             i < count;
             i++) {

            indices[i] =
                    random.nextInt(n);
        }

        return indices;
    }

    private int[] generateInsertedValues(
            int n
    ) {

        int[] values =
                new int[W3_OPERATIONS];

        Random random =
                new Random(RANDOM_SEED);

        /*
         * Generate deterministic values.
         */
        for (int i = 0;
             i < W3_OPERATIONS;
             i++) {

            values[i] =
                    random.nextInt();
        }

        return values;
    }

    // ============================================================
    // SEARCH QUERIES
    // ============================================================

    private int[] generateSearchQueries(
            int[] data
    ) {

        int[] queries =
                new int[W2_OPERATIONS];

        Random random =
                new Random(RANDOM_SEED + 1000);

        /*
         * First 500 queries are PRESENT.
         */
        for (int i = 0;
             i < W2_OPERATIONS / 2;
             i++) {

            int index =
                    random.nextInt(
                            data.length
                    );

            queries[i] =
                    data[index];
        }

        /*
         * Second 500 queries are ABSENT.
         */
        int candidate =
                Integer.MIN_VALUE;

        for (int i = W2_OPERATIONS / 2;
             i < W2_OPERATIONS;
             i++) {

            while (containsValue(
                    data,
                    candidate
            )) {

                candidate++;
            }

            queries[i] =
                    candidate;

            candidate++;
        }

        return queries;
    }

    private boolean containsValue(
            int[] data,
            int value
    ) {

        for (int element : data) {

            if (element == value) {
                return true;
            }
        }

        return false;
    }

    // ============================================================
    // FILL STRUCTURES
    // ============================================================

    private void fillArray(
            DynamicArray array,
            int[] data
    ) {

        for (int value : data) {
            array.add(value);
        }
    }

    private void fillList(
            MyLinkedList list,
            int[] data
    ) {

        for (int value : data) {
            list.add(value);
        }
    }

    // ============================================================
    // MEDIAN
    // ============================================================

    private double median(
            double[] values
    ) {

        double[] copy =
                new double[values.length];

        for (int i = 0;
             i < values.length;
             i++) {

            copy[i] =
                    values[i];
        }

        /*
         * Simple bubble sort.
         *
         * Only five values are sorted,
         * so this is completely sufficient.
         */
        for (int i = 0;
             i < copy.length - 1;
             i++) {

            for (int j = 0;
                 j < copy.length - 1 - i;
                 j++) {

                if (copy[j] > copy[j + 1]) {

                    double temp =
                            copy[j];

                    copy[j] =
                            copy[j + 1];

                    copy[j + 1] =
                            temp;
                }
            }
        }

        return copy[copy.length / 2];
    }

    // ============================================================
    // WRITE CSV
    // ============================================================

    private void writeResult(
            PrintWriter writer,
            BenchmarkResult result
    ) {

        writer.printf(
                Locale.US,
                "%s,%s,%s,%d,%.6f,%d,%d,%d%n",
                result.getWorkload(),
                result.getVariant(),
                result.getStructure(),
                result.getN(),
                result.getTimeMs(),
                result.getSteps(),
                result.getMoves(),
                result.getComparisons()
        );

        System.out.printf(
                Locale.US,
                "%s | %s | %s | n=%d | time=%.6f ms%n",
                result.getWorkload(),
                result.getStructure(),
                result.getVariant(),
                result.getN(),
                result.getTimeMs()
        );
    }
}