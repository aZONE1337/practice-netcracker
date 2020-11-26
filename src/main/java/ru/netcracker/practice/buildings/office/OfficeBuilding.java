package ru.netcracker.practice.buildings.office;

import ru.netcracker.practice.buildings.exceptions.FloorIndexOutOfBoundsExceptions;
import ru.netcracker.practice.buildings.exceptions.SpaceIndexOutOfBoundsException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.list.DoublyLinkedList;
import ru.netcracker.practice.buildings.util.other.Buildings;
import ru.netcracker.practice.buildings.util.other.MyPair;
import ru.netcracker.practice.buildings.util.list.SinglyLinkedList;

import java.io.Serializable;
import java.util.Objects;

public class OfficeBuilding implements Building, Serializable {
    private DoublyLinkedList<Floor> floors;
    private int floorsAmount;

    public OfficeBuilding() {
        this.floors = null;
        this.floorsAmount = 0;
    }

    //количество этажей и массив количества офисов
    public OfficeBuilding(int floorsAmount, int... officesOnFloor) {
        this.floors = new DoublyLinkedList<>();
        this.floorsAmount = floorsAmount;
        if (floorsAmount == officesOnFloor.length) {
            for (int j : officesOnFloor) {
                this.floors.add(new OfficeFloor(j));
            }
        }
    }

    //принимает массив этажей
    public OfficeBuilding(Floor... floors) {
        this.floors = new DoublyLinkedList<>();
        for (Floor OfficeFloor : floors) {
            this.floors.add(OfficeFloor);
        }
        floorsAmount = floors.length;
    }

    public void setFloors(DoublyLinkedList<Floor> floors) {
        this.floors = floors;
    }

    public int getFloorsAmount() {
        return floorsAmount;
    }

    public void setFloorsAmount(int floorsAmount) {
        this.floorsAmount = floorsAmount;
    }

    //возвращает общее количество этажей
    @Override
    public int getBuildingFloors() {
        return floorsAmount;
    }

    //получение количества офисов
    @Override
    public int getBuildingSpaces() {
        int totalOffices = 0;
        for (Floor floor : floors) {
            totalOffices += floor.getTotalSpaces();
        }
        return totalOffices;
    }

    //общей площади
    @Override
    public float getBuildingArea() {
        float totalArea = .0f;
        for (Floor floor : floors) {
            totalArea += floor.getTotalArea();
        }
        return totalArea;
    }

    @Override
    public int getBuildingRooms() {
        return 0;
    }

    //массива этажей
    @Override
    public Floor[] getFloorsAsArray() {
        Floor[] officeFloors = new Floor[floors.size()];
        for (int i = 0; i < floors.size(); i++) {
            try {
                officeFloors[i] = (Floor) floors.get(i).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return officeFloors;
    }

    @Override
    public Floor getFloor(int index) {
        if (!isFloorIndex(index)) {
            throw new FloorIndexOutOfBoundsExceptions(index);
        }
        return floors.get(index);
    }

    public DoublyLinkedList<Floor> getFloors() {
        return floors;
    }

    //изменение этажа по номеру и ссылке
    @Override
    public boolean changeFloor(int index, Floor officeFloor) {
        if (!isFloorIndex(index)) {
            throw new FloorIndexOutOfBoundsExceptions(index);
        }
        floors.replace(index, officeFloor);
        return true;
    }

    public MyPair<Floor, Integer> getRelativeNumber(int absoluteNumber) {
        if (!isSpaceIndex(absoluteNumber)) {
            throw new SpaceIndexOutOfBoundsException(absoluteNumber);
        }
        int i = 0;
        Floor curFloor = floors.get(i);
        int currentAmount = curFloor.getTotalSpaces();
        while (currentAmount <= absoluteNumber) {
            i++;
            curFloor = floors.get(i);
            currentAmount += curFloor.getTotalSpaces();
        }
        currentAmount -= curFloor.getTotalSpaces();
        return new MyPair<>(curFloor, absoluteNumber - currentAmount);
    }

    //получение офиса по номеру в здании
    @Override
    public Space getSpace(int index) {
        MyPair<Floor, Integer> pair = getRelativeNumber(index);
        return pair.getKey().getSpace(pair.getValue());
    }

    //изменение офиса по номеру и ссылке
    @Override
    public boolean setSpace(int index, Space newSpace) {
        Space curOffice = getSpace(index);
        for (Floor floor : floors) {
            //убрать преобразование
            SinglyLinkedList<Space> officeList = ((OfficeFloor) floor).getSpaces();
            for (Space space : officeList) {
                if (space.equals(curOffice)) {
                    officeList.replace(curOffice, newSpace);
                }
            }
        }
        return true;
    }

    //добавление офиса по номеру и ссылке
    public boolean addOffice(int index, Space office) {
        if (index > getBuildingSpaces()) {
            Floor lastFloor = floors.get(floors.size() - 1);
            lastFloor.addSpace(index, office);
        } else {
            for (Floor floor : floors) {
                if (index <= floor.getTotalSpaces()) {
                    floor.addSpace(index, office);
                    return true;
                }
                index -= floor.getTotalSpaces();
            }
        }
        return true;
    }

    //удаление офиса по номеру
    @Override
    public Space removeSpace(int index) {
        MyPair<Floor, Integer> pair = getRelativeNumber(index);
        return pair.getKey().removeSpace(pair.getValue());
    }

    @Override
    public Space getBestSpace() {
        Space max = getSpace(0);
        for (Floor floor : floors) {
            if (max.getArea() < floor.getBestSpace().getArea()) {
                max = floor.getBestSpace();
            }
        }
        return max;
    }

    @Override
    public Space[] getSpacesSorted() {
        Space[] offices = new Space[getBuildingSpaces()];
        int count = 0;
        for (Floor floor : floors) {
            for (Space space : ((OfficeFloor) floor).getSpaces()) {
                offices[count] = space;
                count++;
            }
        }
        //bubble sort
        Buildings.sort(offices, Comparable::compareTo);
        return offices;
    }

    private boolean isSpaceIndex(int index) {
        return index >= 0 && index < getBuildingSpaces();
    }

    private boolean isFloorIndex(int index) {
        return index >= 0 && index < floorsAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeBuilding)) return false;
        OfficeBuilding that = (OfficeBuilding) o;
        return floorsAmount == that.floorsAmount &&
                floors.equals(that.floors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(floors, floorsAmount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Floor floor : floors) {
            sb.append(floor.toString());
        }
        return "Office building " + floorsAmount + ", " + sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new OfficeBuilding(getFloorsAsArray());
    }
}
