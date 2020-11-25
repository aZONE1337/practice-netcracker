package ru.netcracker.practice.buildings.dwelling.hotel;

import ru.netcracker.practice.buildings.dwelling.Dwelling;
import ru.netcracker.practice.buildings.dwelling.Flat;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;

public class Hotel extends Dwelling implements Serializable {

    public Hotel(int floorsAmount, int... flatsOnFloor) {
        super(floorsAmount, flatsOnFloor);
    }

    public Hotel(Floor... floors) {
        super(floors);
    }

    public int getStarRating() {
        Floor[] floors = getFloorsAsArray();
        int max = 0;
        for (int i = 0; i < getBuildingFloors(); i++) {
            if (floors[i] instanceof HotelFloor) {
                if (((HotelFloor) floors[i]).getStars() > max) {
                    max = ((HotelFloor) floors[i]).getStars();
                }
            }
        }
        return max;
    }
    
    @Override
    public Space getBestSpace() {
        Floor[] floors = getFloorsAsArray();
        Space max = new Flat(0, 0.1f);
        for (Floor floor : floors) {
            if (floor instanceof HotelFloor) {
                float coeff = ((HotelFloor) floor).getCoeff();
                Space[] spaces = floor.getSpacesAsArray();
                for (Space space : spaces) {
                    if (space.getArea() * coeff > max.getArea() * coeff) {
                        max = space;
                    }
                }
            }
        }
        return max;
    }

    @Override
    public String toString() {
        return super.toString().replace("Dwelling", "Hotel " + getStarRating() + ",");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return getBuildingFloors() == hotel.getBuildingFloors() &&
                Arrays.equals(getFloorsAsArray(), hotel.getFloorsAsArray());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
