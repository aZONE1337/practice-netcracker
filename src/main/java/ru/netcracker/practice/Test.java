package ru.netcracker.practice;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.net.client.BinaryClient;
import ru.netcracker.practice.buildings.net.client.SerialClient;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        List<Building> buildingList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            buildingList.add(Buildings.readBuilding(
//                    new FileReader(new File("src/main/resources/buildings.txt")))
//            );
//        }
//
//        for (Building building : buildingList) {
//            Buildings.serializeBuilding(building,
//                    new FileOutputStream(new File("src/main/resources/buildings.ser"))
//            );
//        }
//
//        buildingList.clear();
//
//        for (int i = 0; i < 5; i++) {
//            buildingList.add((Building) Buildings.deserializeBuilding(
//                    new FileInputStream(new File("src/main/resources/buildings.ser")))
//            );
//        }
//
//        System.out.println("READY!");
//        SerialClient.main(args);
        BinaryClient.main(args);
    }
}
