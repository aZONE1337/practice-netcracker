package ru.netcracker.practice.buildings.dwelling;

import ru.netcracker.practice.buildings.exceptions.SpaceIndexOutOfBoundsException;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class DwellingFloor implements Floor, Serializable, Cloneable {
    private Space[] flats;   //массив квартир
    private int flatsAmount;    //количество квартир на этаже

    public DwellingFloor() {    //конструктор по умолчанию инициализирует нулями
        this.flatsAmount = 0;
        this.flats = new Space[0];
    }

    //конструктор, принимающий количество квартир
    public DwellingFloor(int flatsNumber) {
        this.flatsAmount = flatsNumber;
        this.flats = new Space[flatsNumber];
        for (int i = 0; i < flatsNumber; i++) {
            this.flats[i] = new Flat();
        }
    }

    //конструктор, принимающий массив квартир
    public DwellingFloor(Space... flats) {
        this.flats = flats;
        this.flatsAmount = flats.length;
    }

    //получение кол. квартир
    @Override
    public int getTotalSpaces() {
        return flatsAmount;
    }

    //получение общей площади квартир этажа

    public float getTotalArea() {
        float totalArea = 0.0f;
        for (Space flat : this.flats) {
            totalArea += flat.getArea();
        }
        return totalArea;
    }

    public Space[] getFlats() {
        return flats;
    }

    public void setFlats(Space... flats) {
        this.flats = flats;
    }

    public int getFlatsAmount() {
        return flatsAmount;
    }

    public void setFlatsAmount(int flatsAmount) {
        this.flatsAmount = flatsAmount;
    }

    //получение количества комнат квартир этажа
    @Override
    public int getTotalRooms() {
        int totalRooms = 0;
        for (Space flat : this.flats) {
            totalRooms += flat.getRooms();
        }
        return totalRooms;
    }

    //получение массива квартир
    @Override
    public Space[] getSpacesAsArray() {
        return flats;
    }

    //получение квартиры по номеру
    @Override
    public Space getSpace(int index) {
        ////возвращает квартиру со значениями по умолчанию, чтобы не обрабатывать nullPointerException
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        return flats[index];
    }

    //изменение квартиры по номеру и ссылке на объект квартиры
    @Override
    public boolean setSpace(int index, Space newSpace) {
        //проверка номера квартиры
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        Space curFlat = getSpace(index);
        for (int i = 0; i < flats.length; i++) {
            if (flats[i].equals(curFlat)) {
                flats[i] = newSpace;
            }
        }
        return true;
    }

    //добавление новой квартиры по номеру и ссылке на объект квартиры
    @Override
    public boolean addSpace(int index, Space newSpace) {
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        flatsAmount++;
        return true;
    }

    //Удаление квартиры по номеру
    @Override
    public Space removeSpace(int index) {
        //если номер квартиры не лежит на отрезке существующих квартир
        if (!isSpaceIndex(index)) {
            throw new SpaceIndexOutOfBoundsException(index);
        }
        Space SpaceToReturn = flats[index];
        Space[] newFlats = new Space[flatsAmount - 1];
        System.arraycopy(flats, 0, newFlats, 0, index);
        System.arraycopy(flats, index + 1, newFlats, index, newFlats.length - index);
        flats = newFlats;
        flatsAmount--;
        return SpaceToReturn;
    }

    //Получение квартиры с самой большой площадью (might return null)
    @Override
    public Space getBestSpace() {
        Space max = flats[0];
        for (Space flat : flats) {
            if (flat.getArea() > max.getArea()) {
                max = flat;
            }
        }
        return max;
    }

    private boolean isSpaceIndex(int index) {
        return index >= 0 && index < flatsAmount;
    }

    @Override
    public int compareTo(Floor floor) {
        return Integer.compare(this.getTotalSpaces(), floor.getTotalSpaces());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Space flat : flats) {
            sb.append(flat.toString());
        }
        return "Dwelling floor " + flatsAmount + ", " + sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DwellingFloor)) return false;
        DwellingFloor that = (DwellingFloor) o;
        return flatsAmount == that.flatsAmount &&
                Arrays.equals(flats, that.flats);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(flatsAmount);
        result = 31 * result + Arrays.hashCode(flats);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Space[] flats = new Space[flatsAmount];
        for (int i = 0; i < flatsAmount; i++) {
            flats[i] = (Space) this.flats[i].clone();
        }
        return new DwellingFloor(flats);
    }
}