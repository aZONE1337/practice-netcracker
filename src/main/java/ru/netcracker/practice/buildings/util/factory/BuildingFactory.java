package ru.netcracker.practice.buildings.util.factory;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public interface BuildingFactory {

    Space createSpace(float area);

    Space createSpace(int rooms, float area);

    Floor createFloor(int spacesAmount);

    Floor CreateFloor(Space[] spaces);

    Building createBuilding(int floorsAmount, int[] spacesOnFloor);

    Building createBuilding(Floor[] floors);
}
