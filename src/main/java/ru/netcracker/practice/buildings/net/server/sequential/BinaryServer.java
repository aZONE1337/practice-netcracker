package ru.netcracker.practice.buildings.net.server.sequential;

import ru.netcracker.practice.buildings.dwelling.Dwelling;
import ru.netcracker.practice.buildings.dwelling.hotel.Hotel;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer {
    private static final int PORT = 1338;
    private static int count = 0;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server started");

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                respond(client, ++count);
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

    public static void respond(Socket client, int number) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new BufferedWriter(
                             new OutputStreamWriter(client.getOutputStream())), true))
        {
            System.out.println("I/O channels opened");
            while (client.isConnected()) {
                Building request;
                try {
                    request = Buildings.readBuilding(in);
                } catch (NullPointerException e) {
                    break;
                }
                System.out.println("Received new request");

                String response;
                try {
                    response = BinaryServer.costEstimate(request) + "$";
                } catch (BuildingUnderArrestException e) {
                    response = "Building is under arrest";
                }

                out.println(response);
                System.out.println("Response sent");
            }
            System.out.println("Client #" + number + " disconnected");
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
