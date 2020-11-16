package ru.netcracker.practice.buildings.util.factory;

import ru.netcracker.practice.buildings.dwelling.Dwelling;
import ru.netcracker.practice.buildings.dwelling.DwellingFloor;
import ru.netcracker.practice.buildings.dwelling.Flat;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public class DwellingFactory implements BuildingFactory {

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
        return new DwellingFloor(spacesAmount);
    }

    @Override
    public Floor CreateFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsAmount, int[] spacesOnFloor) {
        return new Dwelling(floorsAmount, spacesOnFloor);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Dwelling(floors);
    }
}
