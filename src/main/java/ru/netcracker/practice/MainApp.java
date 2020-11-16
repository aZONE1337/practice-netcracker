package ru.netcracker.practice;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.office.Office;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.office.OfficeFloor;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        Space o11 = new Office(11.0f, 1);
        Space o12 = new Office(12.0f, 2);
        Space o13 = new Office(13.0f, 3);

        Space o21 = new Office(21.0f, 1);
        Space o22 = new Office(22.0f, 2);

        Space o31 = new Office(31.0f, 1);

        Floor of1 = new OfficeFloor(new Space[]{o11, o12, o13});
        Floor of2 = new OfficeFloor(new Space[]{o21, o22});
        Floor of3 = new OfficeFloor(new Space[]{o31});

        System.out.println(of1.toString());

        Building ob = new OfficeBuilding(new Floor[]{of1, of2, of3});

        System.out.println("best space: " + ob.getBestSpace() +
                ", total offices: " + ob.getBuildingSpaces() +
                ", floors amount: " + ob.getBuildingFloors() +
                ", total area: " + ob.getBuildingArea()
        );

        ((OfficeBuilding) ob).addOffice(1, new Office(9999.0f, 99));
        ob.changeFloor(2, new OfficeFloor(new Space[]{new Office(777.0f, 77)}));
        ob.changeSpace(3, new Office(5555.0f, 55));
        ob.removeSpace(2);

        System.out.println("best space: " + ob.getBestSpace() +
                ", total offices: " + ob.getBuildingSpaces() +
                ", floors amount: " + ob.getBuildingFloors() +
                ", total area: " + ob.getBuildingArea()
        );

        System.out.println("Office N3 : " + ob.getSpace(3));
        System.out.println(Arrays.toString(ob.getFloorsAsArray()));
        System.out.println(Arrays.toString(ob.getSpacesSorted()));
        try {
            Buildings.serializeBuilding(ob, new FileOutputStream(new File("building.ser")));
            Building obRead = Buildings.deserializeBuilding(new FileInputStream(new File("building.ser")));
            Buildings.outputBuilding(obRead, System.out);
            System.out.println(obRead.toString());

            OfficeBuilding clone = (OfficeBuilding) ((OfficeBuilding) ob).clone();
            OfficeFloor clone1 = (OfficeFloor) ((OfficeFloor) of1).clone();
            Office clone2 = (Office) ((Office) o11).clone();

            System.out.println("Office clone:");
            System.out.println(clone2 == o11);
            System.out.println(clone2.equals(o11));
            System.out.println(clone2.getClass() == o11.getClass());

            System.out.println("OfficeFloor clone:");
            System.out.println(clone1 == of1);
            System.out.println(clone1.equals(of1));
            System.out.println(clone1.getClass() == of2.getClass());

            System.out.println("OfficeBuilding clone:");
            System.out.println(clone == ob);
            System.out.println(clone.equals(ob));
            System.out.println(clone.getClass() == ob.getClass());
            Buildings.outputBuilding(ob, System.out);
            Buildings.outputBuilding(clone, System.out);

        } catch (IOException | ClassNotFoundException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
