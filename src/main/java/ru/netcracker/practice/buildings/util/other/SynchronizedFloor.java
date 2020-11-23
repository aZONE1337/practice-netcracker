package ru.netcracker.practice.buildings.util.other;

import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.list.SinglyLinkedList;

public class SynchronizedFloor implements Floor {
    private final Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int getTotalSpaces() {
        return floor.getTotalSpaces();
    }

    @Override
    public synchronized float getTotalArea() {
        return floor.getTotalArea();
    }

    @Override
    public synchronized int getTotalRooms() {
        return floor.getTotalRooms();
    }

    @Override
    public synchronized Space[] getSpacesAsArray() {
        return floor.getSpacesAsArray();
    }

    @Override
    public synchronized Space getSpace(int index) {
        return floor.getSpace(index);
    }

    @Override
    public synchronized boolean setSpace(int index, Space newSpace) {
        return floor.setSpace(index, newSpace);
    }

    @Override
    public synchronized boolean addSpace(int index, Space newSpace) {
        return floor.addSpace(index, newSpace);
    }

    @Override
    public synchronized Space removeSpace(int index) {
        return floor.removeSpace(index);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized int compareTo(final Floor o) {
        return floor.compareTo(o);
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return floor.clone();
    }
}
