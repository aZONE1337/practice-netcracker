package ru.netcracker.practice;

import ru.netcracker.practice.buildings.dwelling.Flat;
import ru.netcracker.practice.buildings.dwelling.hotel.HotelFloor;
import ru.netcracker.practice.buildings.office.Office;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.office.OfficeFloor;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        System.out.println(Buildings.inputBuilding(new FileInputStream(new File("src/main/resources/buildings.txt")),
                OfficeBuilding.class, HotelFloor.class, Flat.class));
    }
}
