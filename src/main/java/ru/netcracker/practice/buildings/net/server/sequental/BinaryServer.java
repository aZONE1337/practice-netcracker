package ru.netcracker.practice.buildings.net.server.sequental;

import ru.netcracker.practice.buildings.dwelling.Dwelling;
import ru.netcracker.practice.buildings.dwelling.hotel.Hotel;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.util.factory.DwellingFactory;
import ru.netcracker.practice.buildings.util.factory.HotelFactory;
import ru.netcracker.practice.buildings.util.factory.OfficeFactory;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3345)) {

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream()));
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                                client.getOutputStream()));
                System.out.println("I/O channels opened");

                while (client.isConnected()) {

                    String request = in.readLine();
                    System.out.println("Got new request");

                    System.out.println("Preparing response");
                    String response;
                    try {
                        response = costEstimate(Buildings.getBuildingFromString(request)) + "$";
                    } catch (BuildingUnderArrestException e) {
                        response = "Building is under arrest";
                    }
                    out.write(response);
                    out.newLine();
                    out.flush();
                    System.out.println("Response sent");
                }
                System.out.println("Client disconnected");
                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float costEstimate(Building building) throws BuildingUnderArrestException {
        if (new Random().nextBoolean()) {
            throw new BuildingUnderArrestException("Building in under arrest");
        }

        float totalArea = building.getBuildingArea();

        if (building instanceof Dwelling)
            totalArea *= 1000;
        if (building instanceof OfficeBuilding)
            totalArea *= 1500;
        if (building instanceof Hotel)
            totalArea *= 2000;

        return totalArea;
    }
}
