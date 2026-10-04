package com.daa.assignment2;

public class Metrics {

    private long steps;
    private long moves;
    private long comparisons;

    private long startTime;
    private long elapsedTime;

    public Metrics() {
        reset();
    }

    public void reset() {
        steps = 0;
        moves = 0;
        comparisons = 0;
        startTime = 0;
        elapsedTime = 0;
    }

    public void incrementSteps() {
        steps++;
    }

    public void incrementMoves() {
        moves++;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public long getSteps() {
        return steps;
    }

    public long getMoves() {
        return moves;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        elapsedTime = System.nanoTime() - startTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public double getElapsedTimeMillis() {
        return elapsedTime / 1_000_000.0;
    }
}