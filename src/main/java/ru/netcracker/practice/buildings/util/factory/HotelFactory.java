package ru.netcracker.practice.buildings.util.factory;

import ru.netcracker.practice.buildings.dwelling.Flat;
import ru.netcracker.practice.buildings.dwelling.hotel.Hotel;
import ru.netcracker.practice.buildings.dwelling.hotel.HotelFloor;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public class HotelFactory implements BuildingFactory {

    @Override
    public Space createSpace(float area) {
        return new Flat(area);
    }

    @Override
    public Space createSpace(int rooms, float area) {
        return new Flat(area, rooms);
    }

    @Override
    public Floor createFloor(int spacesAmount) {
        return new HotelFloor(spacesAmount);
    }

    @Override
    public Floor CreateFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsAmount, int[] spacesOnFloor) {
        return new Hotel(floorsAmount, spacesOnFloor);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}
