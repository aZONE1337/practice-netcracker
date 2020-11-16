package ru.netcracker.practice.buildings.util.factory;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.office.Office;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.office.OfficeFloor;

public class OfficeFactory implements BuildingFactory {

    @Override
    public Space createSpace(float area) {
        return new Office(area);
    }

    @Override
    public Space createSpace(int rooms, float area) {
        return new Office(area, rooms);
    }

    @Override
    public Floor createFloor(int spacesAmount) {
        return new OfficeFloor(spacesAmount);
    }

    @Override
    public Floor CreateFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsAmount, int[] spacesOnFloor) {
        return new OfficeBuilding(floorsAmount, spacesOnFloor);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding(floors);
    }
}
