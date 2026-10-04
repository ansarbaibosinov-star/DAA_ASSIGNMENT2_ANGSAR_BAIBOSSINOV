package com.daa.assignment2;

public class DynamicArray implements DataStructure {

    private int[] data;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    private Metrics metrics;

    public DynamicArray() {
        this(new Metrics());
    }

    public DynamicArray(Metrics metrics) {
        this.metrics = metrics;
        data = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public void add(int x) {

        if (size == data.length) {
            grow();
        }

        data[size] = x;
        size++;
    }

    @Override
    public void add(int index, int x) {

        checkPositionIndex(index);

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {

            metrics.incrementSteps();
            data[i] = data[i - 1];

            metrics.incrementMoves();
        }

        data[index] = x;

        size++;
    }

    @Override
    public int remove(int index) {

        checkElementIndex(index);

        metrics.incrementSteps();
        int removed = data[index];

        for (int i = index; i < size - 1; i++) {

            metrics.incrementSteps();
            data[i] = data[i + 1];

            metrics.incrementMoves();
        }

        size--;

        return removed;
    }

    @Override
    public int get(int index) {

        checkElementIndex(index);

        metrics.incrementSteps();

        return data[index];
    }

    @Override
    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

            metrics.incrementSteps();
            metrics.incrementComparisons();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private void grow() {

        int newCapacity = data.length * 2;

        int[] newData = new int[newCapacity];

        for (int i = 0; i < size; i++) {

            metrics.incrementSteps();

            newData[i] = data[i];

            metrics.incrementMoves();
        }

        data = newData;
    }

    private void checkElementIndex(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index +
                            ", Size: " + size
            );
        }
    }

    private void checkPositionIndex(int index) {

        if (index < 0 || index > size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index +
                            ", Size: " + size
            );
        }
    }
}