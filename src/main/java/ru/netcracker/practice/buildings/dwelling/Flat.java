package ru.netcracker.practice.buildings.dwelling;

import ru.netcracker.practice.buildings.exceptions.InvalidRoomsCountException;
import ru.netcracker.practice.buildings.exceptions.InvalidSpaceAreaException;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Objects;

public class Flat implements Space, Serializable, Cloneable {
    //для конструктора по умолчанию
    private final float DEFAULT_AREA = 50.0f;
    private final int DEFAULT_ROOMS = 2;

    private float area; //площадь квартиры
    private int rooms;  //количество комнат

    //по умолчанию создаёт квартиру из 2 комнат площадью 50кв.м.
    public Flat() {
        this.area = DEFAULT_AREA;
        this.rooms = DEFAULT_ROOMS;
    }

    //конструктор, который принимает площадь квартиры
    public Flat(float flatArea) {
        if (isNegativeOrZero(Float.toString(flatArea))) {   //если S <= 0, то устанавливает площадь по умолчанию
            throw new InvalidSpaceAreaException(area);
        } else {
            this.area = flatArea;
        }
        this.rooms = DEFAULT_ROOMS;
    }

    //Конструктор, который принимает площадь и кол. комнат
    public Flat(int rooms, float flatArea) {
        if (isNegativeOrZero(Float.toString(flatArea))) {
            throw new InvalidSpaceAreaException(area);
        } else {
            this.area = flatArea;
        }
        if (isNegativeOrZero(Integer.toString(rooms))) {
            throw new InvalidRoomsCountException(rooms);
        } else {
            this.rooms = rooms;
        }
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public void setArea(float flatArea) {
        if (isNegativeOrZero(Float.toString(flatArea))) {
            throw new InvalidSpaceAreaException(area);
        } else {
            this.area = flatArea;
        }
    }

    @Override
    public int getRooms() {
        return rooms;
    }

    @Override
    public void setRooms(int rooms) {
        if (isNegativeOrZero(Integer.toString(rooms))) {
            throw new InvalidRoomsCountException(rooms);
        } else {
            this.rooms = rooms;
        }
    }

    public boolean isNegativeOrZero(String number) {  //yes I know 'bout Math.signum()
        return number.contains("-") || number.equals("0") || number.equals("0.0");
    }

    @Override
    public int compareTo(Space space) {
        return Float.compare(this.getArea(), space.getArea());
    }

    @Override
    public String toString() {
        return "Flat " + rooms + ", " + area + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat)) return false;
        Flat flat = (Flat) o;
        return Float.compare(flat.area, area) == 0 &&
                rooms == flat.rooms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, rooms);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Flat(this.rooms, this.area);
    }
}
