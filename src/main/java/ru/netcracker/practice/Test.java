package ru.netcracker.practice;

import ru.netcracker.practice.buildings.util.list.SinglyLinkedList;

public class Test {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        for (Integer integer : linkedList) {
            System.out.println(integer);
        }
    }
}
