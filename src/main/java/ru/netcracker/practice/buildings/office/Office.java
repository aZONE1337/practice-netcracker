package ru.netcracker.practice.buildings.office;

import ru.netcracker.practice.buildings.exceptions.InvalidRoomsCountException;
import ru.netcracker.practice.buildings.exceptions.InvalidSpaceAreaException;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Objects;

public class Office implements Space, Serializable {
    private static final float DEFAULT_AREA = 250.0f;
    private static final int DEFAULT_ROOMS = 1;

    private float area;
    private int rooms;

    //конструктор по умолчанию
    public Office() {
        this.area = DEFAULT_AREA;
        this.rooms = DEFAULT_ROOMS;
    }

    //конструктор принимающий площадь
    public Office(float area) {
        if (Math.signum(area) == -1) {
            throw new InvalidSpaceAreaException(area);
        }
        this.area = area;
        this.rooms = DEFAULT_ROOMS;
    }

    //конструктор принимающий все значения полей
    public Office(int rooms, float area) {
        if (Math.signum(area) == -1.0f) {
            throw new InvalidSpaceAreaException(area);
        }
        if(Integer.signum(rooms) == -1) {
            throw new InvalidRoomsCountException(rooms);
        }
        this.area = area;
        this.rooms = rooms;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        if (Math.signum(area) == -1.0f) {
            throw new InvalidSpaceAreaException(area);
        }
        this.area = area;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        if (Integer.signum(rooms) == -1) {
            throw new InvalidRoomsCountException(rooms);
        }
        this.rooms = rooms;
    }

    @Override
    public int compareTo(Space space) {
        return Float.compare(this.getArea(), space.getArea());
    }

    @Override
    public String toString() {
        return "Office " + rooms + ", " + area + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office that = (Office) o;
        return Float.compare(that.area, area) == 0 &&
                rooms == that.rooms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, rooms);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Office(this.rooms, this.area);
    }
}
