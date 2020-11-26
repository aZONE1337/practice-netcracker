package ru.netcracker.practice.buildings.dwelling;

import ru.netcracker.practice.buildings.exceptions.FloorIndexOutOfBoundsExceptions;
import ru.netcracker.practice.buildings.exceptions.SpaceIndexOutOfBoundsException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.other.Buildings;
import ru.netcracker.practice.buildings.util.other.MyPair;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Dwelling implements Building, Serializable, Cloneable {
    private final Floor[] floors;
    private final int floorsAmount;


    //конструктор по умолчанию
    public Dwelling() {
        this.floorsAmount = 0;
        this.floors = new Floor[0];
    }

    //конструктор по количеству этажей и количеству квартир на каждом
    public Dwelling(int floorsAmount, int... flatsOnFloor) {
        this.floorsAmount = floorsAmount;
        this.floors = new Floor[floorsAmount];
        if (floorsAmount == flatsOnFloor.length) {
            for (int i = 0; i < flatsOnFloor.length; i++) {
                this.floors[i] = new DwellingFloor(flatsOnFloor[i]);
            }
        }
    }

    //конструктор по массиву этажей
    public Dwelling(Floor... floors) {
        this.floors = floors;
        this.floorsAmount = floors.length;
    }

    @Override
    public int getBuildingFloors() {
        return floorsAmount;
    }

    //получение количества квартир дома
    @Override
    public int getBuildingSpaces() {
        int totalFlats = 0;
        for (Floor dwellingFloor : floors) {
            totalFlats += dwellingFloor.getTotalSpaces();
        }
        return totalFlats;
    }

    //получение общей площади квартир дома
    @Override
    public float getBuildingArea() {
        float totalArea = .0f;
        for (Floor dwellingFloor : floors) {
            totalArea += dwellingFloor.getTotalArea();
        }
        return totalArea;
    }

    //получение общего количества квартир дома
    @Override
    public int getBuildingRooms() {
        int totalRooms = 0;
        for (Floor dwellingFloor : floors) {
            totalRooms += dwellingFloor.getTotalRooms();
        }
        return totalRooms;
    }

    //получение массива этажей дома
    @Override
    public Floor[] getFloorsAsArray() {
        return floors;
    }

    //получение объекта этажа по номеру
    @Override
    public Floor getFloor(int index) {
        if (!isFloorIndex(index)) {
            throw new FloorIndexOutOfBoundsExceptions(index);
        }
        return floors[index];
    }

    //изменение этажа по номеру и новому объекту этажа
    @Override
    public boolean changeFloor(int index, Floor newFloor) {
        Floor curFloor = getFloor(index);
        for (int i = 0; i < floors.length; i++) {
            if (floors[i].equals(curFloor)) {
                floors[i] = newFloor;
            }
        }
        return true;
    }

    private MyPair<Floor, Integer> getRelativeNumber(int absoluteNumber) {
        if (!isSpaceIndex(absoluteNumber)) {
            throw new SpaceIndexOutOfBoundsException(absoluteNumber);
        }
        int i = 0;
        Floor curFloor = floors[i];
        int currentAmount = curFloor.getTotalSpaces();
        while (currentAmount <= absoluteNumber) {
            i++;
            curFloor = floors[i];
            currentAmount += curFloor.getTotalSpaces();
        }
        currentAmount -= curFloor.getTotalSpaces();
        return new MyPair<>(curFloor, absoluteNumber - currentAmount);
    }

    //получение квартиры по номеру в доме (may produce NPA)
    @Override
    public Space getSpace(int index) {
        MyPair<Floor, Integer> pair = getRelativeNumber(index);
        return pair.getKey().getSpace(pair.getValue());
    }

    //изменение квартиры по номеру в доме и новому объекту
    @Override
    public boolean setSpace(int index, Space newSpace) {
        Space curFlat = getSpace(index);
        for (Floor dwellingFloor : floors) {
            Space[] spaces = dwellingFloor.getSpacesAsArray();
            for (int j = 0; j < spaces.length; j++) {
                if (spaces[j].equals(curFlat)) {
                    spaces[j] = newSpace;
                }
            }
        }
        return true;
    }

    //добавление квартиры по номеру и ссылке на объект
    public boolean addSpace(int index, Space newSpace) {
        if (index > getBuildingSpaces()) {
            Floor lastFloor = floors[floorsAmount - 1];
            lastFloor.addSpace(index, newSpace);
        } else {
            for (Floor curFloor : floors) {
                if (index <= curFloor.getTotalSpaces()) {
                    curFloor.addSpace(index, newSpace);
                    return true;
                }
                index -= curFloor.getTotalSpaces();
            }
        }
        return true;
    }

    //удаление квартиры по номеру в доме
    @Override
    public Space removeSpace(int index) {
        MyPair<Floor, Integer> pair = getRelativeNumber(index);
        return pair.getKey().removeSpace(pair.getValue());
    }

    @Override
    public Space getBestSpace() {
        Space max = floors[0].getSpacesAsArray()[0];
        for (Floor dwellingFloor : floors) {
            if (max.getArea() < dwellingFloor.getBestSpace().getArea()) {
                max = dwellingFloor.getBestSpace();
            }
        }
        return max;
    }

    //получение отсортированного по убыванию площадей массива квартир
    @Override
    public Space[] getSpacesSorted() {
        Space[] flats = new Flat[getBuildingSpaces()];
        int counter = 0;
        for (Floor dwellingFloor : floors) {
            Space[] floorFlats = dwellingFloor.getSpacesAsArray();
            for (Space floorFlat : floorFlats) {
                flats[counter] = floorFlat;
                counter++;
            }
        }
        //bubble sort
        Buildings.sort(flats, Comparable::compareTo);
        return flats;
    }

    private boolean isSpaceIndex(int index) {
        return index >= 0 && index < getBuildingSpaces();
    }

    private boolean isFloorIndex(int index) {
        return index >= 0 && index < floorsAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Floor floor : floors) {
            sb.append(floor.toString());
        }
        return "Dwelling " + floorsAmount + ", " + sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dwelling)) return false;
        Dwelling dwelling = (Dwelling) o;
        return floorsAmount == dwelling.floorsAmount &&
                Arrays.equals(floors, dwelling.floors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(floorsAmount);
        result = 31 * result + Arrays.hashCode(floors);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Floor[] floors = new Floor[floorsAmount];
        for (int i = 0; i < floorsAmount; i++) {
            floors[i] = (Floor) this.floors[i].clone();
        }
        return new Dwelling(floors);
    }
}
