package com.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void shouldAddAndGetElements() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));

        assertEquals(3, array.size());
    }

    @Test
    void shouldInsertAtIndex() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        array.add(1, 15);

        assertEquals(10, array.get(0));
        assertEquals(15, array.get(1));
        assertEquals(20, array.get(2));
        assertEquals(30, array.get(3));
    }

    @Test
    void shouldRemoveElement() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }

    @Test
    void shouldContainElement() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        assertTrue(array.contains(10));
        assertFalse(array.contains(50));
    }

    @Test
    void shouldGrowAutomatically() {

        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 100; i++) {
            array.add(i);
        }

        assertEquals(100, array.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i, array.get(i));
        }
    }

    @Test
    void shouldRejectInvalidGetIndex() {

        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );
    }

    @Test
    void shouldRejectInvalidRemoveIndex() {

        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(0)
        );
    }

    @Test
    void shouldRejectInvalidInsertIndex() {

        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(2, 10)
        );
    }
}