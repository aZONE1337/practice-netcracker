package ru.netcracker.practice.buildings.net.server.sequental;

import ru.netcracker.practice.buildings.dwelling.Dwelling;
import ru.netcracker.practice.buildings.dwelling.hotel.Hotel;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.exceptions.EmptyRequestException;
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
            BufferedReader in;
            BufferedWriter out;

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                in = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream()));
                out = new BufferedWriter(
                        new OutputStreamWriter(
                                client.getOutputStream()));
                System.out.println("I/O created");

                while (client.isConnected()) {
                    String request = in.readLine();
                    System.out.println("Got new request");

                    String response;
                    if (request != null) {
                        if (request.equalsIgnoreCase("quit")) {
                            System.out.println("Client quitting");
                            client.close();
                            System.out.println("Closing connection");
                            break;
                        }

                        if (request.length() == 0) {
                            throw new EmptyRequestException("Request is empty");
                        }

                        System.out.println("Preparing response...");
                        List<Building> buildings = getFromLine(request);
                        response = createResponse(buildings);
                    } else {
                        response = "nullRequest";
                        System.out.println("Request was null");
                    }
                    out.write(response);
                    out.newLine();
                    System.out.println("Response ready");
                    out.flush();
                    System.out.println("Response sent");
                }

                in.close();
                out.close();
                System.out.println("I/O closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Building> getFromLine(String specialLine) {
        List<Building> buildings = new ArrayList<>();
        String[] pairs = specialLine.split("-");

        for (String pair : pairs) {
            String[] separated = pair.split("_");
            String buildingLine = separated[0];
            String type = separated[1];

            if (type.equals("Dwelling"))
                Buildings.setBuildingFactory(new DwellingFactory());
            if (type.equals("Hotel"))
                Buildings.setBuildingFactory(new HotelFactory());
            if (type.equals("Office"))
                Buildings.setBuildingFactory(new OfficeFactory());

            buildings.add(Buildings.getBuildingFromString(buildingLine));
        }

        return buildings;
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

    public static String createResponse(List<Building> buildings) {
        return buildings
                .stream()
                .map(building -> {
                    try {
                        return costEstimate(building) + "$";
                    } catch (BuildingUnderArrestException e) {
                        e.printStackTrace();
                    }
                    return "Building arrested";
                })
                .reduce((prev, next) -> prev + "_   " + next)
                .orElse("");
    }
}
