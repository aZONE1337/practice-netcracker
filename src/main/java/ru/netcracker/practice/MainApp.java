package ru.netcracker.practice;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.office.Office;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.office.OfficeFloor;
import ru.netcracker.practice.buildings.threads.*;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class MainApp {
    public static void main(String[] args) {
        Office o11 = new Office(1, 11.0f);
        Space o12 = new Office(2, 12.0f);
        Space o13 = new Office(3, 13.0f);

        Space o21 = new Office(1, 21.0f);
        Space o22 = new Office(2, 22.0f);

        Space o31 = new Office(1, 31.0f);

        Floor of1 = new OfficeFloor(o11, o12, o13);
        Floor of2 = new OfficeFloor(o21, o22);
        Floor of3 = new OfficeFloor(o31);

        System.out.println(of1.toString());

        Building ob = new OfficeBuilding(of1, of2, of3);

        System.out.println("best space: " + ob.getBestSpace() +
                ", total offices: " + ob.getBuildingSpaces() +
                ", floors amount: " + ob.getBuildingFloors() +
                ", total area: " + ob.getBuildingArea()
        );

        ((OfficeBuilding) ob).addOffice(1, new Office(99, 9999.0f));
        ob.changeFloor(2, new OfficeFloor(new Office(77, 777.0f)));
        ob.setSpace(3, new Office(55, 5555.0f));
        ob.removeSpace(2);

        System.out.println("best space: " + ob.getBestSpace() +
                ", total offices: " + ob.getBuildingSpaces() +
                ", floors amount: " + ob.getBuildingFloors() +
                ", total area: " + ob.getBuildingArea()
        );

        System.out.println("Office N3 : " + ob.getSpace(3));
        System.out.println(Arrays.toString(ob.getFloorsAsArray()));
        System.out.println(Arrays.toString(ob.getSpacesSorted()));

        //IO, serialization, clone tests
        try {
            Buildings.serializeBuilding(ob, new FileOutputStream(new File("src/main/resources/building.ser")));
            Building obRead = (Building) Buildings.deserializeBuilding(new FileInputStream(new File("src/main/resources/building.ser")));
            Buildings.outputBuilding(obRead, System.out);
            System.out.println(obRead.toString());

            OfficeBuilding clone = (OfficeBuilding) ob.clone();
            OfficeFloor clone1 = (OfficeFloor) of1.clone();
            Office clone2 = (Office) o11.clone();

            o11.setArea(100000000.0f);
            of1.setSpace(1, new Office(99999999, 11111111111.0f));
            ob.setSpace(2, new Office(1000000000, 1000000.0f));

            System.out.println("Office clone:");
            System.out.println(clone2 == o11);
            System.out.println(clone2.equals(o11));
            System.out.println(clone2.getClass() == o11.getClass());

            System.out.println("OfficeFloor clone:");
            System.out.println(clone1 == of1);
            System.out.println(clone1.equals(of1));
            System.out.println(clone1.getClass() == of1.getClass());

            System.out.println("OfficeBuilding clone:");
            System.out.println(clone == ob);
            System.out.println(clone.equals(ob));
            System.out.println(clone.getClass() == ob.getClass());
            Buildings.outputBuilding(ob, System.out);
            Buildings.outputBuilding(clone, System.out);

        } catch (IOException | ClassNotFoundException | CloneNotSupportedException e) {
            e.printStackTrace();
        }

        //thread tests
        Space[] spaces1 = new Space[50];
        Space[] spaces2 = new Space[spaces1.length];
        for (int i = 0; i < spaces1.length; i++) {
            spaces1[i] = new Office(1, 15.0f);
            spaces2[i] = new Office(3, 10.0f);
        }

        Floor testFloor1 = new OfficeFloor(spaces1);
        Floor testFloor2 = new OfficeFloor(spaces2);

        Semaphore semaphore = new Semaphore(1, true);

        Thread repairer = new Thread(new SequentialRepairer(testFloor1, semaphore));
        Thread cleaner = new Thread(new SequentialCleaner(testFloor1, semaphore));

        try {
            repairer.start();
            cleaner.start();
            cleaner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}