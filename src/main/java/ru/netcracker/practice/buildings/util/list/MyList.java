package ru.netcracker.practice.buildings.util.list;

import java.util.LinkedList;
import java.util.List;

public interface MyList<E> {
    boolean add(E e);

    boolean add(int index, E e);

    E remove(E e);

    E remove(int index);

    boolean replace(int index, E e);

    boolean replace(E oldElem, E newElem);

    E get(int index);

    int size();

    boolean isEmpty();

}
