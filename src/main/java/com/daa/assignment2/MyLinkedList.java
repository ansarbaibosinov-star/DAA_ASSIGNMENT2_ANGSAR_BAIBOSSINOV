package com.daa.assignment2;

public class MyLinkedList implements DataStructure {

    private static class Node {

        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    private Metrics metrics;

    public MyLinkedList() {
        this(new Metrics());
    }

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(int x) {

        Node newNode = new Node(x);

        if (size == 0) {

            head = newNode;
            tail = newNode;

        } else {

            tail.next = newNode;
            metrics.incrementMoves();

            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(int index, int x) {

        checkPositionIndex(index);

        Node newNode = new Node(x);

        if (index == 0) {

            newNode.next = head;
            metrics.incrementMoves();

            head = newNode;
            metrics.incrementMoves();

            if (size == 0) {
                tail = newNode;
                metrics.incrementMoves();
            }

            size++;
            return;
        }

        if (index == size) {

            tail.next = newNode;
            metrics.incrementMoves();

            tail = newNode;
            metrics.incrementMoves();

            size++;
            return;
        }

        Node previous = getNode(index - 1);

        newNode.next = previous.next;
        metrics.incrementMoves();

        previous.next = newNode;
        metrics.incrementMoves();

        size++;
    }

    @Override
    public int remove(int index) {

        checkElementIndex(index);

        if (index == 0) {

            int removed = head.value;

            head = head.next;
            metrics.incrementMoves();

            size--;

            if (size == 0) {
                tail = null;
                metrics.incrementMoves();
            }

            return removed;
        }

        Node previous = getNode(index - 1);
        Node removedNode = previous.next;

        previous.next = removedNode.next;
        metrics.incrementMoves();

        if (index == size - 1) {
            tail = previous;
            metrics.incrementMoves();
        }

        size--;

        return removedNode.value;
    }

    @Override
    public int get(int index) {

        checkElementIndex(index);

        Node current = getNode(index);

        return current.value;
    }

    @Override
    public boolean contains(int x) {

        Node current = head;

        while (current != null) {

            metrics.incrementSteps();
            metrics.incrementComparisons();

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private Node getNode(int index) {

        Node current = head;

        for (int i = 0; i < index; i++) {

            metrics.incrementSteps();

            current = current.next;
        }

        return current;
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