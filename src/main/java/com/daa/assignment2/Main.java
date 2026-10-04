package com.daa.assignment2;

public class Main {

    public static void main(String[] args) {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        array.add(1, 15);

        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));
        System.out.println(array.get(3));

        System.out.println("Contains 20: "
                + array.contains(20));

        System.out.println("Removed: "
                + array.remove(2));

        System.out.println("Size: "
                + array.size());
    }
}