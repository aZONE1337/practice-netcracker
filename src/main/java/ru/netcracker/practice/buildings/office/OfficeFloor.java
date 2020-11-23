package ru.netcracker.practice.buildings.office;

import ru.netcracker.practice.buildings.exceptions.SpaceIndexOutOfBoundsException;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.list.SinglyLinkedList;

import java.io.Serializable;
import java.util.Objects;

public class OfficeFloor implements Floor, Serializable {
    private final SinglyLinkedList<Space> offices;
    private final int officesAmount;

    public OfficeFloor() {
        this.offices = null;
        this.officesAmount = 0;
    }

    //принимает массив
    public OfficeFloor(Space... offices) {
        this.offices = new SinglyLinkedList<>();
        for (Space office : offices) {
            this.offices.add(office);
        }
        officesAmount = offices.length;
    }

    //принимает количество офисов на этаже
    public OfficeFloor(int officesAmount) {
        this.offices = new SinglyLinkedList<>();
        for (int i = 0; i < officesAmount; i++) {
            this.offices.add(new Office());
        }
        this.officesAmount = officesAmount;
    }

    public SinglyLinkedList<Space> getOffices() {
        return offices;
    }

    public int getOfficesAmount() {
        return officesAmount;
    }

    //возвращает действительное количество офисов
    @Override
    public int getTotalSpaces() {
        return officesAmount;
    }

    //возвращает общую площадь
    @Override
    public float getTotalArea() {
        float totalArea = 0.0f;
        for (Space space : offices) {
            totalArea += space.getArea();
        }
        return totalArea;
    }

    //возвращает количество комнат
    @Override
    public int getTotalRooms() {
        int totalRooms = 0;
        for (Space space : offices) {
            totalRooms += space.getRooms();
        }
        return totalRooms;
    }

    public SinglyLinkedList<Space> getSpaces() {
        return offices;
    }

    //возвращает массив офисов
    @Override
    public Space[] getSpacesAsArray() {
        Space[] offices = new Office[this.offices.size()];
        for (int i = 0; i < offices.length; i++) {
            try {
                offices[i] = (Space) this.offices.get(i).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return offices;
    }

    //возвращает офис по номеру
    @Override
    public Space getSpace(int index) {
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        return offices.get(index);
    }

    //изменение по ссылке
    @Override
    public boolean setSpace(int index, Space space) {
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        return offices.replace(index, space);
    }

    //добавление по будущему номеру
    @Override
    public boolean addSpace(int index, Space newSpace) {
        if (index >= officesAmount) {
            for (int i = 0; i < index - officesAmount; i++) {
                offices.add(new Office());
            }
        }
        offices.add(newSpace);
        return true;
    }

    //удаление офиса
    @Override
    public Space removeSpace(int index) {
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        return offices.remove(index);
    }

    @Override
    public Space getBestSpace() {
        Space max = offices.get(0);
        for (Space space : offices) {
            if (max.getArea() < space.getArea()) {
                max = space;
            }
        }
        return max;
    }

    private boolean isSpaceIndex(int index) {
        return index >= 0 && index < officesAmount;
    }

    @Override
    public int compareTo(Floor floor) {
        return Integer.compare(this.getTotalSpaces(), floor.getTotalSpaces());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Space space : offices) {
            sb.append(space.toString());
        }
        return "Office floor " + officesAmount + ", " + sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeFloor that = (OfficeFloor) o;
        return officesAmount == that.officesAmount &&
                offices.equals(that.offices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offices, officesAmount);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new OfficeFloor(getSpacesAsArray());
    }
}