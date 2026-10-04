package com.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void shouldAddAndGetElements() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));

        assertEquals(3, list.size());
    }

    @Test
    void shouldInsertAtIndex() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 15);

        assertEquals(10, list.get(0));
        assertEquals(15, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
    }

    @Test
    void shouldRemoveElement() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void shouldContainElement() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        assertTrue(list.contains(10));
        assertFalse(list.contains(50));
    }

    @Test
    void shouldHandleRemovingLastElement() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        assertEquals(20, list.remove(1));
        assertEquals(1, list.size());

        assertEquals(10, list.get(0));
    }

    @Test
    void shouldRejectInvalidGetIndex() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(1)
        );
    }

    @Test
    void shouldRejectInvalidRemoveIndex() {

        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );
    }

    @Test
    void shouldRejectInvalidInsertIndex() {

        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(2, 10)
        );
    }
}