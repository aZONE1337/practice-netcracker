package ru.netcracker.practice.buildings.interfaces;

public interface Building extends Cloneable {
    int getBuildingFloors();

    int getBuildingSpaces();

    float getBuildingArea();

    int getBuildingRooms();

    Floor[] getFloorsAsArray();

    Floor getFloor(int index);

    boolean changeFloor(int index, Floor newFloor);

    Space getSpace(int index);

    boolean setSpace(int index, Space newSpace);

    Space removeSpace(int index);

    Space getBestSpace();

    Space[] getSpacesSorted();

    Object clone() throws CloneNotSupportedException;
}
