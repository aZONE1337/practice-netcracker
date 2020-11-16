package ru.netcracker.practice.buildings.interfaces;

public interface Space extends Comparable<Space> {
    int getRooms();

    void setRooms(int rooms);

    float getArea();

    void setArea(float area);
}
