package com.daa.assignment2;

public class MinHeap {

    private int[] heap;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    private Metrics metrics;

    public MinHeap() {
        this(new Metrics());
    }

    public MinHeap(Metrics metrics) {
        this.metrics = metrics;
        heap = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    public void insert(int x) {

        if (size == heap.length) {
            grow();
        }

        heap[size] = x;

        int index = size;
        size++;

        while (index > 0) {

            int parent = (index - 1) / 2;

            metrics.incrementSteps();
            metrics.incrementSteps();
            metrics.incrementComparisons();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);

            index = parent;
        }
    }

    public int peekMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.incrementSteps();

        return heap[0];
    }

    public int extractMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.incrementSteps();
        int min = heap[0];

        size--;

        if (size == 0) {
            return min;
        }

        metrics.incrementSteps();
        heap[0] = heap[size];
        metrics.incrementMoves();

        siftDown(0);

        return min;
    }

    private void siftDown(int index) {

        while (true) {

            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left >= size) {
                break;
            }

            int smallerChild = left;

            if (right < size) {

                metrics.incrementSteps();
                metrics.incrementSteps();
                metrics.incrementComparisons();

                if (heap[right] < heap[left]) {
                    smallerChild = right;
                }
            }

            metrics.incrementSteps();
            metrics.incrementSteps();
            metrics.incrementComparisons();

            if (heap[index] <= heap[smallerChild]) {
                break;
            }

            swap(index, smallerChild);

            index = smallerChild;
        }
    }

    private void swap(int first, int second) {

        int temp = heap[first];

        heap[first] = heap[second];
        heap[second] = temp;

        // Three assignments are performed.
        metrics.incrementMoves();
        metrics.incrementMoves();
        metrics.incrementMoves();
    }

    private void grow() {

        int newCapacity = heap.length * 2;

        int[] newHeap = new int[newCapacity];

        for (int i = 0; i < size; i++) {

            metrics.incrementSteps();

            newHeap[i] = heap[i];

            metrics.incrementMoves();
        }

        heap = newHeap;
    }

    public int size() {
        return size;
    }

    public boolean isValidHeap() {

        for (int i = 0; i < size; i++) {

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size) {

                if (heap[i] > heap[left]) {
                    return false;
                }
            }

            if (right < size) {

                if (heap[i] > heap[right]) {
                    return false;
                }
            }
        }

        return true;
    }
}