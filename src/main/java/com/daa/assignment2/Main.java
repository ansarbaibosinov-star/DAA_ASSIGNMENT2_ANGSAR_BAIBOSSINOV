package com.daa.assignment2;

public class Main {

    public static void main(String[] args) {

        try {
            Benchmark benchmark = new Benchmark();
            benchmark.run();

            PlotGenerator plotGenerator = new PlotGenerator();
            plotGenerator.generateAllPlots();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}