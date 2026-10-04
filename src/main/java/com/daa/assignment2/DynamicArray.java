package com.daa.assignment2;

public class DynamicArray implements DataStructure {

    private int[] data;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
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

            data[i] = data[i - 1];

        }

        data[index] = x;

        size++;
    }

    @Override
    public int remove(int index) {

        checkElementIndex(index);

        int removed = data[index];

        for (int i = index; i < size - 1; i++) {

            data[i] = data[i + 1];

        }

        size--;

        return removed;
    }

    @Override
    public int get(int index) {

        checkElementIndex(index);

        return data[index];
    }

    @Override
    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

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
            newData[i] = data[i];
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