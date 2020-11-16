package ru.netcracker.practice.buildings.util.other;

import ru.netcracker.practice.buildings.exceptions.InexchangeableFloorsException;
import ru.netcracker.practice.buildings.exceptions.InexchangeableSpacesException;
import ru.netcracker.practice.buildings.exceptions.SpaceIndexOutOfBoundsException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public class PlacementExchanger {

    public PlacementExchanger() {
    }

    public static boolean isExchangeable(Space sp1, Space sp2) {
        return sp1.getArea() == sp2.getArea() && sp1.getRooms() == sp2.getRooms();
    }

    public static boolean isExchangeable(Floor fl1, Floor fl2) {
        return fl1.getTotalSpaces() == fl2.getTotalSpaces() && fl1.getTotalArea() == fl2.getTotalArea();
    }

    public static void exchangeFloorRooms(Floor fl1, int index1, Floor fl2, int index2) throws InexchangeableSpacesException {
        if (index1 >= fl1.getTotalSpaces() || index2 >= fl2.getTotalSpaces()) {
            throw new SpaceIndexOutOfBoundsException("index1=" + index1 + ", index2=" + index2);
        }
        Space sp1 = fl1.getSpace(index1);
        Space sp2 = fl2.getSpace(index2);
        if (isExchangeable(sp1, sp2)) {
            fl1.changeSpace(index1, sp2);
            fl2.changeSpace(index2, sp1);
        } else {
            throw new InexchangeableSpacesException();
        }
    }

    public static void exchangeBuildingFloors(Building bu1, int index1, Building bu2, int index2) throws InexchangeableFloorsException {
        if (index1 >= bu1.getBuildingFloors() || index2 >= bu2.getBuildingFloors()) {
            throw new SpaceIndexOutOfBoundsException("index1=" + index1 + ", index2=" + index2);
        }
        Floor fl1 = bu1.getFloor(index1);
        Floor fl2 = bu2.getFloor(index2);
        if (isExchangeable(fl1, fl2)) {
            bu1.changeFloor(index1, fl2);
            bu2.changeFloor(index2, fl1);
        } else {
            throw new InexchangeableFloorsException();
        }
    }
}
