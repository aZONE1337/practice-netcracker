package ru.netcracker.practice.buildings.interfaces;

public interface Floor extends Comparable<Floor>, Cloneable {
    int getTotalSpaces();

    float getTotalArea();

    int getTotalRooms();

    Space[] getSpacesAsArray();

    Space getSpace(int index);

    boolean setSpace(int index, Space newSpace);

    boolean addSpace(int index, Space newSpace);

    Space removeSpace(int index);

    Space getBestSpace();

    Object clone() throws CloneNotSupportedException;
}
