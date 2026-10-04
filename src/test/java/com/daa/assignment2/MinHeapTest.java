package com.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void shouldInsertAndPeekMinimum() {

        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);

        assertEquals(3, heap.peekMin());
        assertEquals(4, heap.size());
    }

    @Test
    void shouldKeepHeapPropertyAfterEveryInsert() {

        MinHeap heap = new MinHeap();

        int[] values = {10, 5, 20, 3, 8, 1, 15};

        for (int value : values) {

            heap.insert(value);

            assertTrue(heap.isValidHeap());
        }
    }

    @Test
    void shouldExtractInSortedOrder() {

        MinHeap heap = new MinHeap();

        int[] values = {
                10, 5, 20, 3, 8, 1, 15
        };

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {

            int current = heap.extractMin();

            assertTrue(current >= previous);
            assertTrue(heap.isValidHeap());

            previous = current;
        }
    }

    @Test
    void shouldHandleDuplicateValues() {

        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());

        assertEquals(0, heap.size());
    }

    @Test
    void shouldRejectPeekOnEmptyHeap() {

        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void shouldRejectExtractOnEmptyHeap() {

        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }
}