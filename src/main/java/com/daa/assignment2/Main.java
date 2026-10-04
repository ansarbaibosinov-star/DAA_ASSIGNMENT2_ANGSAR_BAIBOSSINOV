package com.daa.assignment2;

public class Main {

    public static void main(String[] args) {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 15);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

        System.out.println("Contains 20: "
                + list.contains(20));

        System.out.println("Removed: "
                + list.remove(2));

        System.out.println("Size: "
                + list.size());
    }
}